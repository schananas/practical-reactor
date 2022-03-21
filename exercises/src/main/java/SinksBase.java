import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Stefan Dragisic
 */
public class SinksBase {

    public void submitOperation(Runnable operation) {
        Executors.newScheduledThreadPool(1).schedule(operation, 5, TimeUnit.SECONDS);
    }

    //don't change me
    public void doSomeWork() {
        System.out.println("Doing some work");
    }

    //don't change me
    public List<Integer> get_measures_readings() {
        System.out.println("Reading measurements...");
        System.out.println("Got: 0x0800");
        System.out.println("Got: 0x0B64");
        System.out.println("Got: 0x0504");
        return Arrays.asList(0x0800, 0x0B64, 0x0504);
    }
}
