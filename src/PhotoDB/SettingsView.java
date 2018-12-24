package PhotoDB;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsView extends Stage {
    public SettingsView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
        Scene scene = new Scene(root, 300,200);
        this.setScene(scene);
//        this.show();
    }
}
