import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author Stefan Dragisic
 */
class TransformingSequenceBase {
    fun numerical_service(): Flux<Int> {
        return Flux.range(1, 10)
    }

    fun object_service(): Flux<String> {
        return Flux.just("1", "2", "3", "4", "5")
    }

    fun numerical_service_2(): Flux<Int> {
        return Flux.just(100, -1, 0, 78, 1)
    }

    fun maybe_service(): Mono<String> {
        return Mono.empty()
    }
}