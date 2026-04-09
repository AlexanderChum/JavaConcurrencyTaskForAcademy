import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask {
    private final int taskId;
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    public ComplexTask() {
        this.taskId = idGenerator.incrementAndGet();
    }

    public int execute() {
        int workingTimeImitation = ThreadLocalRandom.current().nextInt(1, 1000);
        int result = ThreadLocalRandom.current().nextInt(1, 100);

        try {
            Thread.sleep(workingTimeImitation);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Thread.currentThread().getName() + " Task " + taskId +
                " finished with result: " + result);

        return result;
    }
}