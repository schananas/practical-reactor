import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stefan Dragisic
 */
public class CombiningPublishersBase {

    AtomicInteger taskCounter = new AtomicInteger(0);
    AtomicBoolean localCacheCalled = new AtomicBoolean(false);
    AtomicInteger consumedSpamCounter = new AtomicInteger(0);
    AtomicBoolean fileOpened = new AtomicBoolean(false);
    AtomicBoolean writtenToFile = new AtomicBoolean(false);
    AtomicBoolean fileClosed = new AtomicBoolean(false);
    AtomicInteger committedTasksCounter = new AtomicInteger(0);

    public Mono<String> getCurrentUser() {
        return Mono.just("user123");
    }

    public Mono<String> getUserEmail(String user) {
        return Mono.fromSupplier(() -> user + "@gmail.com");
    }

    public Flux<Mono<Void>> taskExecutor() {
        return Flux.range(1, 10)
                   //.delayElements(Duration.ofMillis(250))
                   .map(i -> Mono.<Void>fromRunnable(() -> {
                       System.out.println("Executing task: #" + i);
                       taskCounter.incrementAndGet();
                   }).subscribeOn(Schedulers.parallel()));
    }

    public Mono<Flux<Message>> streamingService() {
        return Mono.just(Flux.range(1, 10)
                             .delayElements(Duration.ofMillis(250))
                             .map(i ->
                                          new Message("chunk:" + i, UUID.randomUUID().toString()))
                             .doOnNext(msg -> System.out.println("-> msg#" + msg.metaData))
                             .doOnSubscribe(s -> System.out.println("Streaming started..."))
                             .delaySubscription(Duration.ofMillis(750))
                             .doOnComplete(() -> System.out.println("Streaming finished!")))
                   .doOnSubscribe(s -> System.out.println("Connecting to the service..."));
    }

    public Flux<Integer> numberService1() {
        return Flux.range(1, 3).doOnNext(System.out::println);
    }

    public Flux<Integer> numberService2() {
        return Flux.range(4, 4).doOnNext(System.out::println);
    }

    public Flux<String> listAllUsers() {
        return Flux.range(1, 10)
                   .map(i -> "user" + i);
    }

    public Flux<String> getStocksLocalCache() {
        return Flux.defer(() -> {
            System.out.println("(LocalCache) No stocks found in local cache!");
            localCacheCalled.set(true);
            return Flux.empty();
        });
    }

    public Flux<String> getStocksRest() {
        return Flux.range(10, 6)
                   .map(i -> i + "$")
                   .doOnNext(n -> System.out.println("(REST) Got stock, price: " + n))
                   .delaySubscription(Duration.ofMillis(100));
    }

    public Flux<String> getStocksGrpc() {
        return Flux.range(1, 5)
                   .map(i -> i + "$")
                   .doOnNext(n -> System.out.println("(GRPC) Got stock, price: " + n))
                   .delaySubscription(Duration.ofMillis(30));
    }

    public Flux<Message> mailBoxPrimary() {
        return Flux.range(1, 3)
                   .map(i -> new Message("spam", "0x" + i))
                   .doOnNext(n -> System.out.println("Message[spam, 0x" + n.payload + "]"))
                   .doOnNext(n -> consumedSpamCounter.incrementAndGet());
    }

    public Flux<Message> mailBoxSecondary() {
        return Flux.range(1, 2)
                   .map(i -> new Message("job-offer", "please join as in google!"))
                   .doOnNext(n -> System.out.println("Message[job-offer, please join as in google!]"));
    }

    public Flux<String> userSearchInput() {
        return Flux.just("r", "re", "rea", "reac", "reac", "react", "reacto", "reactor")
                   .concatWith(Flux.just("reactive").delaySubscription(Duration.ofMillis(500)))
                   .doOnNext(n -> System.out.println("Typed: " + n));
    }

    public Mono<String> autoComplete(String word) {
        return Mono.just(word + " project")
                   .doOnNext(n -> System.out.println("Suggestion: " + n))
                   .delaySubscription(Duration.ofMillis(100));
    }

    public Mono<Void> openFile() {
        return Mono.<Void>fromRunnable(() -> {
            fileOpened.set(true);
            System.out.println("Opening file...");
        }).delaySubscription(Duration.ofMillis(100));
    }

