package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;

public class MassEmailTest extends AddressBookGuiTest {

    @Test
    public void openMassEmailTest() {
        //use accelerator
        getCommandBox().run("mass");
        
        getCommandBox().run("mass all"); 
        
        getCommandBox().run("mass friends"); 
        
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertWindowOpen() {
       // assertTrue(ERROR_MESSAGE, HelpWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HelpWindowHandle(guiRobot.getStage(HelpWindowHandle.HELP_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }
}
