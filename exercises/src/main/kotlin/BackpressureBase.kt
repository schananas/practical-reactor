import reactor.core.publisher.Flux
import reactor.test.publisher.TestPublisher
import java.util.UUID

/**
 * @author Stefan Dragisic
 */
class BackpressureBase {
    var pub1: TestPublisher<String> = TestPublisher.create()
    fun messageStream1(): Flux<String> {
        return pub1.flux()
    }

    var pub2: TestPublisher<String> = TestPublisher.create()
    fun messageStream2(): Flux<String> {
        return pub2.flux()
    }

    var pub3: TestPublisher<String> = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW)
    fun messageStream3(): Flux<String> {
        return pub3.flux()
    }

    var pub4: TestPublisher<String> = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW)
    fun messageStream4(): Flux<String> {
        return pub4.flux()
    }

    fun remoteMessageProducer(): Flux<String> {
        return Flux.generate { s -> s.next("MESSAGE#" + UUID.randomUUID()) }
    }
}