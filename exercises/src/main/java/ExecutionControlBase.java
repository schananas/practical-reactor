import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Don't change this file. It's part of the test.
 *
 * @author Stefan Dragisic
 */
public class ExecutionControlBase {

    public Flux<String> readNotifications() {
        return Flux.just("New SMS message!", "Missed call!", "New email!", "Update available!", "New calendar event!");
    }

    public Flux<String> semaphore() {
        return Flux.interval(
                Duration.ofMillis(2250)
        ).map(s -> "go").doOnNext(s -> System.out.println("Semaphore says: " + s));
    }

    public Flux<Mono<String>> tasks() {
        return Flux.just(Mono.just("1")
                             .doOnNext(n -> System.out.println("Executing task #1..."))
                             .delayElement(Duration.ofMillis(900))
                             .subscribeOn(Schedulers.boundedElastic()),
                         Mono.just("2")
                             .doOnNext(n -> System.out.println("Executing task #2..."))
                             .delayElement(Duration.ofMillis(1000))
                             .subscribeOn(Schedulers.boundedElastic()),
                         Mono.just("3")
                             .doOnNext(n -> System.out.println("Executing task #3..."))
                             .delayElement(Duration.ofMillis(800))
                             .subscribeOn(Schedulers.boundedElastic())
        );
    }

    public static void blockingCall() {
        System.out.println("Executing blocking task...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Flux<Event> eventProcessor() {
        return Flux.range(0, 500)
                   .doOnNext(n -> System.out.println("Processing event #" + n))
                   .map(i -> new Event(i % 2 == 0 ? "event#:" + i : "", "Event #" + UUID.randomUUID()));
    }

    AtomicInteger counter = new AtomicInteger(0);

    public Mono<Void> appendToStore(String eventJson) {
        return Mono.just(eventJson)
                   .delayElement(Duration.ofMillis(50))
                   .doOnNext(s -> System.out.println("Appending event to store: " + counter.incrementAndGet()))
                   .then();
    }

    public static class Event {

        public String metaData = "";
        public String payload = "";

        public Event(String metaData, String payload) {
            this.metaData = metaData;
            this.payload = payload;
        }
    }
}
