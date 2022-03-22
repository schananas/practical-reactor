import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In this chapter we will learn difference between hot and cold publishers,
 * how to split a publisher into multiple and how to keep history so late subscriber don't miss any updates.
 *
 * Read first:
 *
 * https://projectreactor.io/docs/core/release/reference/#reactor.hotCold
 * https://projectreactor.io/docs/core/release/reference/#which.multicasting
 * https://projectreactor.io/docs/core/release/reference/#advanced-broadcast-multiple-subscribers-connectableflux
 *
 * Useful documentation:
 *
 * https://projectreactor.io/docs/core/release/reference/#which-operator
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 *
 * @author Stefan Dragisic
 */
public class c12_Broadcasting extends BroadcastingBase {

    /**
     * Split incoming message stream into two streams, one contain user that sent message and second that contains
     * message payload.
     */
    @Test
    public void sharing_is_caring() throws InterruptedException {
        Flux<Message> messages = messageStream()
                //todo: do your changes here
                ;

        //don't change code below
        Flux<String> userStream = messages.map(m -> m.user);
        Flux<String> payloadStream = messages.map(m -> m.payload);

        CopyOnWriteArrayList<String> metaData = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> payload = new CopyOnWriteArrayList<>();

        userStream.doOnNext(n -> System.out.println("User: " + n)).subscribe(metaData::add);
        payloadStream.doOnNext(n -> System.out.println("Payload: " + n)).subscribe(payload::add);

        Thread.sleep(3000);

        Assertions.assertEquals(Arrays.asList("user#0", "user#1", "user#2", "user#3", "user#4"), metaData);
        Assertions.assertEquals(Arrays.asList("payload#0", "payload#1", "payload#2", "payload#3", "payload#4"),
                                payload);
    }

    /**
     * Since two subscribers are interested in the updates, which are coming from same source, convert `updates` stream
     * to from cold to hot source.
     * Answer: What is the difference between hot and cold publisher? Why does won't .share() work in this case?
     */
    @Test
    public void hot_vs_cold() {
        Flux<String> updates = systemUpdates()
                //todo: do your changes here
                ;

        //subscriber 1
        StepVerifier.create(updates.take(3).doOnNext(n -> System.out.println("subscriber 1 got: " + n)))
                    .expectNext("RESTARTED", "UNHEALTHY", "HEALTHY")
                    .verifyComplete();

        //subscriber 2
        StepVerifier.create(updates.take(4).doOnNext(n -> System.out.println("subscriber 2 got: " + n)))
                    .expectNext("DISK_SPACE_LOW", "OOM_DETECTED", "CRASHED", "UNKNOWN")
                    .verifyComplete();
    }

    /**
     * In previous exercise second subscriber subscribed to update later, and it missed some updates. Adapt previous
     * solution so second subscriber will get all updates, even the one's that were broadcaster before its
     * subscription.
     */
    @Test
    public void history_lesson() {
        Flux<String> updates = systemUpdates()
                //todo: do your changes here
                ;

        //subscriber 1
        StepVerifier.create(updates.take(3).doOnNext(n -> System.out.println("subscriber 1 got: " + n)))
                    .expectNext("RESTARTED", "UNHEALTHY", "HEALTHY")
                    .verifyComplete();

        //subscriber 2
        StepVerifier.create(updates.take(5).doOnNext(n -> System.out.println("subscriber 2 got: " + n)))
                    .expectNext("RESTARTED", "UNHEALTHY", "HEALTHY", "DISK_SPACE_LOW", "OOM_DETECTED")
                    .verifyComplete();
    }

}
