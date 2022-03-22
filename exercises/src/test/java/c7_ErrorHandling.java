import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * It's time introduce some resiliency by recovering from unexpected events!
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#which.errors
 * https://projectreactor.io/docs/core/release/reference/#error.handling
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c7_ErrorHandling extends ErrorHandlingBase {

    /**
     * You are monitoring hearth beat signal from space probe. Heart beat is sent every 1 second.
     * Raise error if probe does not any emit heart beat signal longer then 3 seconds.
     * If error happens, save it in `errorRef`.
     */
    @Test
    public void houston_we_have_a_problem() {
        AtomicReference<Throwable> errorRef = new AtomicReference<>();
        Flux<String> heartBeat = probeHeartBeatSignal()
                //todo: do your changes here
                //todo: & here
                ;

        StepVerifier.create(heartBeat)
                    .expectNextCount(3)
                    .expectError(TimeoutException.class)
                    .verify();

        Assertions.assertTrue(errorRef.get() instanceof TimeoutException);
    }

    /**
     * Retrieve currently logged user.
     * If any error occurs, exception should be further propagated as `SecurityException`.
     * Keep original cause.
     */
    @Test
    public void potato_potato() {
        Mono<String> currentUser = getCurrentUser()
                //todo: change this line only
                //use SecurityException
                ;

        StepVerifier.create(currentUser)
                    .expectErrorMatches(e -> e instanceof SecurityException &&
                            e.getCause().getMessage().equals("No active session, user not found!"))
                    .verify();
    }

    /**
     * Consume all the messages `messageNode()`.
     * Ignore any failures, and if error happens finish consuming silently without propagating any error.
     */
    @Test
    public void under_the_rug() {
        Flux<String> messages = messageNode();
        //todo: change this line only
        ;

        StepVerifier.create(messages)
                    .expectNext("0x1", "0x2")
                    .verifyComplete();
    }

    /**
     * Retrieve all the messages `messageNode()`,and if node suddenly fails
     * use `backupMessageNode()` to consume the rest of the messages.
     */
    @Test
    public void have_a_backup() {
        //todo: feel free to change code as you need
        Flux<String> messages = null;
        messageNode();
        backupMessageNode();

        //don't change below this line
        StepVerifier.create(messages)
                    .expectNext("0x1", "0x2", "0x3", "0x4")
                    .verifyComplete();
    }

    /**
     * Consume all the messages `messageNode()`, if node suddenly fails report error to `errorReportService` then
     * propagate error downstream.
     */
    @Test
    public void error_reporter() {
        //todo: feel free to change code as you need
        Flux<String> messages = messageNode();
        errorReportService(null);

        //don't change below this line
        StepVerifier.create(messages)
                    .expectNext("0x1", "0x2")
                    .expectError(RuntimeException.class)
                    .verify();
        Assertions.assertTrue(errorReported.get());
    }

    /**
     * Execute all tasks from `taskQueue()`. If task executes
     * without any error, commit task changes, otherwise rollback task changes.
     * Do don't propagate any error downstream.
     */
    @Test
    public void unit_of_work() {
        Flux<Task> taskFlux = taskQueue()
                //todo: do your changes here
                ;

        StepVerifier.create(taskFlux)
                    .expectNextMatches(task -> task.executedExceptionally.get())
                    .expectNextMatches(task -> task.executedSuccessfully.get())
                    .verifyComplete();
    }

    /**
     * `getFilesContent()` should return files content from three different files. But one of the files may be
     * corrupted and will throw an exception if opened.
     * Using `onErrorContinue()` skip corrupted file and get the content of the other files.
     */
    @Test
    public void billion_dollar_mistake() {
        Flux<String> content = getFilesContent()
                .flatMap(Function.identity())
                //todo: change this line only
                ;

        StepVerifier.create(content)
                    .expectNext("file1.txt content", "file3.txt content")
                    .verifyComplete();
    }

    /**
     * Quote from one of creators of Reactor: onErrorContinue is my billion-dollar mistake. `onErrorContinue` is
     * considered as a bad practice, its unsafe and should be avoided.
     *
     * {@see <a href="https://nurkiewicz.com/2021/08/onerrorcontinue-reactor.html">onErrorContinue</a>} {@see <a
     * href="https://devdojo.com/ketonemaniac/reactor-onerrorcontinue-vs-onerrorresume">onErrorContinue vs
     * onErrorResume</a>} {@see <a href="https://bsideup.github.io/posts/daily_reactive/where_is_my_exception/">Where is
     * my exception?</a>}
     *
     * Your task is to implement `onErrorContinue()` behaviour using `onErrorResume()` operator,
     * by using knowledge gained from previous lessons.
     */
    @Test
    public void resilience() {
        //todo: change code as you need
        Flux<String> content = getFilesContent()
                .flatMap(Function.identity()); //start from here

        //don't change below this line
        StepVerifier.create(content)
                    .expectNext("file1.txt content", "file3.txt content")
                    .verifyComplete();
    }

    /**
     * You are trying to read temperature from your recently installed DIY IoT temperature sensor. Unfortunately, sensor
     * is cheaply made and may not return value on each read. Keep retrying until you get a valid value.
     */
    @Test
    public void its_hot_in_here() {
        Mono<Integer> temperature = temperatureSensor()
                //todo: change this line only
                ;

        StepVerifier.create(temperature)
                    .expectNext(34)
                    .verifyComplete();
    }

    /**
     * In following example you are trying to establish connection to database, which is very expensive operation.
     * You may retry to establish connection maximum of three times, so do it wisely!
     * FIY: database is temporarily down, and it will be up in few seconds (5).
     */
    @Test
    public void back_off() {
        Mono<String> connection_result = establishConnection()
                //todo: change this line only
                ;

        StepVerifier.create(connection_result)
                    .expectNext("connection_established")
                    .verifyComplete();
    }

    /**
     * You are working with legacy system in which you need to read alerts by pooling SQL table. Implement polling
     * mechanism by invoking `nodeAlerts()` repeatedly until you get all (2) alerts. If you get empty result, delay next
     * polling invocation by 1 second.
     */
    @Test
    public void good_old_polling() {
        //todo: change code as you need
        Flux<String> alerts = null;
        nodeAlerts();

        //don't change below this line
        StepVerifier.create(alerts.take(2))
                    .expectNext("node1:low_disk_space", "node1:down")
                    .verifyComplete();
    }

    public static class SecurityException extends Exception {

        public SecurityException(Throwable cause) {
            super(cause);
        }
    }
}
