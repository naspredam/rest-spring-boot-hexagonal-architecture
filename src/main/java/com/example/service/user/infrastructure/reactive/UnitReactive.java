package com.example.service.user.infrastructure.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class UnitReactive<T> {

    Mono<T> mono;

    public Mono<T> mono() {
        return mono;
    }

    public <R> UnitReactive<R> map(Function<? super T, ? extends R> mapper) {
        return UnitReactive.of(mono.map(mapper));
    }

    public <R> UnitReactive<R> flatMap(Function<? super T, ? extends UnitReactive<? extends R>> transformer) {
        Function<? super T, ? extends Mono<? extends R>> monoFunction = transformer.andThen(UnitReactive::mono);
        return UnitReactive.of(mono.flatMap(monoFunction));
    }

    public static <T> UnitReactive<T> error(Throwable t) {
        return UnitReactive.of(Mono.error(t));
    }

}