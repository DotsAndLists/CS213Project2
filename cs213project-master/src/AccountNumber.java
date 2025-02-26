import java.util.Random;
/**
 * Represents an account number with a branch, account type, and unique identifier.
 * Provides functionality for generating, comparing, and retrieving account numbers.
 *
 * @author Aryaman Urs
 * @author Arjun Deshpande
 */
public class AccountNumber implements Comparable<AccountNumber> {
    private static final int SEED = 9999; 
    private static final Random RANDOM = new Random(SEED); 

    private Branch branch;
    private AccountType type;
    private String number;
    /**
     * Constructs an AccountNumber with a specified branch and account type.
     * The account number includes a randomly generated 4-digit identifier.
     *
     * @param branch the branch associated with the account
     * @param type the type of the account
     * @author Aryaman Urs
     */
    public AccountNumber(Branch branch, AccountType type) {
        this.branch = branch;
        this.type = type;
        this.number = generateRandomNumber();
    }

    /**
     * Generates a 4-digit random number between 1000 and 9999.
     *
     * @return a randomly generated 4-digit string
     * @author Aryaman Urs
     */
    private String generateRandomNumber() {
        int num = RANDOM.nextInt(9000) + 1000; // Ensures a 4-digit number
        return String.valueOf(num);
    }

    /**
     * Returns the full 9-digit account number in the format:
     * &lt;BranchCode&gt;&lt;AccountType&gt;&lt;4-digit random number&gt;
     *
     * @return the formatted account number as a string
     * @author Aryaman Urs
     */
    @Override
    public String toString() {
        return branch.getBranchCode() + type.getCode() + number;
    }
    /**
     * Compares this account number with another object for equality.
     *
     * @param obj the object to compare with
     * @return true if both account numbers are identical, otherwise false
     * @author Aryaman Urs
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AccountNumber)) return false;
        AccountNumber other = (AccountNumber) obj;
        return this.toString().equals(other.toString());
    }
    /**
     * Compares this account number with another for ordering.
     *
     * @param other the account number to compare against
     * @return a negative integer, zero, or a positive integer as this account number
     *         is less than, equal to, or greater than the specified account number
     * @author Aryaman Urs
     */
    @Override
    public int compareTo(AccountNumber other) {
        return this.toString().compareTo(other.toString());
    }

    // Getters
    /**
     * Retrieves the branch associated with the account number.
     *
     * @return the branch of the account
     * @author Aryaman Urs
     */
    public Branch getBranch() {
        return branch;
    }
    /**
     * Retrieves the account type of the account number.
     *
     * @return the account type
     * @author Aryaman Urs
     */
    public AccountType getType() {
        return type;
    }
    /**
     * Retrieves the unique 4-digit identifier of the account number.
     *
     * @return the last 4 digits of the account number
     * @author Aryaman Urs
     */
    public String getNumber() {
        return number;
    }

    //from string made by arjun
    /**
     * Converts a string representation of an account number into an AccountNumber object.
     * The input string must be exactly 9 characters long and contain valid branch and account type codes.
     *
     * @param accountNumber the string representation of the account number
     * @return an AccountNumber object if the input is valid, otherwise null
     * @author Arjun Deshpande
     */
    public static AccountNumber fromString(String accountNumber) {
        if (accountNumber.length() != 9) return null;
        String branchCode = accountNumber.substring(0, 3);
        String typeCode = accountNumber.substring(3, 5);
        String numberPart = accountNumber.substring(5);
        Branch branch = null; //FIND BRANCH
        for (Branch b : Branch.values()) {
            if (b.getBranchCode().equals(branchCode)) {
                branch = b;
                break;
            }
        }
        if (branch == null) return null;
        AccountType type = null;//FIND ACC TYPE
        for (AccountType t : AccountType.values()) {
            if (t.getCode().equals(typeCode)) {
                type = t;
                break;
            }
        }
        if (type == null) return null;
        AccountNumber accNum = new AccountNumber(branch, type);// Create account number with override
        accNum.number = numberPart; // Direct private field access within class
        return accNum;
    }//end methode

    //setType made by Arjun
    /**
     * Sets the type of the account.
     * This method is used when downgrading an account type.
     *
     * @param newType the new account type to set
     * @author Arjun Deshpande
     */
    public void setType(AccountType newType) { //FOR USAGE IN ACCOUNT DOWNGRADE
        this.type = newType;
    }//end methode
    /**
     * Testbed for the AccountNumber class.
     * Demonstrates the creation and comparison of account numbers.
     *
     * @param args command-line arguments (not used)
     * @author Aryaman Urs
     */
    public static void main(String[] args) {
        AccountNumber acc1 = new AccountNumber(Branch.EDISON, AccountType.CHECKING);
        AccountNumber acc2 = new AccountNumber(Branch.PISCATAWAY, AccountType.SAVINGS);
        AccountNumber acc3 = new AccountNumber(Branch.EDISON, AccountType.CHECKING);

        System.out.println("Account 1: " + acc1);
        System.out.println("Account 2: " + acc2);
        System.out.println("Account 3: " + acc3);

        // Testing equals()
        System.out.println("Account 1 equals Account 2? " + acc1.equals(acc2)); // Should be false
        System.out.println("Account 1 equals Account 3? " + acc1.equals(acc3)); // might be true (due to fixed seed)

        // Testing compareTo()
        System.out.println("Compare Account 1 and Account 2: " + acc1.compareTo(acc2));
        System.out.println("Compare Account 1 and Account 3: " + acc1.compareTo(acc3));
        System.out.println("Compare Account 1 and Account 3: " + acc1.getBranch());
    }//end testbed
}//end class
