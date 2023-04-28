import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

/**
 * Don't change this file. It's part of the test.
 *
 * @author Stefan Dragisic
 */
class ExecutionControlBase {
    fun readNotifications(): Flux<String> {
        return Flux.just("New SMS message!", "Missed call!", "New email!", "Update available!", "New calendar event!")
    }

    fun semaphore(): Flux<String> {
        return Flux.interval(
            Duration.ofMillis(2250)
        ).map { s -> "go" }.doOnNext { s -> System.out.println("Semaphore says: $s") }
    }

    fun tasks(): Flux<Mono<String>> {
        return Flux.just(Mono.just("1")
            .doOnNext { n -> System.out.println("Executing task #1...") }
            .delayElement(Duration.ofMillis(900))
            .subscribeOn(Schedulers.boundedElastic()),
            Mono.just("2")
                .doOnNext { n -> System.out.println("Executing task #2...") }
                .delayElement(Duration.ofMillis(1000))
                .subscribeOn(Schedulers.boundedElastic()),
            Mono.just("3")
                .doOnNext { n -> System.out.println("Executing task #3...") }
                .delayElement(Duration.ofMillis(800))
                .subscribeOn(Schedulers.boundedElastic())
        )
    }

    fun eventProcessor(): Flux<Event> {
        return Flux.range(0, 500)
            .doOnNext { n -> System.out.println("Processing event #$n") }
            .map { i -> Event(if (i % 2 == 0) "event#:$i" else "", "Event #" + UUID.randomUUID()) }
    }

    var counter: AtomicInteger = AtomicInteger(0)
    fun appendToStore(eventJson: String?): Mono<Void> {
        return Mono.just(eventJson)
            .delayElement(Duration.ofMillis(50))
            .doOnNext { s -> System.out.println("Appending event to store: " + counter.incrementAndGet()) }
            .then()
    }

    class Event(metaData: String, payload: String) {
        var metaData = ""
        var payload = ""

        init {
            this.metaData = metaData
            this.payload = payload
        }
    }

    companion object {
        fun blockingCall() {
            System.out.println("Executing blocking task...")
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}