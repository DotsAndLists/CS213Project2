import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * Manages transactions for a bank account database.
 * Handles account operations such as opening, closing, deposits, withdrawals, and printing account details.
 *
 * @author Arjun Deshpande
 * @author Aryaman Urs
 */
public class TransactionManager {
    private AccountDatabase database = new AccountDatabase();
    /**
     * Runs the transaction manager, processing user commands in a loop.
     * Recognized commands:
     * O - Open account
     * C - Close account
     * D - Deposit
     * W - Withdraw
     * P - Print accounts
     * PA - Print archived accounts
     * PB - Print accounts by branch
     * PH - Print accounts by holder
     * PT - Print accounts by type
     * Q - Quit
     * @author Arjun Deshpande
     */
    public void run() {//Made by arjun
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            StringTokenizer tokenizer = new StringTokenizer(line);
            if (!tokenizer.hasMoreTokens()) continue;
            String command = tokenizer.nextToken();
            try {
                switch (command) {
                    case "O": processOpen(tokenizer); break;
                    case "C": processClose(tokenizer); break;
                    case "D": processDeposit(tokenizer); break;
                    case "W": processWithdraw(tokenizer); break;
                    case "P": database.print(); break;
                    case "PA": database.printArchive(); break;
                    case "PB": database.printByBranch(); break;
                    case "PH": database.printByHolder(); break;
                    case "PT": database.printByType(); break;
                    case "Q":
                        System.out.println("Transaction Manager is terminated.");
                        return;
                    default: System.out.println("Invalid command.");
                }
            } catch (Exception e) {
                System.out.println("Processing error.");
            }
        }
    }
    /**
     * Processes the opening of a new account.
     *
     * @param tokenizer the tokenizer containing account details
     * @author Aryaman Urs
     */
    private void processOpen(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 5) {
            System.out.println("Invalid command!");
            return;
        }
        String typeStr = tokenizer.nextToken();
        AccountType type = AccountType.fromString(typeStr);
        if (type == null) {
            System.out.println(typeStr + " - invalid account type.");
            return;
        }

        String branchStr = tokenizer.nextToken();
        Branch branch = Branch.fromCity(branchStr);
        if (branch == null) {
            System.out.println(branchStr + " - invalid branch.");
            return;
        }

        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dobStr = tokenizer.nextToken();
        Date dob = Date.fromString(dobStr);

        if (dob == null || !dob.isValid()) {
            return;
        }

        String amountStr = tokenizer.nextToken();
        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(amountStr);
            if (initialDeposit <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("For input string: \"" + amountStr + "\" - not a valid amount.");
            return;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        if (database.containsHolderAndType(profile, type)) {
            System.out.println(firstName + " " + lastName + " already has a " + typeStr.toLowerCase() + " account.");
            return;
        }
        AccountNumber accNumber = new AccountNumber(branch, type);
        Account newAccount = new Account(accNumber, profile, initialDeposit);
        database.add(newAccount);
        System.out.println(type.name() + " account " + accNumber + " has been opened.");
    }
    /**
     * Processes the closing of an existing account.
     *
     * @param tokenizer the tokenizer containing account details
     * @author Aryaman Urs
     */
    private void processClose(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 1) {
            System.out.println("Invalid command!");
            return;
        }
        String identifier = tokenizer.nextToken();
        if (identifier.matches("\\d{9}")) { // Case 1: If it's a numeric account number
            AccountNumber number = AccountNumber.fromString(identifier);
            if (number == null) {
                System.out.println(identifier + " - invalid account number.");
                return;
            }
            Account account = database.numFind(number);
            if (account == null) {
                System.out.println(identifier + " account does not exist.");
                return;
            }
            database.remove(account);
            System.out.println(identifier + " is closed and moved to archive; balance set to 0.");
            return;
        }
        String firstName = identifier;
        String lastName = tokenizer.nextToken();
        String dobStr = tokenizer.nextToken();
        Date dob = Date.fromString(dobStr);
        if (dob == null || !dob.isValid()) {
            System.out.println("DOB invalid: " + dobStr + " not a valid calendar date!");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        if (!database.containsHolderAndType(profile, AccountType.CHECKING) &&
                !database.containsHolderAndType(profile, AccountType.SAVINGS) &&
                !database.containsHolderAndType(profile, AccountType.MONEY_MARKET)) {
            System.out.println(firstName + " " + lastName + " " + dobStr + " does not have any accounts in the database.");
            return;
        }
        database.removeByProfile(profile);
        System.out.println("All accounts for " + firstName + " " + lastName + " " + dobStr + " are closed and moved to archive; balance set to 0.");
    }//end method


    /**
     * Processes a deposit transaction for an account.
     *
     * @param tokenizer the tokenizer containing the account number and deposit amount
     * @author Arjun Deshpande
     */
    private void processDeposit(StringTokenizer tokenizer) {//made by Arjun

        if (tokenizer.countTokens() < 2) {
            System.out.println("Invalid command!");
            return;
        }
        String accNumberStr = tokenizer.nextToken();

        String amountStr = tokenizer.nextToken();
        AccountNumber number = AccountNumber.fromString(accNumberStr);
        if (number == null) {
            System.out.println("Invalid account number");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                System.out.println(amount+" - deposit amount cannot be 0 or negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("For input string: \"" + amountStr + "\" - not a valid amount.");
            return;
        }
        Account acc = database.numFind(number);//PROBLEM LINE
        if (acc != null) {
            database.deposit(number, amount);
            System.out.printf("Deposit successful. New balance: $%.2f%n", acc.getBalance());
        } else {
            System.out.println("Account not found");
        }
    }//end methods
    /**
     * Processes a withdrawal transaction for an account.
     *
     * @param tokenizer the tokenizer containing the account number and withdrawal amount
     * @author Arjun Deshpande
     */
    private void processWithdraw(StringTokenizer tokenizer) {//made by arjun
        if (tokenizer.countTokens() < 2) {
            System.out.println("Invalid command!");
            return;
        }
        String accNumberStr = tokenizer.nextToken();
        String amountStr = tokenizer.nextToken();
        AccountNumber number = AccountNumber.fromString(accNumberStr);
        if (number == null) {
            System.out.println("Invalid account number");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                System.out.println(amount+" withdrawal amount cannot be 0 or negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("For input string: \"" + amountStr + "\" - not a valid amount.");
            return;
        }

        Account acc = database.numFind(number);//PROBLEM LINE
        if (acc != null) {
            String status = "";
            boolean success = database.withdraw(number, amount);
            if (!success) {
                System.out.println("Insufficient funds");
                return;
            }

            if (acc.getType() == AccountType.MONEY_MARKET && acc.getBalance() < 2000) {
                acc.getNumber().setType(AccountType.SAVINGS); //Downgrades the acc type
                status = " Account downgraded to Savings";
            }

            System.out.printf("Withdrawal successful. New balance: $%.2f.%s%n",
                    acc.getBalance(), status);
        }
        else {
            System.out.println("Account not found");
        }
    }//end methode
}//end class
