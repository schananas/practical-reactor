import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicReference

/**
 *
 * Don't change anything from this class!
 *
 * @author Stefan Dragisic
 */
open class IntroductionBase {
    fun hello_world_service(): Mono<String> {
        return Mono.just("Hello World!")
    }

    fun unresponsiveService(): Mono<String> {
        return Mono.never()
    }

    protected var emptyServiceIsCalled: AtomicReference<Boolean> = AtomicReference(false)
    fun emptyService(): Mono<String> {
        return Mono.defer {
            emptyServiceIsCalled.set(true)
            Mono.empty()
        }
    }

    fun multiResultService(): Flux<String> {
        return Flux.just("valid result")
            .concatWith(Flux.error(RuntimeException("oops, you collected to many, and you broke the service...")))
    }

    protected var fortuneTop5ServiceIsCalled: AtomicReference<Boolean> = AtomicReference(false)
    fun fortuneTop5(): Flux<String> {
        return Flux.defer {
            fortuneTop5ServiceIsCalled.set(true)
            Flux.just("Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group")
        }
    }
}