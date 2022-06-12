import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * It's time to do some data manipulation!
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#which.values
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c2_TransformingSequence extends TransformingSequenceBase {

    /***
     * Your task is simple:
     *  Increment each number emitted by the numerical service
     */
    @Test
    public void transforming_sequence() {
        Flux<Integer> numbersFlux = numerical_service()
                .map(integer -> integer + 1);

        //StepVerifier is used for testing purposes
        //ignore it for now, or explore it independently
        StepVerifier.create(numbersFlux)
                    .expectNext(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
                    .verifyComplete();
    }

    /***
     * Transform given number sequence to:
     *   - ">": if given number is greater than 0
     *   - "=": if number is equal to 0
     *   - "<": if given number is lesser then 0
     */
    @Test
    public void transforming_sequence_2() {
        Flux<Integer> numbersFlux = numerical_service_2();

        Flux<String> resultSequence = numbersFlux.map(integer -> {
            if (integer > 0) {
                return ">";
            } else if (integer < 0) {
                return "<";
            }
            return "=";
        });

        //don't change code below
        StepVerifier.create(resultSequence)
                    .expectNext(">", "<", "=", ">", ">")
                    .verifyComplete();
    }

    /**
     * `object_service()` streams sequence of Objects, but if you peek into service implementation, you can see
     * that these items are in fact strings!
     * Casting using `map()` to cast is one way to do it, but there is more convenient way.
     * Remove `map` operator and use more appropriate operator to cast sequence to String.
     */
    @Test
    public void cast() {
        Flux<String> numbersFlux = object_service()
                .cast(String.class);

        StepVerifier.create(numbersFlux)
                    .expectNext("1", "2", "3", "4", "5")
                    .verifyComplete();
    }

    /**
     * `maybe_service()` may return some result.
     * In case it doesn't return any result, return value "no results".
     */
    @Test
    public void maybe() {
        Mono<String> result = maybe_service()
                .defaultIfEmpty("no results");

        StepVerifier.create(result)
                    .expectNext("no results")
                    .verifyComplete();
    }

    /**
     * Reduce the values from `numerical_service()` into a single number that is equal to sum of all numbers emitted by
     * this service.
     */
    @Test
    public void sequence_sum() {
        Mono<Integer> sum = numerical_service().reduce((integer, integer2) -> integer + integer2);
        // Or from the solutions:
//        Mono<Integer> sum = numerical_service().reduce(0, Integer::sum);

        StepVerifier.create(sum)
                    .expectNext(55)
                    .verifyComplete();
    }

    /***
     *  Reduce the values from `numerical_service()` but emit each intermediary number
     *  Use first Flux value as initial value.
     */
    @Test
    public void sum_each_successive() {
        Flux<Integer> sumEach = numerical_service()
                .scan((integer, integer2) -> integer + integer2);
        // Or
//        Flux<Integer> sumEach = numerical_service()
//                .scan(Integer::sum);

        StepVerifier.create(sumEach)
                    .expectNext(1, 3, 6, 10, 15, 21, 28, 36, 45, 55)
                    .verifyComplete();
    }

    /**
     * A developer who wrote `numerical_service()` forgot that sequence should start with zero, so you must prepend zero
     * to result sequence.
     *
     * Do not alter `numerical_service` implementation!
     * Use only one operator.
     */
    @Test
    public void sequence_starts_with_zero() {
        Flux<Integer> result = numerical_service()
                .startWith(0)
                ;

        StepVerifier.create(result)
                    .expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    .verifyComplete();
    }
}
