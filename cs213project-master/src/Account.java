/**
 * Represents a bank account with an account number, holder profile, and balance.
 *
 * @author Arjun Deshpande
 * @author Aryaman Urs
 */
public class Account implements Comparable<Account> {
    private AccountNumber number;
    private Profile holder;
    private double balance;
    /**
     * Constructs a new Account with the specified account number, holder, and initial balance.
     *
     * @param number the account number
     * @param holder the profile of the account holder
     * @param balance the initial balance of the account
     * @author Aryaman Urs
     */
    public Account(AccountNumber number, Profile holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }
    /**
     * Deposits the specified amount into the account.
     *
     * @param amount the amount to deposit, must be positive
     * @author Aryaman Urs
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            // System.out.println("Deposit amount must be positive.");
        }
    }
    /**
     * Withdraws the specified amount from the account.
     * @author Aryaman Urs
     * @param amount the amount to withdraw, must be positive and less than or equal to the balance
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            // System.out.println("Insufficient balance or invalid withdrawal amount.");
        }
    }
    /**
     * Checks if this account is equal to another object.
     * @author Aryaman Urs
     * @param obj the object to compare
     * @return true if the object is an Account with the same account number; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        return this.number.equals(other.number);
    }
    /**
     * Compares this account to another account based on account number.
     * @author Aryaman Urs
     * @param other the other account to compare
     * @return a negative integer, zero, or a positive integer as this account is less than,
     *         equal to, or greater than the specified account
     */
    @Override
    public int compareTo(Account other) {
        return this.number.compareTo(other.number);
    }
    /**
     * Returns a string representation of the account.
     * @author Aryaman Urs
     * @return a formatted string with account details
     */
    @Override
    public String toString() {
        return String.format(
            "Account#[%s] Holder[%s] Balance[$%.2f] Branch [%s]",
            number, holder, balance, number.getBranch().name()
        );
    }

    // Getters
    /**
     * Gets the account number.
     * @author Aryaman Urs
     * @return the account number
     */
    public AccountNumber getNumber() {
        return number;
    }
    /**
     * Gets the account holder's profile.
     * @author Aryaman Urs
     * @return the profile of the account holder
     */
    public Profile getHolder() {
        return holder;
    }
    /**
     * Gets the current balance of the account.
     * @author Aryaman Urs
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }


    /**
     * Obtains the account type through the Account Class
     * @author Arjun Deshpande
     * @return this.number.getType();
     */
    public AccountType getType() {
        return this.number.getType();
    }//getType

    /**
     * Testbed main method to demonstrate account functionality.
     * @author Aryaman Urs
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Sample accounts
        Profile john = new Profile("John", "Doe", new Date(2000, 2, 19));
        Profile jane = new Profile("Jane", "Smith", new Date(1995, 8, 25));
        AccountNumber accNum1 = new AccountNumber(Branch.BRIDGEWATER, AccountType.CHECKING);
        AccountNumber accNum2 = new AccountNumber(Branch.EDISON, AccountType.SAVINGS);
        Account acc1 = new Account(accNum1, john, 600.00);
        Account acc2 = new Account(accNum2, jane, 1200.00);
        // Testing toString()
        System.out.println("Account 1: " + acc1);
        System.out.println("Account 2: " + acc2);

        // Testing deposit()
        acc1.deposit(100);
        System.out.println("After depositing $100: " + acc1);
        // Testing withdraw()
        acc1.withdraw(50);
        System.out.println("After withdrawing $50: " + acc1);
        // Testing equals()
        System.out.println("Account 1 equals Account 2? " + acc1.equals(acc2)); // Should be false
        // Testing compareTo()
        System.out.println("Compare Account 1 and Account 2: " + acc1.compareTo(acc2));
    }
}
