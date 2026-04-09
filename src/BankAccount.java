import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {
    private final int id;
    private final AtomicInteger balance;
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    public BankAccount(int balance) {
        this.balance = new AtomicInteger(balance);
        this.id = idGenerator.incrementAndGet();
    }

    public int deposit(int money) {
        return this.balance.addAndGet(money);
    }

    public int withdraw(int money) {
        if (balance.get() < money) {
            System.out.println("Not enough money");
            return -1;
        } else {
            return this.balance.addAndGet((-1) * money);
        }
    }

    public int getBalance() {
        return balance.get();
    }

    public int getId() {
        return id;
    }
}
