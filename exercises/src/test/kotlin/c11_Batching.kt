//import org.junit.jupiter.api.*
///**
// * Another way of controlling amount of data flowing is batching.
// * Reactor provides three batching strategies: grouping, windowing, and buffering.
// *
// * Read first:
// *
// * https://projectreactor.io/docs/core/release/reference/#advanced-three-sorts-batching
// * https://projectreactor.io/docs/core/release/reference/#which.window
// *
// * Useful documentation:
// *
// * https://projectreactor.io/docs/core/release/reference/#which-operator
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
// * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
// *
// * @author Stefan Dragisic
// */
//class c11_Batching : BatchingBase() {
//    /**
//     * To optimize disk writing, write data in batches of max 10 items, per batch.
//     */
//    @Test
//    fun batch_writer() {
//        //todo do your changes here
//        val dataStream: Flux<Void>? = null
//        dataStream()
//        writeToDisk(null)
//
//        //do not change the code below
//        StepVerifier.create(dataStream)
//            .verifyComplete()
//        Assertions.assertEquals(10, diskCounter.get())
//    }
//
//    /**
//     * You are implementing a command gateway in CQRS based system. Each command belongs to an aggregate and has `aggregateId`.
//     * All commands that belong to the same aggregate needs to be sent sequentially, after previous command was sent, to
//     * prevent aggregate concurrency issue.
//     * But commands that belong to different aggregates can and should be sent in parallel.
//     * Implement this behaviour by using `GroupedFlux`, and knowledge gained from the previous exercises.
//     */
//    @Test
//    fun command_gateway() {
//        //todo: implement your changes here
//        val processCommands: Flux<Void>? = null
//        inputCommandStream()
//        sendCommand(null)
//
//        //do not change the code below
//        val duration: Duration = StepVerifier.create(processCommands)
//            .verifyComplete()
//        Assertions.assertTrue(duration.getSeconds() <= 3, "Expected to complete in less than 3 seconds")
//    }
//
//    /**
//     * You are implementing time-series database. You need to implement `sum over time` operator. Calculate sum of all
//     * metric readings that have been published during one second.
//     */
//    @Test
//    fun sum_over_time() {
//        val metrics: Flux<Long> = metrics() //todo: implement your changes here
//            .take(10)
//        StepVerifier.create(metrics)
//            .expectNext(45L, 165L, 255L, 396L, 465L, 627L, 675L, 858L, 885L, 1089L)
//            .verifyComplete()
//    }
//}