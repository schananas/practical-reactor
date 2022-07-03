import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import reactor.blockhound.BlockHound;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.NonBlocking;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * With multi-core architectures being a commodity nowadays, being able to easily parallelize work is important.
 * Reactor helps with that by providing many mechanisms to execute work in parallel.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#schedulers
 * https://projectreactor.io/docs/core/release/reference/#advanced-parallelizing-parralelflux
 * https://projectreactor.io/docs/core/release/reference/#_the_publishon_method
 * https://projectreactor.io/docs/core/release/reference/#_the_subscribeon_method
 * https://projectreactor.io/docs/core/release/reference/#which.time
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c9_ExecutionControl extends ExecutionControlBase {

    /**
     * You are working on smartphone app and this part of code should show user his notifications. Since there could be
     * multiple notifications, for better UX you want to slow down appearance between notifications by 1 second.
     * Pay attention to threading, compare what code prints out before and after solution. Explain why?
     */
    @Test
    public void slow_down_there_buckaroo() {
        long threadId = Thread.currentThread().getId();
        Flux<String> notifications = readNotifications()
                .doOnNext(System.out::println)
                //todo: change this line only
                ;

        StepVerifier.create(notifications
                                    .doOnNext(s -> assertThread(threadId)))
                    .expectNextCount(5)
                    .verifyComplete();
    }

    private void assertThread(long invokerThreadId) {
        long currentThread = Thread.currentThread().getId();
        if (currentThread != invokerThreadId) {
            System.out.println("-> Not on the same thread");
        } else {
            System.out.println("-> On the same thread");
        }
        Assertions.assertTrue(currentThread != invokerThreadId, "Expected to be on a different thread");
    }

    /**
     * You are using free access to remote hosting machine. You want to execute 3 tasks on this machine, but machine
     * will allow you to execute one task at a time on a given schedule which is orchestrated by the semaphore. If you
     * disrespect schedule, your access will be blocked.
     * Delay execution of tasks until semaphore signals you that you can execute the task.
     */
    @Test
    public void ready_set_go() {
        //todo: feel free to change code as you need
        Flux<String> tasks = tasks()
                .flatMap(Function.identity());
        semaphore();

        //don't change code below
        StepVerifier.create(tasks)
                    .expectNext("1")
                    .expectNoEvent(Duration.ofMillis(2000))
                    .expectNext("2")
                    .expectNoEvent(Duration.ofMillis(2000))
                    .expectNext("3")
                    .verifyComplete();
    }

    /**
     * Make task run on thread suited for short, non-blocking, parallelized work.
     * Answer:
     * - Which types of schedulers Reactor provides?
     * - What is their purpose?
     * - What is their difference?
     */
    @Test
    public void non_blocking() {
        Mono<Void> task = Mono.fromRunnable(() -> {
                                  Thread currentThread = Thread.currentThread();
                                  assert NonBlocking.class.isAssignableFrom(Thread.currentThread().getClass());
                                  System.out.println("Task executing on: " + currentThread.getName());
                              })
                              //todo: change this line only
                              .then();

        StepVerifier.create(task)
                    .verifyComplete();
    }

    /**
     * Make task run on thread suited for long, blocking, parallelized work.
     * Answer:
     * - What BlockHound for?
     */
    @Test
    public void blocking() {
        BlockHound.install(); //don't change this line

        Mono<Void> task = Mono.fromRunnable(ExecutionControlBase::blockingCall)
                              .subscribeOn(Schedulers.single())//todo: change this line only
                              .then();

        StepVerifier.create(task)
                    .verifyComplete();
    }

    /**
     * Adapt code so tasks are executed in parallel, with max concurrency of 3.
     */
    @Test
    public void free_runners() {
        //todo: feel free to change code as you need
        Mono<Void> task = Mono.fromRunnable(ExecutionControlBase::blockingCall);

        Flux<Void> taskQueue = Flux.just(task, task, task)
                                   .concatMap(Function.identity());

        //don't change code below
        Duration duration = StepVerifier.create(taskQueue)
                                        .expectComplete()
                                        .verify();

        Assertions.assertTrue(duration.getSeconds() <= 2, "Expected to complete in less than 2 seconds");
    }

    /**
     * Adapt the code so tasks are executed in parallel, but task results should preserve order in which they are invoked.
     */
    @Test
    public void sequential_free_runners() {
        //todo: feel free to change code as you need
        Flux<String> tasks = tasks()
                .flatMap(Function.identity());
        ;

        //don't change code below
        Duration duration = StepVerifier.create(tasks)
                                        .expectNext("1")
                                        .expectNext("2")
                                        .expectNext("3")
                                        .verifyComplete();

        Assertions.assertTrue(duration.getSeconds() <= 1, "Expected to complete in less than 1 seconds");
    }

    /**
     * Make use of ParallelFlux to branch out processing of events in such way that:
     * - filtering events that have metadata, printing out metadata, and mapping to json can be done in parallel.
     * Then branch in before appending events to store. `appendToStore` must be invoked sequentially!
     */
    @Test
    public void event_processor() {
        //todo: feel free to change code as you need
        Flux<String> eventStream = eventProcessor()
                .filter(event -> event.metaData.length() > 0)
                .doOnNext(event -> System.out.println("Mapping event: " + event.metaData))
                .map(this::toJson)
                .concatMap(n -> appendToStore(n).thenReturn(n));

        //don't change code below
        StepVerifier.create(eventStream)
                    .expectNextCount(250)
                    .verifyComplete();

        List<String> steps = Scannable.from(eventStream)
                                      .parents()
                                      .map(Object::toString)
                                      .collect(Collectors.toList());

        String last = Scannable.from(eventStream)
                               .steps()
                               .collect(Collectors.toCollection(LinkedList::new))
                               .getLast();

        Assertions.assertEquals("concatMap", last);
        Assertions.assertTrue(steps.contains("ParallelMap"), "Map operator not executed in parallel");
        Assertions.assertTrue(steps.contains("ParallelPeek"), "doOnNext operator not executed in parallel");
        Assertions.assertTrue(steps.contains("ParallelFilter"), "filter operator not executed in parallel");
        Assertions.assertTrue(steps.contains("ParallelRunOn"), "runOn operator not used");
    }

    private String toJson(Event n) {
        try {
            return new ObjectMapper().writeValueAsString(n);
        } catch (JsonProcessingException e) {
            throw Exceptions.propagate(e);
        }
    }
}
