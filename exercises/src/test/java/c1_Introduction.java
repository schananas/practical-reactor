import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This chapter will introduce you to the basics of Reactor.
 * You will learn how to retrieve result from Mono and Flux
 * in different ways.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#intro-reactive
 * https://projectreactor.io/docs/core/release/reference/#reactive.subscribe
 * https://projectreactor.io/docs/core/release/reference/#_subscribe_method_examples
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c1_Introduction extends IntroductionBase {

    /**
     * Every journey starts with Hello World!
     * As you may know, Mono represents asynchronous result of 0-1 element.
     * Retrieve result from this Mono by blocking indefinitely or until a next signal is received.
     */
    @Test
    public void hello_world() {
        Mono<String> serviceResult = hello_world_service();
        String result = serviceResult.block();

        assertEquals("Hello World!", result);
    }

    /**
     * Retrieving result should last for a limited time amount of time, or you might get in trouble.
     * Try retrieving result from service by blocking for maximum of 1 second or until a next signal is received.
     */
    @Test
    public void unresponsive_service() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Mono<String> serviceResult = unresponsiveService();

            String result = serviceResult.block(Duration.of(1, ChronoUnit.SECONDS));
        });

        String expectedMessage = "Timeout on blocking read for 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Services are unpredictable, they might and might not return a result and no one likes nasty NPE's.
     * Retrieve result from the service as optional object.
     */
    @Test
    public void empty_service() {
        Mono<String> serviceResult = emptyService();

        Optional<String> optionalServiceResult = serviceResult.blockOptional();

        assertTrue(optionalServiceResult.isEmpty());
        assertTrue(emptyServiceIsCalled.get());
    }

    /**
     * Many services return more than one result and best services supports streaming!
     * It's time to introduce Flux, an Asynchronous Sequence of 0-N Items.
     *
     * Service we are calling returns multiple items, but we are interested only in the first one.
     * Retrieve first item from this Flux by blocking indefinitely until a first item is received.
     */
    @Test
    public void multi_result_service() {
        Flux<String> serviceResult = multiResultService();

        String result = serviceResult.blockFirst();

        assertEquals("valid result", result);
    }

    /**
     * We have the service that returns list of fortune top five companies.
     * Collect companies emitted by this service into a list.
     * Retrieve results by blocking.
     */
    @Test
    public void fortune_top_five() {
        Flux<String> serviceResult = fortuneTop5();

        List<String> results = serviceResult.collectList().block();

        assertEquals(Arrays.asList("Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group"), results);
        assertTrue(fortuneTop5ServiceIsCalled.get());
    }

    /***
     * "I Used an Operator on my Flux, but it Doesn’t Seem to Apply. What Gives?"
     *
     * Previously we retrieved result by blocking on a Mono/Flux.
     * That really beats whole purpose of non-blocking and asynchronous library like Reactor.
     * Blocking operators are usually used for testing or when there is no way around, and
     * you need to go back to synchronous world.
     *
     * Fix this test without using any blocking operator.
     * Change only marked line!
     */
    @Test
    public void nothing_happens_until_you_() throws InterruptedException {
        CopyOnWriteArrayList<String> companyList = new CopyOnWriteArrayList<>();

        Flux<String> serviceResult = fortuneTop5();

        serviceResult
                .doOnNext(companyList::add).subscribe(System.out::println)
        //todo: add an operator here, don't use any blocking operator!
        ;

        Thread.sleep(1000); //bonus: can you explain why this line is needed?

        assertEquals(Arrays.asList("Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group"), companyList);
    }

    /***
     * If you finished previous task, this one should be a breeze.
     *
     * Upgrade previously used solution, so that it:
     *  - adds each emitted item to `companyList`
     *  - does nothing if error occurs
     *  - sets `serviceCallCompleted` to `true` once service call is completed.
     *
     *  Don't use doOnNext, doOnError, doOnComplete hooks.
     */
    @Test
    public void leaving_blocking_world_behind() throws InterruptedException {
        AtomicReference<Boolean> serviceCallCompleted = new AtomicReference<>(false);
        CopyOnWriteArrayList<String> companyList = new CopyOnWriteArrayList<>();

        fortuneTop5().subscribe(companyList::add, System.out::println, () -> serviceCallCompleted.set(true))
        ;

        Thread.sleep(1000);

        assertTrue(serviceCallCompleted.get());
        assertEquals(Arrays.asList("Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group"), companyList);
    }
}
