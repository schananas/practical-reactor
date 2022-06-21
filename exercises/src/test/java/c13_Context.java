import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Often we might require state when working with complex streams. Reactor offers powerful context mechanism to share
 * state between operators, as we can't rely on thread-local variables, because threads are not guaranteed to be the
 * same. In this chapter we will explore usage of Context API.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#context
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c13_Context extends ContextBase {

    /**
     * You are writing a message handler that is executed by a framework (client). Framework attaches a http correlation
     * id to the Reactor context. Your task is to extract the correlation id and attach it to the message object.
     */
    public Mono<Message> messageHandler(String payload) {
        //todo: do your changes withing this method
        return Mono.just(new Message("set correlation_id from context here", payload));
    }

    @Test
    public void message_tracker() {
        //don't change this code
        Mono<Message> mono = messageHandler("Hello World!")
                .contextWrite(Context.of(HTTP_CORRELATION_ID, "2-j3r9afaf92j-afkaf"));

        StepVerifier.create(mono)
                    .expectNextMatches(m -> m.correlationId.equals("2-j3r9afaf92j-afkaf") && m.payload.equals(
                            "Hello World!"))
                    .verifyComplete();
    }

    /**
     * Following code counts how many times connection has been established. But there is a bug in the code. Fix it.
     */
    @Test
    public void execution_counter() {
        Mono<Void> repeat = Mono.deferContextual(ctx -> {
            ctx.get(AtomicInteger.class).incrementAndGet();
            return openConnection();
        });
        //todo: change this line only
        ;

        StepVerifier.create(repeat.repeat(4))
                    .thenAwait(Duration.ofSeconds(10))
                    .expectAccessibleContext()
                    .assertThat(ctx -> {
                        assert ctx.get(AtomicInteger.class).get() == 5;
                    }).then()
                    .expectComplete().verify();
    }

    /**
     * You need to retrieve 10 result pages from the database.
     * Using the context and repeat operator, keep track of which page you are on.
     * If the error occurs during a page retrieval, log the error message containing page number that has an
     * error and skip the page. Fetch first 10 pages.
     */
    @Test
    public void pagination() {
        AtomicInteger pageWithError = new AtomicInteger(); //todo: set this field when error occurs

        //todo: start from here
        Flux<Integer> results = getPage(0)
                .flatMapMany(Page::getResult)
                .repeat(10)
                .doOnNext(i -> System.out.println("Received: " + i));


        //don't change this code
        StepVerifier.create(results)
                    .expectNextCount(90)
                    .verifyComplete();

        Assertions.assertEquals(3, pageWithError.get());
    }
}
