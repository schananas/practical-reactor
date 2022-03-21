import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * Don't change anything from this class!
 *
 * @author Stefan Dragisic
 */
public class IntroductionBase {

    public Mono<String> hello_world_service() {
        return Mono.just("Hello World!");
    }

    public Mono<String> unresponsiveService() {
        return Mono.never();
    }

    protected AtomicReference<Boolean> emptyServiceIsCalled = new AtomicReference<>(false);
    public Mono<String> emptyService() {
        return Mono.defer(()-> {
            emptyServiceIsCalled.set(true);
            return Mono.empty();
        });
    }

    public Flux<String> multiResultService() {
        return Flux.just("valid result").concatWith(Flux.error(new RuntimeException("oops, you collected to many, and you broke the service...")));
    }

    protected AtomicReference<Boolean> fortuneTop5ServiceIsCalled = new AtomicReference<>(false);
    public Flux<String> fortuneTop5() {
        return Flux.defer(() -> {
            fortuneTop5ServiceIsCalled.set(true);
            return Flux.just("Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group");
        });
    }



}
