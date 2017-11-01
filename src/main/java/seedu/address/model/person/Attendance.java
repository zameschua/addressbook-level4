package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's attendance in the address book.
 * Hardcoded now till further versions support actual implementation of attendance
 */
public class Attendance {

    public final String attendancePicPath;

    /**
     * Default constructor
     * All Person will get this default image in V1.4 and V1.5
     */
    public Attendance() {
        attendancePicPath = "/images/attendance.jpg";
    }

    /**
     * User can choose to add in attendance pictures hosted on personal file hosting service.
     * @param path
     * @throws IllegalValueException
     */
    public Attendance(String path) throws IllegalValueException {
        requireNonNull(path);
        attendancePicPath = path;
    }

    @Override
    public String toString() { return attendancePicPath; }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Attendance
                && this.attendancePicPath.equals(((Attendance) other).attendancePicPath));
    }

    @Override
    public int hashCode() {
        return attendancePicPath.hashCode();
    }
}
