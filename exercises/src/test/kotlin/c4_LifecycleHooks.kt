//import org.junit.jupiter.api.*
//
///**
// * Lifecycle hooks are used to add additional behavior (side-effects) and to peek into sequence without modifying it. In
// * this chapter we will explore most common lifecycle hooks.
// *
// * Read first:
// *
// * https://projectreactor.io/docs/core/release/reference/#which.peeking
// *
// * Useful documentation:
// *
// * https://projectreactor.io/docs/core/release/reference/#which-operator
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
// *
// * @author Stefan Dragisic
// */
//class c4_LifecycleHooks : LifecycleHooksBase() {
//    /**
//     * Add a hook that will execute when Flux `temperatureFlux` is subscribed too.
//     * As a side effect hook should add string "subscribe" to `hooksTriggered` list.
//     */
//    @Test
//    fun no_subscription_no_gains() {
//        val hooksTriggered: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.take(5))
//            .expectNextCount(5)
//            .verifyComplete()
//        Assertions.assertEquals(hooksTriggered, List.of("subscribe"))
//    }
//
//    /**
//     * Add a hook that will execute before Flux `temperatureFlux` is subscribed too. As a side effect hook should add
//     * string "before subscribe" to `hooksTriggered` list.
//     */
//    @Test
//    fun be_there_early() {
//        val hooksTriggered: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.take(5).doOnSubscribe { s -> hooksTriggered.add("subscribe") })
//            .expectNextCount(5)
//            .verifyComplete()
//        Assertions.assertEquals(hooksTriggered, Arrays.asList("before subscribe", "subscribe"))
//    }
//
//    /**
//     * Add a hook that will execute for each element emitted by `temperatureFlux`. As a side effect print out the value
//     * using `System.out` and increment `counter` value.
//     */
//    @Test
//    fun atomic_counter() {
//        val counter = AtomicInteger(0)
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux)
//            .expectNextCount(20)
//            .verifyComplete()
//        Assertions.assertEquals(counter.get(), 20)
//    }
//
//    /**
//     * Add a hook that will execute when `temperatureFlux` has completed without errors. As a side effect set
//     * `completed` flag to true.
//     */
//    @Test
//    fun successfully_executed() {
//        val completed = AtomicBoolean(false)
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.skip(20))
//            .expectNextCount(0)
//            .verifyComplete()
//        Assertions.assertTrue(completed.get())
//    }
//
//    /**
//     * Add a hook that will execute when `temperatureFlux` is canceled by the subscriber. As a side effect set
//     * `canceled` flag to true.
//     */
//    @Test
//    fun need_to_cancel() {
//        val canceled = AtomicBoolean(false)
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.take(0))
//            .expectNextCount(0)
//            .verifyComplete()
//        Assertions.assertTrue(canceled.get())
//    }
//
//    /**
//     * Add a side-effect that increments `hooksTriggeredCounter` counter when the `temperatureFlux` terminates, either
//     * by completing successfully or failing with an error.
//     * Use only one operator.
//     */
//    @Test
//    fun terminator() {
//        val hooksTriggeredCounter = AtomicInteger(0)
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.take(0))
//            .expectNextCount(0)
//            .verifyComplete()
//        StepVerifier.create(temperatureFlux.skip(20))
//            .expectNextCount(0)
//            .verifyComplete()
//        StepVerifier.create(temperatureFlux.skip(20).concatWith(Flux.error(RuntimeException("oops"))))
//            .expectError()
//            .verify()
//        Assertions.assertEquals(hooksTriggeredCounter.get(), 2)
//    }
//
//    /**
//     * Add a side effect that increments `hooksTriggeredCounter` when the `temperatureFlux` terminates, either when
//     * completing successfully, gets canceled or failing with an error.
//     * Use only one operator!
//     */
//    @Test
//    fun one_to_catch_them_all() {
//        val hooksTriggeredCounter = AtomicInteger(0)
//        val temperatureFlux: Flux<Integer> = room_temperature_service() //todo: change this line only
//        StepVerifier.create(temperatureFlux.take(0))
//            .expectNextCount(0)
//            .verifyComplete()
//        StepVerifier.create(temperatureFlux.skip(20))
//            .expectNextCount(0)
//            .verifyComplete()
//        StepVerifier.create(
//            temperatureFlux.skip(20)
//                .concatWith(Flux.error(RuntimeException("oops")))
//        )
//            .expectError()
//            .verify()
//        Assertions.assertEquals(hooksTriggeredCounter.get(), 3)
//    }
//
//    /**
//     * Replace `to do` strings with "one" || "two" || "three" depending on order of `doFirst()` hook execution.
//     */
//    @Test
//    fun ordering_is_important() {
//        val sideEffects: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()
//        val just: Mono<Boolean> = Mono.just(true)
//            .doFirst { sideEffects.add("three") }
//            .doFirst { sideEffects.add("two") }
//            .doFirst { sideEffects.add("one") }
//        val orderOfExecution: List<String> = Arrays.asList("todo", "todo", "todo") //todo: change this line only
//        StepVerifier.create(just)
//            .expectNext(true)
//            .verifyComplete()
//        Assertions.assertEquals(sideEffects, orderOfExecution)
//    }
//
//    /**
//     * There is advanced operator, typically used for monitoring of a Flux. This operator will add behavior
//     * (side-effects) triggered for each signal that happens on Flux. It also has access to the context, which might be
//     * useful later.
//     *
//     * In this exercise, Flux will emit three elements and then complete. Add signal names to `signal` list dynamically,
//     * once these signals occur.
//     *
//     * Bonus: Explore this operator's documentation, as it may be useful in the future.
//     */
//    @Test
//    fun one_to_rule_them_all() {
//        val signals: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()
//        val flux: Flux<Integer> = Flux.just(1, 2, 3) //todo: change this line only
//        StepVerifier.create(flux)
//            .expectNextCount(3)
//            .verifyComplete()
//        Assertions.assertEquals(signals, Arrays.asList("ON_NEXT", "ON_NEXT", "ON_NEXT", "ON_COMPLETE"))
//    }
//}