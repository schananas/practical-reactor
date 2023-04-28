import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Stefan Dragisic
 */
class CombiningPublishersBase {
    var taskCounter: AtomicInteger = AtomicInteger(0)
    var localCacheCalled: AtomicBoolean = AtomicBoolean(false)
    var consumedSpamCounter: AtomicInteger = AtomicInteger(0)
    var fileOpened: AtomicBoolean = AtomicBoolean(false)
    var writtenToFile: AtomicBoolean = AtomicBoolean(false)
    var fileClosed: AtomicBoolean = AtomicBoolean(false)
    var committedTasksCounter: AtomicInteger = AtomicInteger(0)
    val currentUser: Mono<String>
        get() = Mono.just("user123")

    fun getUserEmail(user: String): Mono<String> {
        return Mono.fromSupplier { "$user@gmail.com" }
    }

    fun taskExecutor(): Flux<Mono<Void>> {
        return Flux.range(1, 10) //.delayElements(Duration.ofMillis(250))
            .map { i ->
                Mono.fromRunnable<Void> {
                    System.out.println("Executing task: #$i")
                    taskCounter.incrementAndGet()
                }.subscribeOn(Schedulers.parallel())
            }
    }

    fun streamingService(): Mono<Flux<Message>> {
        return Mono.just(Flux.range(1, 10)
            .delayElements(Duration.ofMillis(250))
            .map { i -> Message("chunk:$i", UUID.randomUUID().toString()) }
            .doOnNext { msg -> System.out.println("-> msg#" + msg.metaData) }
            .doOnSubscribe { s -> System.out.println("Streaming started...") }
            .delaySubscription(Duration.ofMillis(750))
            .doOnComplete { System.out.println("Streaming finished!") })
            .doOnSubscribe { s -> System.out.println("Connecting to the service...") }
    }

    fun numberService1(): Flux<Int> {
        return Flux.range(1, 3).doOnNext(System.out::println)
    }

    fun numberService2(): Flux<Int> {
        return Flux.range(4, 4).doOnNext(System.out::println)
    }

    fun listAllUsers(): Flux<String> {
        return Flux.range(1, 10)
            .map { i -> "user$i" }
    }

    val stocksLocalCache: Flux<String>
        get() = Flux.defer {
            System.out.println("(LocalCache) No stocks found in local cache!")
            localCacheCalled.set(true)
            Flux.empty()
        }
    val stocksRest: Flux<String>
        get() = Flux.range(10, 6)
            .map { i -> i.toString() + "$" }
            .doOnNext { n -> System.out.println("(REST) Got stock, price: $n") }
            .delaySubscription(Duration.ofMillis(100))
    val stocksGrpc: Flux<String>
        get() = Flux.range(1, 5)
            .map { i -> i.toString() + "$" }
            .doOnNext { n -> System.out.println("(GRPC) Got stock, price: $n") }
            .delaySubscription(Duration.ofMillis(30))

    fun mailBoxPrimary(): Flux<Message> {
        return Flux.range(1, 3)
            .map { i -> Message("spam", "0x$i") }
            .doOnNext { n -> System.out.println("Message[spam, 0x" + n.payload + "]") }
            .doOnNext { n -> consumedSpamCounter.incrementAndGet() }
    }

    fun mailBoxSecondary(): Flux<Message> {
        return Flux.range(1, 2)
            .map { i -> Message("job-offer", "please join as in google!") }
            .doOnNext { n -> System.out.println("Message[job-offer, please join as in google!]") }
    }

    fun userSearchInput(): Flux<String> {
        return Flux.just("r", "re", "rea", "reac", "reac", "react", "reacto", "reactor")
            .concatWith(Flux.just("reactive").delaySubscription(Duration.ofMillis(500)))
            .doOnNext { n -> System.out.println("Typed: $n") }
    }

    fun autoComplete(word: String): Mono<String> {
        return Mono.just("$word project")
            .doOnNext { n -> System.out.println("Suggestion: $n") }
            .delaySubscription(Duration.ofMillis(100))
    }

    fun openFile(): Mono<Void> {
        return Mono.fromRunnable<Void> {
            fileOpened.set(true)
            System.out.println("Opening file...")
        }.delaySubscription(Duration.ofMillis(100))
    }

