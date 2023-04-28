import reactor.core.publisher.Flux
import java.io.Serializable
import java.util.LinkedList
import java.util.concurrent.atomic.AtomicReference

/**
 * @author Stefan Dragisic
 */
class FilteringSequenceBase {
    fun popular_girl_names_service(): Flux<String> {
        return Flux.just(
            "Olivia", "Emma", "Ava", "Charlotte", "Sophia",
            "Amelia", "Isabella", "Mia", "Evelyn", "Harper",
            "Camila", "Gianna", "Abigail", "Luna", "Ella"
        )
    }

    fun mashed_data_service(): Flux<Serializable> {
        return Flux.just("1", LinkedList<String>(), AtomicReference<String>(), "String.class", String::class.java)
    }

    fun duplicated_records_service(): Flux<String> {
        return Flux.just("1", "2", "1", "3", "4", "5", "3", "3")
    }

    fun fragile_service(): Flux<String> {
        return Flux.just("watch_out").concatWith(Flux.error(RuntimeException("Spiders!")))
    }

    fun number_service(): Flux<Int> {
        return Flux.range(0, 300)
    }
}