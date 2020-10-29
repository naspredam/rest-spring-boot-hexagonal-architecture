package com.example.hexagonal.arch.service.common.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class ReactiveCollection<T> {

    Flux<T> flux;

    public Flux<T> flux() {
        return flux;
    }

    public <R> ReactiveCollection<R> map(Function<? super T, ? extends R> mapper) {
        return ReactiveCollection.of(flux.map(mapper));
    }
}
