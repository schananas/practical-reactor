import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

/**
 * @author Stefan Dragisic
 */
class ContextBase {
    val HTTP_CORRELATION_ID = "http_correlation_id"
    fun openConnection(): Mono<Void> {
        return Mono.fromRunnable<Void> { System.out.println("Opening connection!") }
            .delaySubscription(Duration.ofMillis(500))
            .then()
    }

    class Message(correlationId: String, payload: String) {
        var correlationId = ""
        var payload = ""

        init {
            this.correlationId = correlationId
            this.payload = payload
        }
    }

    fun getPage(pageNumber: Int): Mono<Page> {
        return Mono.just(Page(pageNumber))
    }

    class Page(page: Int) {
        var page: Int = 0

        init {
            if (page == 3) {
                System.out.println("Page 3 is not available!")
                throw IllegalStateException("Page 3 is not available!")
            }
            this.page = page
        }

        val result: Flux<Int>
            get() = Flux.defer {
                if (page >= 10) {
                    return@defer Flux.empty()
                } else {
                    return@defer Flux.range(page * 10, 10)
                }
            }
    }
}