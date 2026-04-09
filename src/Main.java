public class Main {
    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue(5);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    queue.enqueue(i);
                    System.out.println("Напиши какую цифру ты сохранил " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    Object o = queue.dequeue();
                    System.out.println("Напиши какую цифру ты достал " + o);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
