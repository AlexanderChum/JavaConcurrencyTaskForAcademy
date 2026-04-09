import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    private final int amountOfTasks; //по тестовому коду мы передаем число задач в конструктор и затем делаем то же
    //самое в метод, поэтому оставил оба числа

    public ComplexTaskExecutor(int numberOfTasks) {
        this.amountOfTasks = numberOfTasks;
    }

    public void executeTasks(int numberOfTasks) {
        List<Integer> results = new CopyOnWriteArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("All " + numberOfTasks + " tasks completed. Summarizing results..");
            int total = 0;
            for (int value : results) {
                total += value;
            }
            System.out.println("Summarized result: " + total);
        });

        for (int i = 0; i < numberOfTasks; i++) {
            final int taskIndex = i;
            ComplexTask task = new ComplexTask();

            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " task " + taskIndex +
                            " waiting at barrier...");

                    int result = task.execute();
                    results.add(result);
                    barrier.await();

                    System.out.println(Thread.currentThread().getName() + " task " + taskIndex +
                            " passed the barrier!");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    System.out.println(Thread.currentThread().getName() + " barrier was broken");
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
