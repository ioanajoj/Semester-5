package en.ubb.lab1.domain;


/**
 * @author joj on 10/2/2019
 **/
public class OperationRecord {

    private static int counter;

    private final int serialNumber;

    private final BankAccount sourceAccount;

    private final BankAccount destinationAccount;

    private final int amount;

    public OperationRecord(final BankAccount sourceAccount, final BankAccount destinationAccount, final int amount) {
        this.serialNumber = ++counter;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }

    public int getAmount() {
        return amount;
    }

    public String call() {
        if(sourceAccount.getId() < destinationAccount.getId()) {
            sourceAccount.addOperationRecord(this);
            destinationAccount.addOperationRecord(this);
        }
        else {
            destinationAccount.addOperationRecord(this);
            sourceAccount.addOperationRecord(this);
        }
        return "Extracted amount: " + this.amount + " from account " +
                this.sourceAccount.getId() + " to account: " + this.destinationAccount.getId();
    }

    @Override
    public String toString() {
        return "Operation " + serialNumber +
                " from account " + sourceAccount.getId() +
                " to account " + destinationAccount.getId() +
                " with amount: " + amount;
    }
}
