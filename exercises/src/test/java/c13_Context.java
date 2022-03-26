import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
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
       return Mono.deferContextual(ctx -> {
           String correlationId = ctx.get(HTTP_CORRELATION_ID);
           return Mono.just(new Message(correlationId,payload));
       });
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
                                })
                                .contextWrite(Context.of(AtomicInteger.class, new AtomicInteger(0)));

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
        AtomicInteger pageWithError = new AtomicInteger();

        Flux<Integer> results = Mono.deferContextual(ctx -> getPage(ctx.get(AtomicInteger.class).get()))
                                    .doOnEach(s -> {
                                        if (s.getType() == SignalType.ON_NEXT) {
                                            s.getContextView().get(AtomicInteger.class).incrementAndGet();
                                        } else if (s.getType() == SignalType.ON_ERROR) {
                                            pageWithError.set(s.getContextView().get(AtomicInteger.class).get());
                                            System.out.println(
                                                    "Error has occurred: " + s.getThrowable().getMessage());
                                            System.out.println("Error occurred at page: " + s.getContextView()
                                                                                             .get(AtomicInteger.class)
                                                                                             .getAndIncrement());
                                        }
                                    })
                                    .onErrorResume(e -> Mono.empty())
                                    .flatMapMany(Page::getResult)
                                    .repeat(10)
                                    .doOnNext(i -> System.out.println("Received: " + i))
                                    .contextWrite(Context.of(AtomicInteger.class, new AtomicInteger(0)));
        //don't change this code
        StepVerifier.create(results)
                    .expectNextCount(90)
                    .verifyComplete();

        Assertions.assertEquals(3, pageWithError.get());
    }
}
