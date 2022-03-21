import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author Stefan Dragisic
 */
public class ContextBase {

    public final String HTTP_CORRELATION_ID = "http_correlation_id";

    public Mono<Void> openConnection() {
        return Mono.fromRunnable(() -> System.out.println("Opening connection!"))
                   .delaySubscription(Duration.ofMillis(500))
                   .then();
    }

    public static class Message {

        public String correlationId = "";
        public String payload = "";

        public Message(String correlationId, String payload) {
            this.correlationId = correlationId;
            this.payload = payload;
        }
    }

    public Mono<Page> getPage(int pageNumber) {
        return Mono.just(new Page(pageNumber));
    }

    public static class Page {

        public Integer page = 0;

        public Page(Integer page) {
            if (page == 3) {
                System.out.println("Page 3 is not available!");
                throw new IllegalStateException("Page 3 is not available!");
            }
            this.page = page;
        }

        public Flux<Integer> getResult() {
            return Flux.defer(() -> {
                if (page >= 10) {
                    return Flux.empty();
                } else {
                    return Flux.range(page * 10, 10);
                }
            });
        }
    }
}
