package guitests.guihandles;

import java.awt.*;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import  javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


/**
 * A handle to the {@code LoginPage} in the GUI.
 */

public class LoginHandle extends NodeHandle<Node>{

    public static final String LOGIN_DISPLAY_ID = "#LoginPanel";
    
    
    public LoginHandle(Node LoginPanelNode){
        super(LoginPanelNode);
        TextField s = getChildNode("#emailBox");
        s.setText("hi");
        PasswordField p = getChildNode("#passwordBox"); 
        p.setText("bye");
        guiRobot.type(KeyCode.ENTER);
    }
    
    /**
     * Fills the email & passwords TextField 
     */
    /*public void fill() {
        for(Node s : listNode) {
            if(s.getId().equals("emailBox")) {
                TextField d = (TextField) s; 
                d.setText("hi");
            }

            if(s.getId().equals("passwordBox")) {
                TextField d = (TextField) s;
                d.setText("hi");
            }
        }
        guiRobot.type(KeyCode.ENTER); 
        
    }
    
    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }*/




}
