public class BlockingQueue {
    private Object[] items;
    private int size = 0;

    public BlockingQueue(int capacity) {
        this.items = new Object[capacity];
    }

    public synchronized void enqueue(Object o) throws InterruptedException {
        while (size == items.length) {
            wait();
        }
        items[size] = o;
        size++;
        notify();
    }

    public synchronized Object dequeue() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        Object result = items[0];
        for (int i = 0; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[size - 1] = null;
        size--;
        notify();
        return result;
    }

    public synchronized int size() {
        return size;
    }
}
