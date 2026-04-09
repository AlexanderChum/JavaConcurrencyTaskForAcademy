import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentBank {
    private final List<BankAccount> accounts = new CopyOnWriteArrayList<>();

    public BankAccount createAccount(int money) {
        BankAccount account = new BankAccount(money);
        accounts.add(account);
        System.out.println("Current amount of money: " + account.getBalance());
        return account;
    }

    public void transfer(BankAccount account1, BankAccount account2, int money) {
        BankAccount firstLock = account1.getId() < account2.getId() ? account1 : account2;
        BankAccount secondLock = account1.getId() < account2.getId() ? account2 : account1;

        synchronized (firstLock) {
            synchronized (secondLock) {
                int newBalance = account1.withdraw(money);
                if (newBalance != -1) {
                    account2.deposit(money);
                    System.out.println("Transferred " + money + " from account " + account1.getId() +
                            " to account " + account2.getId());
                } else {
                    System.out.println("Transfer failed: not enough money in account " + account1.getId());
                }
            }
        }
    }

    public int getTotalBalance() {
        int result = 0;
        for (BankAccount account : accounts) {
            result += account.getBalance();
        }
        return result;
    }
}
