import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Stefan Dragisic
 */
public class TransformingSequenceBase {

    public Flux<Integer> numerical_service() {
        return Flux.range(1, 10);
    }

    public Flux<Object> object_service() {
        return Flux.just("1", "2", "3", "4", "5");
    }

    public Flux<Integer> numerical_service_2() {
        return Flux.just(100, -1, 0, 78, 1);
    }

    public Mono<String> maybe_service() {
        return Mono.empty();
    }

}
