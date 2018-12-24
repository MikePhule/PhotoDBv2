package PhotoDB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsViewController {

    public TextField user;
    public PasswordField pass;
    @FXML
    private AnchorPane pane;
    private Stage currentStage;


    @FXML
    void initialize() {
        user.setText(DBHandler.getUser());

    }

    public void btnSave(ActionEvent actionEvent) {
        DBHandler.setUser(user.getText());
        DBHandler.setPassword(pass.getText());
        currentStage = (Stage) pane.getScene().getWindow();
        currentStage.close();
    }

    public void btnCancel(ActionEvent actionEvent) {
        currentStage = (Stage) pane.getScene().getWindow();
        currentStage.close();
    }
}
