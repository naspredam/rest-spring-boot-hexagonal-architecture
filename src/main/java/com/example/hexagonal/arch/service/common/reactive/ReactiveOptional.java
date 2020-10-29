package com.example.hexagonal.arch.service.common.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class ReactiveOptional<T> {

    Mono<T> mono;

    public Mono<T> mono() {
        return mono;
    }

    public <R> ReactiveOptional<R> map(Function<? super T, ? extends R> mapper) {
        return ReactiveOptional.of(mono.map(mapper));
    }

    public <R> ReactiveOptional<R> flatMap(Function<? super T, ? extends ReactiveOptional<? extends R>> transformer) {
        Function<? super T, ? extends Mono<? extends R>> monoFunction = transformer.andThen(ReactiveOptional::mono);
        return ReactiveOptional.of(mono.flatMap(monoFunction));
    }

    public static <T> ReactiveOptional<T> error(Throwable t) {
        return ReactiveOptional.of(Mono.error(t));
    }

}
