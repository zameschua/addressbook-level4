package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * List all tags that exist in software to user
 */
public class ListAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "listalltags";

    public static final String MESSAGE_SUCCESS = "Listed all tags";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "ListAllTags command not implemented yet";
    
    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
