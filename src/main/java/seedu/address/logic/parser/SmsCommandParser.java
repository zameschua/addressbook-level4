package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.commons.core.Messages;

import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MassEmailPredicate;

/**
 * Parses input arguments and creates a new SmsCommand object
 */
public class SmsCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SmsCommand
     * and returns an SmsCommand object for execution.
     */
    public SmsCommand parse(String args)throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        // TODO: Change to TagPredicate or something
        return new SmsCommand(new MassEmailPredicate(Arrays.asList(nameKeywords)));
    }
}
