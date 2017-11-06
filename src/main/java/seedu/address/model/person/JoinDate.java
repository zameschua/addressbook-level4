package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents when Person was added to addressbook.
 * Guarantees: Every Person will have a Join Date.
 */

//@@author ReneeSeet

public class JoinDate {

    private String joinDate;

    /**
     * Default constructor
     * only used for testing
     */
    public JoinDate(String date) {
        joinDate = date;
    }

    public JoinDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //e.g 16/10/2017
        LocalDate localDate = LocalDate.now();
        joinDate = dtf.format(localDate);
    }

    @Override
    public String toString() {
        return joinDate;
    }

}
