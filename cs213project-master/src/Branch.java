/**
 * Represents different branches of a bank, each associated with a branch code, zip code, and county.
 *
 * @author Aryaman Urs
 * @author Arjun Deshpande
 */
public enum Branch {
    EDISON("100", "08817", "Middlesex"),
    BRIDGEWATER("200", "08807", "Somerset"),
    PRINCETON("300", "08542", "Mercer"),
    PISCATAWAY("400", "08854", "Middlesex"),
    WARREN("500", "07057", "Somerset");

    private final String branchCode;
    private final String zip;
    private final String county;
    /**
     * Constructs a Branch with a specific branch code, zip code, and county.
     *
     * @param branchCode the unique code for the branch
     * @param zip        the zip code of the branch location
     * @param county     the county where the branch is located
     * @author Aryaman Urs
     */
    Branch(String branchCode, String zip, String county) {
        this.branchCode = branchCode;
        this.zip = zip;
        this.county = county;
    }
    /**
     * Retrieves the city name of the branch with the first letter capitalized.
     *
     * @return the city name of the branch
     * @author Arjun Deshpande
     */
    public String getCity() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
    /**
     * Gets the branch code associated with this branch.
     *
     * @return the branch code as a string
     * @author Aryaman Urs
     */
    public String getBranchCode() {
        return branchCode;
    }
    /**
     * Gets the zip code of the branch location.
     *
     * @return the zip code as a string
     * @author Aryaman Urs
     */
    public String getZip() {
        return zip;
    }
    /**
     * Gets the county where the branch is located.
     *
     * @return the county name as a string
     * @author Aryaman Urs
     */
    public String getCounty() {
        return county;
    }

    /**
     * Retrieves a Branch instance based on the city name, ignoring case sensitivity.
     *
     * @param city the name of the city to search for
     * @return the corresponding Branch if found, otherwise null
     * @author Aryaman Urs
     */
    public static Branch fromCity(String city) {
        for (Branch branch : values()) {
            if (branch.name().equalsIgnoreCase(city)) {
                return branch;
            }
        }
        return null; // Invalid branch
    }
}