import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stefan Dragisic
 */
public class ErrorHandlingBase {

    AtomicBoolean errorReported = new AtomicBoolean(false);
    AtomicInteger counter = new AtomicInteger(3);
    AtomicBoolean gate = new AtomicBoolean(false);
    AtomicBoolean scheduled = new AtomicBoolean(false);
    AtomicInteger invokedCounter = new AtomicInteger(0);
    AtomicInteger pollCounter = new AtomicInteger(0);

    public Flux<String> probeHeartBeatSignal() {
        return Flux.concat(Flux.just("keep-alive"),
                           Flux.just("keep-alive"),
                           Flux.just("keep-alive"),
                           Flux.never())
                   .delayElements(Duration.ofSeconds(1))
                   .doOnNext(n -> System.out.println("Service: " + n));
    }

    public Mono<String> getCurrentUser() {
        return Mono.error(new IllegalAccessError("No active session, user not found!"));
    }

    public Flux<String> messageNode() {
        return Flux.just("0x1", "0x2")
                   .concatWith(Flux.error(new RuntimeException("Service shutdown unexpectedly!")));
    }

    public Flux<String> backupMessageNode() {
        return Flux.just("0x3", "0x4");
    }

    public Mono<Void> errorReportService(Throwable error) {
        return Mono.fromRunnable(() -> {
            errorReported.set(true);
            System.out.println("Thank you for reporting this error!");
            System.out.println("Error reported: " + error.getMessage());
        });
    }

    public Flux<Task> taskQueue() {
        return Flux.just(new Task(), new Task());
    }

    public Flux<Mono<String>> getFilesContent() {
        return Flux.just("file1.txt", "file2.txt", "file3.txt")
                   .doOnNext(n -> System.out.println("Reading file: " + n))
                   .map(n -> Mono.fromCallable(() -> {
                       if (n.equals("file2.txt")) {
                           throw new RuntimeException("file2.txt is broken");
                       }
                       return n + " content";
                   })).doOnError(e -> System.out.println("Error reading file: " + e.getMessage()));
    }

    public Mono<Integer> temperatureSensor() {
        return Mono.fromCallable(() -> {
            if (counter.decrementAndGet() == 0) {
                return 34;
            } else {
                throw new RuntimeException("Sensor reading failed!");
            }
        });
    }

    public Mono<String> establishConnection() {
        return Mono.fromCallable(() -> {
            invokedCounter.incrementAndGet();
            if (invokedCounter.get() > 3) {
                System.out.println("You are not allowed to connect more than 3 times!");
                throw new RuntimeException("You are not allowed to connect more than 3 times!");
            }
            System.out.println("Establishing connection...");
            if (gate.get()) {
                return "connection_established";
            } else {
                if (!scheduled.get()) {
                    scheduled.set(true);
                    Executors.newScheduledThreadPool(1).schedule(() -> gate.set(true), 5, TimeUnit.SECONDS);
                }
                throw new RuntimeException("Sensor reading failed!");
            }
        });
    }

    public Mono<String> nodeAlerts() {
        return Mono.fromCallable(() -> {
                       System.out.println("------------------------------------------------------");
                       System.out.println("SELECT * FROM alerts LIMIT 1");
                       if (pollCounter.incrementAndGet() == 5) {
                           System.out.println("New alert: Node1 is low on disk space!");
                           return "node1:low_disk_space";
                       } else if (pollCounter.get() == 7) {
                           System.out.println("New alert: Node1 is down!");
                           return "node1:down";
                       } else {
                           System.out.println("No alerts found!");
                           return null;
                       }
                   })
                   .subscribeOn(Schedulers.boundedElastic());
    }

    public static class Task {

        static AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean executedSuccessfully = new AtomicBoolean(false);
        AtomicBoolean executedExceptionally = new AtomicBoolean(false);

        public Mono<Void> execute() {
            return Mono.fromRunnable(() -> {
                System.out.println("Executing task...");
                if (counter.incrementAndGet() == 1) {
                    throw new RuntimeException("Task failed!");
                } else {
                    System.out.println("Task executed successfully...");
                }
            });
        }

        public Mono<Void> commit() {
            return Mono.fromRunnable(() -> {
                executedSuccessfully.set(true);
                System.out.println("Committing task...");
            });
        }


        public Mono<Void> rollback(Throwable error) {
            return Mono.fromRunnable(() -> {
                executedExceptionally.set(true);
                System.out.println("Rollback task... Cause: " + error.getMessage());
            });
        }
    }
}
