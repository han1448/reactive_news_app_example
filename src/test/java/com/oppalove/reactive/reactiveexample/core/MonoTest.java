package com.oppalove.reactive.reactiveexample.core;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void MonoAnd() {

        Mono.just("Hi")
                .subscribe(System.out::println);

        Mono.just("Hi")
                .map(t -> t.concat("!"))
                .log()
                .and(Mono.just("Reactive"))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void MonoAs() {
        Mono.just("200")
                .as(Flux::from)
                .subscribe(System.out::println);
    }

    @Test
    public void MonoCache() {
        Mono<String> cachedMono = Mono.just("Hi")
                .map(text -> {
                    System.out.println("working...");
                    return text;
                })
                .cache()
                .map(text -> text.concat(", i'm cache"));
        cachedMono.log().subscribe(System.out::println);
        cachedMono.log().subscribe(System.out::println);
    }


}
