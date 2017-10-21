package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class CommandBoxKeyInputEvent extends BaseEvent {
    
    private String newCommandText;
    
    public CommandBoxKeyInputEvent(String newCommandText){
        this.newCommandText = newCommandText;
    }

    public String getCommandText() {
        return newCommandText;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