    public Mono<Void> writeToFile(String content) {
        return Mono.<Void>fromRunnable(() -> {
            writtenToFile.set(true);
            System.out.println("Writing: " + content);
        }).delaySubscription(Duration.ofMillis(1000));
    }

    public Flux<String> readFile() {
        return Flux.defer(() -> {
            if (fileOpened.get()) {
                return Flux.just("0x1", "0x2", "0x3");
            } else {
                return Flux.error(new IllegalStateException("File is not opened!"));
            }
        }).doOnNext(n -> System.out.println("Next line: " + n));
    }


    public Mono<Void> closeFile() {
        return Mono.<Void>fromRunnable(() -> {
            fileClosed.set(true);
            System.out.println("File closed!");
        }).delaySubscription(Duration.ofMillis(500));
    }

    public Flux<Mono<String>> tasksToExecute() {
        return Flux.range(1, 3)
                   .map(i -> Mono.fromSupplier(() -> {
                                     System.out.println("Executing task: #" + i);
                                     return "task#" + i;
                                 })
                                 .delaySubscription(Duration.ofMillis(250))
                                 .subscribeOn(Schedulers.parallel()));
    }

    public Mono<Void> commitTask(String taskId) {
        committedTasksCounter.incrementAndGet();
        return Mono.fromRunnable(() -> System.out.println("Task committed:" + taskId));
    }

    public Flux<String> microsoftTitles() {
        return Flux.just("windows12", "bing2", "office366")
                   .doOnNext(title -> System.out.println("Realising: " + title))
                   .delayElements(Duration.ofMillis(150))
                   .delaySubscription(Duration.ofMillis(250));
    }

    public Flux<String> blizzardTitles() {
        return Flux.just("wow2", "overwatch3", "warcraft4")
                   .doOnNext(title -> System.out.println("Realising: " + title))
                   .delayElements(Duration.ofMillis(150))
                   .delaySubscription(Duration.ofMillis(350));
    }

    public Flux<Chassis> carChassisProducer() {
        return Flux.range(1, 3)
                   .delayElements(Duration.ofMillis(350))
                   .map(i -> new Chassis(UUID.randomUUID()))
                   .doOnNext(c -> System.out.println("Chassis produced! #" + c.vin));
    }

    public Flux<Engine> carEngineProducer() {
        return Flux.range(1, 3)
                   .delayElements(Duration.ofMillis(550))
                   .map(i -> new Engine(UUID.randomUUID()))
                   .doOnNext(e -> System.out.println("Engine produced! #" + e.vin));
    }

    public Mono<String> sourceA() {
        return Mono.just("A");
    }

    public Mono<String> sourceB() {
        return Mono.just("B");
    }

    public static class Message {

        public String metaData = "";
        public String payload = "";

        public Message(String metaData, String payload) {
            this.metaData = metaData;
            this.payload = payload;
        }
    }

    public static class Chassis {

        private static Integer i = 0;
        private Integer seq = 0;
        UUID vin;

        public Chassis(UUID vin) {
            this.vin = vin;
            seq = ++i;
        }

        public Integer getSeqNum() {
            return seq;
        }
    }

    public static class Engine {

        private static Integer i = 0;
        private Integer seq = 0;
        UUID vin;

        public Engine(UUID vin) {
            this.vin = vin;
            seq = ++i;
        }

        public Integer getSeqNum() {
            return seq;
        }
    }

    public static class Car {

        Chassis chassis;
        Engine engine;

        public Car(Chassis chassis, Engine engine) {
            this.chassis = chassis;
            this.engine = engine;
        }
    }

    public static class StreamingConnection {

        public static AtomicBoolean isOpen = new AtomicBoolean();
        public static AtomicBoolean cleanedUp = new AtomicBoolean();

        public static Mono<Flux<String>> startStreaming() {
            return Mono.just(Flux.range(1, 20).map(i -> "Message #" + i)
                       .delayElements(Duration.ofMillis(250))
                       .doOnNext(s -> System.out.println("Sending message: " + s))
                       .doFirst(() -> {
                           System.out.println("Streaming started!");
                           isOpen.set(true);
                       }));
        }

        public static Mono<Void> closeConnection() {
            return Mono.empty().doFirst(() -> {
                System.out.println("Streaming stopped! Cleaning up...");
                cleanedUp.set(true);
            }).then();
        }
    }
}
