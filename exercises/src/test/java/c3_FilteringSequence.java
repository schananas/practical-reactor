import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Sequence may produce many elements, but we are not always interested in all of them. In this chapter we will learn
 * how to filter elements from a sequence.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#which.filtering
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c3_FilteringSequence extends FilteringSequenceBase {

    /**
     * Collect most popular girl names, no longer then 4 characters.
     */
    @Test
    public void girls_are_made_of_sugar_and_spice() {
        Flux<String> shortListed = popular_girl_names_service()
                //todo: change this line only
                ;

        StepVerifier.create(shortListed)
                    .expectNext("Emma", "Ava", "Mia", "Luna", "Ella")
                    .verifyComplete();
    }

    /**
     * `mashed_data_service()` returns sequence of generic objects.
     *  Without using `filter()` operator, collect only objects that are instance of `String`
     */
    @Test
    public void needle_in_a_haystack() {
        Flux<Object> strings = mashed_data_service()
                //todo: change this line only
                ;

        StepVerifier.create(strings)
                    .expectNext("1", "String.class")
                    .verifyComplete();
    }

    /**
     * This service may return duplicated data. Filter out all the duplicates from the sequence.
     */
    @Test
    public void economical() {
        Flux<String> items = duplicated_records_service()
                //todo: change this line only, use only one operator
                ;

        StepVerifier.create(items)
                    .expectNext("1", "2", "3", "4", "5")
                    .verifyComplete();
    }

    /**
     * This service returns many elements, but you are only interested in the first one.
     * Also, service is very fragile, if you pull more than needed, you may brake it.
     *
     * This time no blocking. Use only one operator.
     */
    @Test
    public void watch_out_for_the_spiders() {
        //todo: change code as you need
        Mono<String> firstResult = Mono.empty();
        fragile_service();

        //don't change code below
        StepVerifier.create(firstResult)
                    .expectNext("watch_out")
                    .verifyComplete();
    }

    /**
     * `number_service()` returns 300 numbers, but you only need first 100 numbers.
     */
    @Test
    public void dont_take_more_then_you_need() {
        Flux<Integer> numbers = number_service()
                //todo: change this line only
                ;

        StepVerifier.create(numbers)
                    .expectNextCount(100)
                    .verifyComplete();
    }

    /**
     * `number_service()` returns 300 numbers, but you only need last 100 numbers.
     */
    @Test
    public void not_a_binary_search() {
        Flux<Integer> numbers = number_service()
                //todo: change this line only
                ;

        StepVerifier.create(numbers)
                    .expectNextMatches(i -> i >= 200)
                    .expectNextCount(99)
                    .verifyComplete();
    }

    /**
     * `number_service()` returns 300 numbers, but you only need 100 numbers, from the middle.
     */
    @Test
    public void golden_middle() {
        Flux<Integer> numbers = number_service()
                //todo: do your changes here
                ;

        StepVerifier.create(numbers)
                    .expectNextMatches(i -> i >= 100)
                    .expectNextCount(99)
                    .verifyComplete();
    }
}
