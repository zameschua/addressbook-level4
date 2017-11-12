package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.MassEmailCommand;
import seedu.address.model.person.TagMatchingPredicate;

//@@author ReneeSeet

public class MassEmailCommandParserTest {

    private MassEmailParser parser = new MassEmailParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMassEmailCommand() {
        // no leading and trailing whitespaces
        MassEmailCommand expectedMassEmailCommand =
                new MassEmailCommand(new TagMatchingPredicate((Arrays.asList("friends", "OwesMoney"))));
        assertParseSuccess(parser, "friends OwesMoney", expectedMassEmailCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t OwesMoney  \t", expectedMassEmailCommand);
    }
}
