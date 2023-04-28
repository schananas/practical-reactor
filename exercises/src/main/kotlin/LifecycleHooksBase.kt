import reactor.core.publisher.Flux
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom

/**
 * @author Stefan Dragisic
 */
class LifecycleHooksBase {
    fun room_temperature_service(): Flux<Int> {
        return Flux.interval(Duration.ofMillis(100), Duration.ofMillis(100))
            .take(20)
            .map { i -> ThreadLocalRandom.current().nextInt(10, 30) }
    }
}