import java.util.Calendar;
/**
 * Represents a calendar date with validation and comparison capabilities.
 * Handles dates in the mm/dd/yyyy format and supports basic date operations.
 * @author Arjun Deshpande
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    /**
     * Constructs a Date object with specified month, day, and year.
     * Note: Does not validate the date. Use isValid() to check validity.
     * @author Arjun Deshpande
     * @param month the month component (1-12)
     * @param day the day component (1-31 depending on month)
     * @param year the year component (any integer)
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.year = year;
        this.day = day;
    }//end constructor
    /**
     * Validates the date according to calendar rules.
     * @author Arjun Deshpande
     * @return true if the date represents a valid calendar date, false otherwise
     * @implNote Checks:
     * - Month between 1-12
     * - Day appropriate for month
     * - Leap year handling for February
     * - Negative values prohibited
     */
    public boolean isValid() {
        // Debugging print statement to check input values
        // System.out.println("Checking date validity: " + month + "/" + day + "/" + year);
        if (year < 1900 || month < 1 || month > 12 || day < 1) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " is out of range!");
            return false;
        }
        final int QUADRENNIAL = 4;
        final int CENTENNIAL = 100;
        final int QUATERCENTENNIAL = 400;
        int maxDay;
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                maxDay = 31;
                break;
            case 4: case 6: case 9: case 11:
                maxDay = 30;
                break;
            case 2:
                if ((year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0)) maxDay = 29;
                else maxDay = 28;
                break;
            default:
                System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " not a valid calendar date!");
                return false; // Invalid month
        }

        // Ensure the day does not exceed the maximum day for that month
        if (day > maxDay) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " not a valid calendar date!");
            return false;
        }

        // Get the current date
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        if (year > currentYear || (year == currentYear && month > currentMonth) ||
                (year == currentYear && month == currentMonth && day >= currentDay)) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " is in the future or today!");
            return false;
        }
        int age = currentYear - year;
        if (month > currentMonth || (month == currentMonth && day > currentDay)) {
            age--; // Adjust if birthday hasn't occurred yet this year
        }
        if (age < 18) {
            System.out.println("DOB invalid: " + month + "/" + day + "/" + year + " is under 18 years old!");
            return false;
        }
        return true; // All checks passed, date is valid
    }
    /**
     * Compares this date with another for equality.
     * @author Arjun Deshpande
     * @param obj the object to compare with
     * @return true if both objects are Date instances with identical year, month, and day
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Date)) return false;
        Date other = (Date) obj;
        return year == other.year && month == other.month && day == other.day;
    }//end override
    /**
     * Compares this date with another date chronologically.
     * @author Arjun Deshpande
     * @param other the date to compare with
     * @return negative value if earlier, positive if later, 0 if equal
     */
    @Override
    public int compareTo(Date other) {
        if (year != other.year) return Integer.compare(year, other.year);
        if (month != other.month) return Integer.compare(month, other.month);
        return Integer.compare(day, other.day);
    }//end override
    /**
     * Returns a string representation in mm/dd/yyyy format.
     * @author Arjun Deshpande
     * @return formatted date string (e.g., "02/19/2000")
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", month, day, year);
    }//end override
    /**
     * Parses a date string in mm/dd/yyyy format.
     *
     * @param dateStr the string to parse
     * @return Date object if valid format, null otherwise
     * @author Arjun Deshpande
     */
    public static Date fromString(String dateStr) {
        String[] parts = dateStr.split("/");
        if (parts.length != 3) return null;
        try {
            int m = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            return new Date(m, d, y);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    /**
     * Testbed main method for validating Date class functionality.
     *
     * @param args command-line arguments (not used)
     * @author Arjun Deshpande
     */
    public static void main(String[] args) {
        Date testDate = new Date(5,7 , 2005);//SHOULD BE VALID
        System.out.println(testDate.isValid() ? "Valid" : "Invalid");
    }//testbed method (MUST WORK)

}//end class Date