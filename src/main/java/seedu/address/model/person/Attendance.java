package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author pohjie
/**
 * Represents a Person's attendance in the address book.
 * Hardcoded now till further versions support actual implementation of attendance
 */
public class Attendance {

    public final int maxAttendance = 8;
    private int attended;
    private int missed;

    /**
     * Default constructor
     * All Person will get this default image in V1.4 and V1.5
     */
    public Attendance() {
        attended = 0;
        missed = 8;
    }


    /**
     * User can choose to set the number of attended sessions if it is not zero.
     * @param attended
     * @throws IllegalValueException
     */
    public Attendance(int attended) throws IllegalValueException { // make sure attendance is valid. Tests here
        this.attended = attended;
        missed = maxAttendance - attended;
    }

    public int getAttended() {
        return attended;
    }

    public int getMissed() {
        return missed;
    }

    @Override
    public String toString() {
        return "Attended is: " + attended + ", and missed is: " + missed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Attendance
                && this.attended == (((Attendance) other).attended)
                && this.missed == (((Attendance) other).missed));

    }

}