    fun writeToFile(content: String): Mono<Void> {
        return Mono.fromRunnable<Void> {
            writtenToFile.set(true)
            System.out.println("Writing: $content")
        }.delaySubscription(Duration.ofMillis(1000))
    }

    fun readFile(): Flux<String> {
        return Flux.defer {
            if (fileOpened.get()) {
                return@defer Flux.just("0x1", "0x2", "0x3")
            } else {
                return@defer Flux.error(IllegalStateException("File is not opened!"))
            }
        }.doOnNext { n -> System.out.println("Next line: $n") }
    }

    fun closeFile(): Mono<Void> {
        return Mono.fromRunnable<Void?> {
            fileClosed.set(true)
            System.out.println("File closed!")
        }.delaySubscription(Duration.ofMillis(500))
    }

    fun tasksToExecute(): Flux<Mono<String>> {
        return Flux.range(1, 3)
            .map { i ->
                Mono.fromSupplier {
                    System.out.println("Executing task: #$i")
                    "task#$i"
                }
                    .delaySubscription(Duration.ofMillis(250))
                    .subscribeOn(Schedulers.parallel())
            }
    }

    fun commitTask(taskId: String): Mono<Void> {
        committedTasksCounter.incrementAndGet()
        return Mono.fromRunnable { System.out.println("Task committed:$taskId") }
    }

    fun microsoftTitles(): Flux<String> {
        return Flux.just("windows12", "bing2", "office366")
            .doOnNext { title -> System.out.println("Realising: $title") }
            .delayElements(Duration.ofMillis(150))
            .delaySubscription(Duration.ofMillis(250))
    }

    fun blizzardTitles(): Flux<String> {
        return Flux.just("wow2", "overwatch3", "warcraft4")
            .doOnNext { title -> System.out.println("Realising: $title") }
            .delayElements(Duration.ofMillis(150))
            .delaySubscription(Duration.ofMillis(350))
    }

    fun carChassisProducer(): Flux<Chassis> {
        return Flux.range(1, 3)
            .delayElements(Duration.ofMillis(350))
            .map { i -> Chassis(UUID.randomUUID()) }
            .doOnNext { c -> System.out.println("Chassis produced! #" + c.vin) }
    }

    fun carEngineProducer(): Flux<Engine> {
        return Flux.range(1, 3)
            .delayElements(Duration.ofMillis(550))
            .map { i -> Engine(UUID.randomUUID()) }
            .doOnNext { e -> System.out.println("Engine produced! #" + e.vin) }
    }

    fun sourceA(): Mono<String> {
        return Mono.just("A")
    }

    fun sourceB(): Mono<String> {
        return Mono.just("B")
    }

    class Message(metaData: String, payload: String) {
        var metaData = ""
        var payload = ""

        init {
            this.metaData = metaData
            this.payload = payload
        }
    }

    class Chassis(vin: UUID) {
        private var seq: Int = 0
        var vin: UUID

        init {
            this.vin = vin
            seq = ++i
        }

        val seqNum: Int
            get() = seq

        companion object {
            private var i: Int = 0
        }
    }

    class Engine(vin: UUID) {
        private var seq: Int = 0
        var vin: UUID

        init {
            this.vin = vin
            seq = ++i
        }

        val seqNum: Int
            get() = seq

        companion object {
            private var i: Int = 0
        }
    }

    class Car(var chassis: Chassis, var engine: Engine)
    object StreamingConnection {
        var isOpen: AtomicBoolean = AtomicBoolean()
        var cleanedUp: AtomicBoolean = AtomicBoolean()
        fun startStreaming(): Mono<Flux<String>> {
            return Mono.just(Flux.range(1, 20).map { i -> "Message #$i" }
                .delayElements(Duration.ofMillis(250))
                .doOnNext { s -> System.out.println("Sending message: $s") }
                .doFirst {
                    System.out.println("Streaming started!")
                    isOpen.set(true)
                })
        }

        fun closeConnection(): Mono<Void> {
            return Mono.empty<Void>().doFirst {
                System.out.println("Streaming stopped! Cleaning up...")
                cleanedUp.set(true)
            }.then()
        }
    }
}