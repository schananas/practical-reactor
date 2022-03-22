import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Lifecycle hooks are used to add additional behavior (side-effects) and to peek into sequence without modifying it. In
 * this chapter we will explore most common lifecycle hooks.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#which.peeking
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c4_LifecycleHooks extends LifecycleHooksBase {

    /**
     * Add a hook that will execute when Flux `temperatureFlux` is subscribed too.
     * As a side effect hook should add string "subscribe" to `hooksTriggered` list.
     */
    @Test
    public void no_subscription_no_gains() {
        CopyOnWriteArrayList<String> hooksTriggered = new CopyOnWriteArrayList<>();

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.take(5))
                    .expectNextCount(5)
                    .verifyComplete();

        Assertions.assertEquals(hooksTriggered, List.of("subscribe"));
    }

    /**
     * Add a hook that will execute before Flux `temperatureFlux` is subscribed too. As a side effect hook should add
     * string "before subscribe" to `hooksTriggered` list.
     */
    @Test
    public void be_there_early() {
        CopyOnWriteArrayList<String> hooksTriggered = new CopyOnWriteArrayList<>();

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.take(5).doOnSubscribe(s -> hooksTriggered.add("subscribe")))
                    .expectNextCount(5)
                    .verifyComplete();

        Assertions.assertEquals(hooksTriggered, Arrays.asList("before subscribe", "subscribe"));
    }

    /**
     * Add a hook that will execute for each element emitted by `temperatureFlux`. As a side effect print out the value
     * using `System.out` and increment `counter` value.
     */
    @Test
    public void atomic_counter() {
        AtomicInteger counter = new AtomicInteger(0);

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux)
                    .expectNextCount(20)
                    .verifyComplete();

        Assertions.assertEquals(counter.get(), 20);
    }

    /**
     * Add a hook that will execute when `temperatureFlux` has completed without errors. As a side effect set
     * `completed` flag to true.
     */
    @Test
    public void successfully_executed() {
        AtomicBoolean completed = new AtomicBoolean(false);

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.skip(20))
                    .expectNextCount(0)
                    .verifyComplete();

        Assertions.assertTrue(completed.get());
    }

    /**
     * Add a hook that will execute when `temperatureFlux` is canceled by the subscriber. As a side effect set
     * `canceled` flag to true.
     */
    @Test
    public void need_to_cancel() {
        AtomicBoolean canceled = new AtomicBoolean(false);

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.take(0))
                    .expectNextCount(0)
                    .verifyComplete();

        Assertions.assertTrue(canceled.get());
    }

    /**
     * Add a side-effect that increments `hooksTriggeredCounter` counter when the `temperatureFlux` terminates, either
     * by completing successfully or failing with an error.
     * Use only one operator.
     */
    @Test
    public void terminator() {
        AtomicInteger hooksTriggeredCounter = new AtomicInteger(0);

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.take(0))
                    .expectNextCount(0)
                    .verifyComplete();

        StepVerifier.create(temperatureFlux.skip(20))
                    .expectNextCount(0)
                    .verifyComplete();

        StepVerifier.create(temperatureFlux.skip(20).concatWith(Flux.error(new RuntimeException("oops"))))
                    .expectError()
                    .verify();

        Assertions.assertEquals(hooksTriggeredCounter.get(), 2);
    }

    /**
     * Add a side effect that increments `hooksTriggeredCounter` when the `temperatureFlux` terminates, either when
     * completing successfully, gets canceled or failing with an error.
     * Use only one operator!
     */
    @Test
    public void one_to_catch_them_all() {
        AtomicInteger hooksTriggeredCounter = new AtomicInteger(0);

        Flux<Integer> temperatureFlux = room_temperature_service()
                //todo: change this line only
                ;

        StepVerifier.create(temperatureFlux.take(0))
                    .expectNextCount(0)
                    .verifyComplete();

        StepVerifier.create(temperatureFlux.skip(20))
                    .expectNextCount(0)
                    .verifyComplete();

        StepVerifier.create(temperatureFlux.skip(20)
                                           .concatWith(Flux.error(new RuntimeException("oops"))))
                    .expectError()
                    .verify();

        Assertions.assertEquals(hooksTriggeredCounter.get(), 3);
    }

    /**
     * Replace `to do` strings with "one" || "two" || "three" depending on order of `doFirst()` hook execution.
     */
    @Test
    public void ordering_is_important() {
        CopyOnWriteArrayList<String> sideEffects = new CopyOnWriteArrayList<>();

        Mono<Boolean> just = Mono.just(true)
                                 .doFirst(() -> sideEffects.add("three"))
                                 .doFirst(() -> sideEffects.add("two"))
                                 .doFirst(() -> sideEffects.add("one"));

        List<String> orderOfExecution =
                Arrays.asList("todo", "todo", "todo"); //todo: change this line only

        StepVerifier.create(just)
                    .expectNext(true)
                    .verifyComplete();

        Assertions.assertEquals(sideEffects, orderOfExecution);
    }

    /**
     * There is advanced operator, typically used for monitoring of a Flux. This operator will add behavior
     * (side-effects) triggered for each signal that happens on Flux. It also has access to the context, which might be
     * useful later.
     *
     * In this exercise, Flux will emit three elements and then complete. Add signal names to `signal` list dynamically,
     * once these signals occur.
     *
     * Bonus: Explore this operator's documentation, as it may be useful in the future.
     */
    @Test
    public void one_to_rule_them_all() {
        CopyOnWriteArrayList<String> signals = new CopyOnWriteArrayList<>();

        Flux<Integer> flux = Flux.just(1, 2, 3)
                //todo: change this line only
                ;

        StepVerifier.create(flux)
                    .expectNextCount(3)
                    .verifyComplete();

        Assertions.assertEquals(signals, Arrays.asList("ON_NEXT", "ON_NEXT", "ON_NEXT", "ON_COMPLETE"));
    }
}
