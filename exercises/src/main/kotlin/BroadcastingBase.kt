import reactor.core.publisher.Flux
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Stefan Dragisic
 */
class BroadcastingBase {
    fun systemUpdates(): Flux<String> {
        return Flux.just("RESTARTED", "UNHEALTHY", "HEALTHY", "DISK_SPACE_LOW", "OOM_DETECTED", "CRASHED", "UNKNOWN")
            .delayElements(Duration.ofSeconds(1))
            .doOnNext { n -> System.out.println("Broadcast update: $n") }
    }

    var counter: AtomicInteger = AtomicInteger(0)
    fun messageStream(): Flux<Message> {
        return Flux.generate { sink ->
            val id: Int = counter.getAndIncrement()
            sink.next(id)
        }
            .map { i -> Message("user#$i", "payload#$i") }
            .delayElements(Duration.ofMillis(250))
            .take(5)
    }

    class Message(user: String, payload: String) {
        var user = ""
        var payload = ""

        init {
            this.user = user
            this.payload = payload
        }
    }
}