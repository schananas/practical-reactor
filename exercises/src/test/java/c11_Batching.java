import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Another way of controlling amount of data flowing is batching.
 * Reactor provides three batching strategies: grouping, windowing, and buffering.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#advanced-three-sorts-batching
 * https://projectreactor.io/docs/core/release/reference/#which.window
 *
 * Useful documentation:
 *
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
        Flux<Void> dataStream = dataStream()
                .buffer(10)
                .flatMap(this::writeToDisk);


        //do not change the code below
        StepVerifier.create(dataStream)
                    .verifyComplete();

        Assertions.assertEquals(10, diskCounter.get());
    }

    /**
     * You are implementing a command gateway in CQRS based system. Each command belongs to an aggregate and has `aggregateId`.
     * All commands that belong to the same aggregate needs to be sent sequentially, after previous command was sent, to
     * prevent aggregate concurrency issue.
     * But commands that belong to different aggregates can and should be sent in parallel.
     * Implement this behaviour by using `GroupedFlux`, and knowledge gained from the previous exercises.
     */
    @Test
    public void command_gateway() {
        Flux<Void> processCommands = inputCommandStream()
                .groupBy(Command::getAggregateId)
                .flatMap(aggregateCommands -> //execute commands from same aggregate in parallel
                                 aggregateCommands.concatMap(this::sendCommand) //execute commands from same aggregate sequentially
                );

        //do not change the code below
        StepVerifier.create(processCommands)
                    .verifyComplete();
    }


    /**
     * You are implementing time-series database. You need to implement `sum over time` operator. Calculate sum of all
     * metric readings that have been published during one second.
     */
    @Test
    public void sum_over_time() {
        Flux<Long> metrics = metrics()
                .window(Duration.ofSeconds(1))
                .concatMap(window -> window.reduce(0L, Long::sum))
                .doOnNext(sum -> System.out.println("sum last second: " + sum))
                .take(10);

        StepVerifier.create(metrics)
                    .expectNext(45L, 165L, 255L, 396L, 465L, 627L, 675L, 858L, 885L, 1089L)
                    .verifyComplete();
    }
}
