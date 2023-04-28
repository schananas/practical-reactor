//import org.junit.jupiter.api.*
///**
// * It's time to do some data manipulation!
// *
// * Read first:
// *
// * https://projectreactor.io/docs/core/release/reference/#which.values
// *
// * Useful documentation:
// *
// * https://projectreactor.io/docs/core/release/reference/#which-operator
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
// *
// * @author Stefan Dragisic
// */
//class c2_TransformingSequence : TransformingSequenceBase() {
//    /***
//     * Your task is simple:
//     * Increment each number emitted by the numerical service
//     */
//    @Test
//    fun transforming_sequence() {
//        val numbersFlux: Flux<Integer> = numerical_service() //todo change only this line
//
//        //StepVerifier is used for testing purposes
//        //ignore it for now, or explore it independently
//        StepVerifier.create(numbersFlux)
//            .expectNext(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
//            .verifyComplete()
//    }
//
//    /***
//     * Transform given number sequence to:
//     * - ">": if given number is greater than 0
//     * - "=": if number is equal to 0
//     * - "<": if given number is lesser then 0
//     */
//    @Test
//    fun transforming_sequence_2() {
//        val numbersFlux: Flux<Integer> = numerical_service_2()
//
//        //todo: do your changes here
//        val resultSequence: Flux<String>? = null
//
//        //don't change code below
//        StepVerifier.create(resultSequence)
//            .expectNext(">", "<", "=", ">", ">")
//            .verifyComplete()
//    }
//
//    /**
//     * `object_service()` streams sequence of Objects, but if you peek into service implementation, you can see
//     * that these items are in fact strings!
//     * Casting using `map()` to cast is one way to do it, but there is more convenient way.
//     * Remove `map` operator and use more appropriate operator to cast sequence to String.
//     */
//    @Test
//    fun cast() {
//        val numbersFlux: Flux<String> = object_service()
//            .map { i -> i } //todo: change this line only
//        StepVerifier.create(numbersFlux)
//            .expectNext("1", "2", "3", "4", "5")
//            .verifyComplete()
//    }
//
//    /**
//     * `maybe_service()` may return some result.
//     * In case it doesn't return any result, return value "no results".
//     */
//    @Test
//    fun maybe() {
//        val result: Mono<String> = maybe_service() //todo: change this line only
//        StepVerifier.create(result)
//            .expectNext("no results")
//            .verifyComplete()
//    }
//
//    /**
//     * Reduce the values from `numerical_service()` into a single number that is equal to sum of all numbers emitted by
//     * this service.
//     */
//    @Test
//    fun sequence_sum() {
//        val sum: Mono<Integer>? = null
//        numerical_service() //todo: do your changes here
//        StepVerifier.create(sum)
//            .expectNext(55)
//            .verifyComplete()
//    }
//
//    /***
//     * Reduce the values from `numerical_service()` but emit each intermediary number
//     * Use first Flux value as initial value.
//     */
//    @Test
//    fun sum_each_successive() {
//        val sumEach: Flux<Integer> = numerical_service() //todo: do your changes here
//        StepVerifier.create(sumEach)
//            .expectNext(1, 3, 6, 10, 15, 21, 28, 36, 45, 55)
//            .verifyComplete()
//    }
//
//    /**
//     * A developer who wrote `numerical_service()` forgot that sequence should start with zero, so you must prepend zero
//     * to result sequence.
//     *
//     * Do not alter `numerical_service` implementation!
//     * Use only one operator.
//     */
//    @Test
//    fun sequence_starts_with_zero() {
//        val result: Flux<Integer> = numerical_service() //todo: change this line only
//        StepVerifier.create(result)
//            .expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//            .verifyComplete()
//    }
//}