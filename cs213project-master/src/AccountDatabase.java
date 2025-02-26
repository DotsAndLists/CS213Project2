/**
 * Represents a database of bank accounts with account management capabilities.
 * Handles account storage, transactions, and reporting.
 *
 * @author Arjun Deshpande
 *
 */
public class AccountDatabase {
    //private static final int NOT_FOUND = -1;
    private Account[] accounts;
    private int size;
    private Archive archive;
    //entire class made by arjun
    /**
     * Constructs an empty account database with initial capacity of 4 accounts.
     *
     * @author Arjun Deshpande
     */
    public AccountDatabase() {
        accounts = new Account[4];
        size = 0;
        archive = new Archive();
    }
    /**
     * Finds an account by account number.
     *
     * @param number the account number to search for
     * @return the matching Account object, or null if not found
     * @author Arjun Deshpande
     */
    public Account numFind(AccountNumber number) {
        for (int i = 0; i < size; i++) {
            if (accounts[i].getNumber().equals(number)) {
                return accounts[i];
            }
        }
        return null;
    }//end public method
    /**
     * Finds the index of an account in the database.
     *
     * @param account the account to locate
     * @return index of the account, or -1 if not found
     * @author Arjun Deshpande
     */
    private int find(Account account) {
        for (int i = 0; i < size; i++)
            if (accounts[i].equals(account))
                return i;
        return -1;
    }
    /**
     * Increases the account storage capacity by 4 elements.
     *
     * @author Arjun Deshpande
     */
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        System.arraycopy(accounts, 0, newAccounts, 0, size);
        accounts = newAccounts;
    }
    /**
     * Checks if an account exists in the database.
     *
     * @param account the account to check
     * @return true if account exists, false otherwise
     * @author Arjun Deshpande
     */
    public boolean contains(Account account) {
        return find(account) != -1;
    }//End class (unused?)
    /**
     * Checks if a holder has an account of specific type.
     *
     * @param profile the account holder's profile
     * @param type the account type to check
     * @return true if matching account exists, false otherwise
     * @author Arjun Deshpande
     */
    public boolean containsHolderAndType(Profile profile, AccountType type) {
        for (int i = 0; i < size; i++) {
            Account acc = accounts[i];
            if (acc.getHolder().equals(profile) && acc.getType() == type)
                return true;
        }
        return false;
    }

    /**
     * Adds a new account to the database.
     *
     * @param account the account to add
     * @author Arjun Deshpande
     */
    public void add(Account account) {
        if (size == accounts.length) grow();
        accounts[size++] = account;
    }
    /**
     * Removes an account from the database.
     *
     * @param account the account to remove
     * @author Arjun Deshpande
     */
    public void remove(Account account) {
        int index = find(account);
        if (index == -1) return;
        accounts[index] = accounts[--size];
        accounts[size] = null;
    }
    /**
     * Removes all accounts associated with a profile.
     *
     * @param profile the profile whose accounts should be removed
     * @author Arjun Deshpande
     */
    public void removeByProfile(Profile profile) {
        for (int i = size - 1; i >= 0; i--) {
            Account acc = accounts[i];
            if (acc.getHolder().equals(profile)) {
                remove(acc);
                archive.add(acc);
            }
        }
    }
    /**
     * Withdraws funds from an account.
     *
     * @param number the account number
     * @param amount the amount to withdraw
     * @return true if withdrawal succeeded, false otherwise
     * @author Arjun Deshpande
     */
    public boolean withdraw(AccountNumber number, double amount) {
        for (int i = 0; i < size; i++) {
            Account acc = accounts[i];
            if (acc.getNumber().equals(number)) {
                if (acc.getBalance() < amount) return false;
                acc.withdraw(amount);
                return true;
            }
        }
        return false;
    }
    /**
     * Deposits funds to an account.
     *
     * @param number the account number
     * @param amount the amount to deposit
     * @author Arjun Deshpande
     */
    public void deposit(AccountNumber number, double amount) {
        for (int i = 0; i < size; i++) {
            Account acc = accounts[i];
            if (acc.getNumber().equals(number)) {
                acc.deposit(amount);
                return;
            }
        }
    }
    /**
     * Prints archived accounts.
     *
     * @author Arjun Deshpande
     */
    public void printArchive() { archive.print(); }
    /**
     * Prints accounts sorted by branch.
     *
     * @author Arjun Deshpande
     */
    public void printByBranch() { sortAndPrint(this::compareByBranch); }
    /**
     * Prints accounts sorted by holder.
     *
     * @author Arjun Deshpande
     */
    public void printByHolder() { sortAndPrint(this::compareByHolder); }
    /**
     * Prints accounts sorted by type.
     *
     * @author Arjun Deshpande
     */
    public void printByType() { sortAndPrint(this::compareByType); }
    /**
     * Prints accounts sorted by type.
     *
     * @author Arjun Deshpande
     */
    private void sortAndPrint(java.util.Comparator<Account> comparator) {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++)
                if (comparator.compare(accounts[j], accounts[min]) < 0)
                    min = j;
            swap(i, min);
        }
        print();
    }
    /**
     * Compares two accounts by their branch.
     *
     * @param a1 the first account
     * @param a2 the second account
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     * @author Arjun Deshpande
     */
    private int compareByBranch(Account a1, Account a2) {
        int cmp = a1.getNumber().getBranch().getCounty().compareTo(a2.getNumber().getBranch().getCounty());
        return cmp != 0 ? cmp : a1.getNumber().getBranch().name().compareTo(a2.getNumber().getBranch().name());
    }
    /**
     * Compares two accounts by their holder.
     *
     * @param a1 the first account
     * @param a2 the second account
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     * @author Arjun Deshpande
     */
    private int compareByHolder(Account a1, Account a2) {
        int cmp = a1.getHolder().compareTo(a2.getHolder());
        return cmp != 0 ? cmp : a1.compareTo(a2);
    }
    /**
     * Compares two accounts by their type.
     *
     * @param a1 the first account
     * @param a2 the second account
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     * @author Arjun Deshpande
     */
    private int compareByType(Account a1, Account a2) {
        int cmp = a1.getType().compareTo(a2.getType());//PROBLEM LINE
        return cmp != 0 ? cmp : a1.compareTo(a2);
    }
    /**
     * Swaps two accounts in the database.
     *
     * @param i the index of the first account
     * @param j the index of the second account
     * @author Arjun Deshpande
     */
    private void swap(int i, int j) {
        Account temp = accounts[i];
        accounts[i] = accounts[j];
        accounts[j] = temp;
    }
    /**
     * Prints all accounts in the database.
     *
     * @author Arjun Deshpande
     */
    public void print() {
        if (size == 0) {
            System.out.println("Account database is empty!");
            return;
        }

        System.out.println("*List of accounts in the account database.");
        for (int i = 0; i < size; i++) {
            System.out.println(accounts[i]);
        }
        System.out.println("*end of list.");
    }
}//end klasse