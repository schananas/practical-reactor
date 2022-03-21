import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stefan Dragisic
 */
public class BatchingBase {

    AtomicInteger diskCounter = new AtomicInteger(0);

    public Flux<Byte> dataStream() {
        return Flux.range(0, 99).map(i -> Byte.parseByte(String.valueOf(i)));
    }

    public Mono<Void> writeToDisk(List<Byte> chunk) {
        return Flux.fromIterable(chunk)
                   .doFirst(() -> diskCounter.incrementAndGet())
                   .delayElements(Duration.ofMillis(50))
                   .doOnNext(s -> System.out.println("Written to disk, chunk size: " + chunk.size()))
                   .then();
    }


    public Flux<Command> inputCommandStream() {
        return Flux.range(0, 100)
                   .map(i -> new Command("000000" + i % 10, UUID.randomUUID().toString()));
    }

    public Mono<Void> sendCommand(Command command) {
        return Mono.just(command)
                   .doOnNext(s -> System.out.println("Sending command... aggregateId#" + command.getAggregateId()))
                   .delayElement(Duration.ofMillis(250))
                   .doOnNext(s -> System.out.println("Command sent... aggregateId#" + command.getAggregateId()))
                   .then()
                   .subscribeOn(Schedulers.parallel());
    }

    public static class Command {

        private final String aggregateId;
        private final String payload;

        public Command(String aggregateId, String payload) {
            this.aggregateId = aggregateId;
            this.payload = payload;
        }

        public String getAggregateId() {
            return aggregateId;
        }

        public String getPayload() {
            return payload;
        }
    }

    public Flux<Long> metrics() {
        return Flux.interval(Duration.ofMillis(95));
    }
}
