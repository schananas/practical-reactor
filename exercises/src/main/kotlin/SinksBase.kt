import java.util.Arrays
import java.util.List
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Stefan Dragisic
 */
class SinksBase {
    fun submitOperation(operation: Runnable?) {
        Executors.newScheduledThreadPool(1).schedule(operation, 5, TimeUnit.SECONDS)
    }

    //don't change me
    fun doSomeWork() {
        System.out.println("Doing some work")
    }

    //don't change me
    fun get_measures_readings(): kotlin.collections.List<Int> {
        System.out.println("Reading measurements...")
        System.out.println("Got: 0x0800")
        System.out.println("Got: 0x0B64")
        System.out.println("Got: 0x0504")
        return listOf(0x0800, 0x0B64, 0x0504)
    }
}