package com.example.service.user.infrastructure.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class SingleReactive<T> {

    Mono<T> mono;

    public Mono<T> mono() {
        return mono;
    }

    public <R> SingleReactive<R> map(Function<? super T, ? extends R> mapper) {
        return SingleReactive.of(mono.map(mapper));
    }

    public <R> SingleReactive<R> flatMap(Function<? super T, ? extends SingleReactive<? extends R>> transformer) {
        Function<? super T, ? extends Mono<? extends R>> monoFunction = transformer.andThen(SingleReactive::mono);
        return SingleReactive.of(mono.flatMap(monoFunction));
    }

    public static <T> SingleReactive<T> error(Throwable t) {
        return SingleReactive.of(Mono.error(t));
    }

}