import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Another way of controlling amount of data flowing is batching.
 * Reactor provides three batching strategies: grouping, windowing, and buffering
 * <p>
 * Read first:
 * <p>
 * https://projectreactor.io/docs/core/release/reference/#advanced-three-sorts-batching
 * https://projectreactor.io/docs/core/release/reference/#which.window
 * <p>
 * Useful documentation:
 * <p>
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c11_Batching extends BatchingBase {

    /**
     * To optimize disk writing, write data in batches of max 10 items, per batch.
     */
    @Test
    public void batch_writer() {
        //todo do your changes here
        Flux<Void> dataStream = null;
        dataStream();
        writeToDisk(null);

        //do not change the code below
        StepVerifier.create(dataStream)
                    .verifyComplete();

        Assertions.assertEquals(10, diskCounter.get());
    }

    /**
     * You are implementing a command gateway in CQRS based system. Each command belongs to an aggregate (aggregateId).
     * <p>
     * All commands that belong to the same aggregate needs to be sent sequentially, after previously was sent, to
     * prevent aggregate concurrency issue.
     * <p>
     * But commands that belong to different aggregates should be sent in parallel (max 10 at the same time). Implement
     * this behaviour by using GroupedFlux, and knowledge gained from the previous exercises.
     */
    @Test
    public void command_gateway() {
        //todo: implement your changes here
        Flux<Void> processCommands = null;
        inputCommandStream();
        sendCommand(null);

        //do not change the code below
        StepVerifier.create(processCommands)
                    .verifyComplete();
    }


    /**
     * m You are implement time series database. You need to implement sum over time operator. Calculate sum of all
     * metrics that have been published each second.
     * <p>
     * Hint: Use window(Duration.ofSeconds(1)) and reduce(0, (a, b) -> a + b) .window(Duration.ofSeconds(1))
     * .concatMap(window -> window.reduce(0L, Long::sum)) .doOnNext(sum -> System.out.println("sum last second: " +
     * sum))
     */
    @Test
    public void sum_over_time() {
        Flux<Long> metrics = metrics()
                //todo: implement your changes here
                .take(10);

        StepVerifier.create(metrics)
                    .expectNext(45L, 165L, 255L, 396L, 465L, 627L, 675L, 858L, 885L, 1089L)
                    .verifyComplete();
    }
}
