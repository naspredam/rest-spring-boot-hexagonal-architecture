package com.example.service.user.infrastructure.reactive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class CollectionReactive<T> {

    Flux<T> flux;

    public Flux<T> flux() {
        return flux;
    }

    public <R> CollectionReactive<R> map(Function<? super T, ? extends R> mapper) {
        return CollectionReactive.of(flux.map(mapper));
    }
}

