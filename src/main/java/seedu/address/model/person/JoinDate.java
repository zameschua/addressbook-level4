package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Customers's Join Date in the address book.
 */

public class JoinDate {

    public final String value;

    /**
     * Validates date
     */
    public JoinDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //e.g 16/10/2017
        LocalDate localDate = LocalDate.now();
        value = dtf.format(localDate);
    }

    public JoinDate(String date) {
       this.value = date; 
    }

    @Override
    public String toString() {
        return value;
    }
    
}
