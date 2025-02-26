/**
 * Represents a user profile with a first name, last name, and date of birth.
 * Provides methods for comparison and equality checks based on profile attributes.
 *
 * @author Aryaman Urs
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;
    /**
     * Constructs a Profile with the given first name, last name, and date of birth.
     *
     * @param fname the first name of the user
     * @param lname the last name of the user
     * @param dob   the date of birth of the user
     * @author Aryaman Urs
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }
    /**
     * Checks if this profile is equal to another object.
     * Two profiles are considered equal if they have the same first name, last name,
     * and date of birth, case-insensitively.
     *
     * @param obj the object to compare with
     * @return true if the profiles are equal, otherwise false
     * @author Aryaman Urs
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Profile)) return false;
        Profile other = (Profile) obj;
        return fname.equalsIgnoreCase(other.fname) &&
               lname.equalsIgnoreCase(other.lname) &&
               dob.equals(other.dob);
    }
    /**
     * Compares this profile with another profile.
     * Comparison is based on last name, then first name, and finally date of birth.
     *
     * @param other the profile to compare against
     * @return a negative integer, zero, or a positive integer as this profile
     *         is less than, equal to, or greater than the specified profile
     * @author Aryaman Urs
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = lname.compareToIgnoreCase(other.lname);
        if (lastNameComparison != 0) return lastNameComparison;

        int firstNameComparison = fname.compareToIgnoreCase(other.fname);
        if (firstNameComparison != 0) return firstNameComparison;

        return dob.compareTo(other.dob);
    }
    /**
     * Returns a string representation of the profile in the format:
     * "FirstName LastName DateOfBirth".
     *
     * @return the string representation of the profile
     * @author Aryaman Urs
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Testbed for the Profile class.
     * Demonstrates the creation and comparison of profiles.
     *
     * @param args command-line arguments (not used)
     * @author Aryaman Urs
     */
    public static void main(String[] args) {
        Date dob1 = new Date(1995, 5, 15);
        Date dob2 = new Date(2000, 7, 20);
        Profile profile1 = new Profile("John", "Doe", dob1);
        Profile profile2 = new Profile("John", "Doe", dob1);
        Profile profile3 = new Profile("Jane", "Smith", dob2);

        // Testing toString()
        System.out.println("Profile 1: " + profile1);
        System.out.println("Profile 2: " + profile2);
        System.out.println("Profile 3: " + profile3);

        // Testing equals()
        System.out.println("Profile 1 equals Profile 2: " + profile1.equals(profile2)); // Should be true
        System.out.println("Profile 1 equals Profile 3: " + profile1.equals(profile3)); // Should be false

        // Testing compareTo()
        System.out.println("Compare Profile 1 and Profile 2: " + profile1.compareTo(profile2)); // Should be 0
        System.out.println("Compare Profile 1 and Profile 3: " + profile1.compareTo(profile3)); // Should be negative
    }
}
