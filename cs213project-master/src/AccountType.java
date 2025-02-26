/**
 * Represents different types of bank accounts, each associated with a unique code.
 *
 * @author Aryaman Urs
 */
public enum AccountType {
    CHECKING("01"),
    SAVINGS("02"),
    MONEY_MARKET("03");

    private final String code;
    /**
     * Constructs an AccountType with a specific code.
     *
     * @param code the unique code representing the account type
     * @author Aryaman Urs
     */
    AccountType(String code) {
        this.code = code;
    }
    /**
     * Retrieves the code associated with the account type.
     *
     * @return the account type code as a string
     * @author Aryaman Urs
     */
    public String getCode() {
        return code;
    }
    /**
     * Returns a string representation of the account type, including its name and code.
     *
     * @return a formatted string representation of the account type
     * @author Aryaman Urs
     */
    @Override
    public String toString() {
        return name() + " (Code: " + code + ")";
    }

    /**
     * Retrieves an AccountType instance based on the provided name, ignoring case sensitivity.
     *
     * @param type the name of the account type to search for
     * @return the corresponding AccountType if found, otherwise null
     * @author Aryaman Urs
     */
    public static AccountType fromString(String type) {
        String normalized = type.trim().toUpperCase();
        switch (normalized) {
            case "CHECKING": return CHECKING;
            case "SAVINGS": return SAVINGS;
            case "MONEYMARKET": return MONEY_MARKET;
            default: return null; // Invalid type
        }
    }



}
