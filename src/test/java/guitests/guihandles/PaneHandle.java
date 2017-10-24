package guitests.guihandles;

import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

public abstract class PaneHandle {

    protected final GuiRobot guiRobot = new GuiRobot();
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Pane pane;

    public PaneHandle(Pane Pane) {
        this.pane = requireNonNull(Pane);
    }
    
}
