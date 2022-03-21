import reactor.core.publisher.Flux;
import reactor.test.publisher.TestPublisher;

import java.util.UUID;

/**
 * @author Stefan Dragisic
 */
public class BackpressureBase {

    TestPublisher<String> pub1 = TestPublisher.create();

    public Flux<String> messageStream1() {
        return pub1.flux();
    }

    TestPublisher<String> pub2 = TestPublisher.create();

    public Flux<String> messageStream2() {
        return pub2.flux();
    }

    TestPublisher<String> pub3 = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW);

    public Flux<String> messageStream3() {
        return pub3.flux();
    }

    TestPublisher<String> pub4 = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW);

    public Flux<String> messageStream4() {
        return pub4.flux();
    }

    public Flux<String> remoteMessageProducer() {
        return Flux.generate(s -> s.next("MESSAGE#" + UUID.randomUUID()));
    }
}
