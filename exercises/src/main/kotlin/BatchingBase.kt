import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.List
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Stefan Dragisic
 */
class BatchingBase {
    var diskCounter: AtomicInteger = AtomicInteger(0)
    fun dataStream(): Flux<Byte> {
        return Flux.range(0, 99).map { i -> (i.toString()).toByte() }
    }

    fun writeToDisk(chunk: List<Byte?>): Mono<Void> {
        return Flux.fromIterable(chunk)
            .doFirst { diskCounter.incrementAndGet() }
            .delayElements(Duration.ofMillis(50))
            .doOnNext { s -> System.out.println("Written to disk, chunk size: " + chunk.size) }
            .then()
    }

    fun inputCommandStream(): Flux<Command> {
        return Flux.range(0, 100)
            .map { i -> Command("000000" + i % 10, UUID.randomUUID().toString()) }
    }

    fun sendCommand(command: Command): Mono<Void> {
        return Mono.just(command)
            .doOnNext { s -> System.out.println("Sending command... aggregateId#" + command.aggregateId) }
            .delayElement(Duration.ofMillis(250))
            .doOnNext { s -> System.out.println("Command sent... aggregateId#" + command.aggregateId) }
            .then()
            .subscribeOn(Schedulers.parallel())
    }

    class Command(val aggregateId: String, val payload: String)

    fun metrics(): Flux<Long> {
        return Flux.interval(Duration.ofMillis(95))
    }
}