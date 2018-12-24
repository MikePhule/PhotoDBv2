package PhotoDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {


    public Button createDB;
    public TableColumn tblID;
    public TableColumn tblFilename;
    public TableView tableView;
    public AnchorPane pane;


    private ResultSet rs;
    private DBHandler dbHandler;
    private ObservableList<DBData> list = FXCollections.observableArrayList();

    private void updateList() {
        list.clear();

        try {
            rs = dbHandler.getTable();
            while (rs.next()) {
                list.add(new DBData(rs.getInt(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void initialize() {

        try {
            dbHandler = new DBHandler();
        } catch (SQLException e) {
            System.out.println("Can't connect to DB! Error: " + e.getStackTrace());
        }

        tblID.setCellValueFactory(new PropertyValueFactory("id"));
        tblFilename.setCellValueFactory(new PropertyValueFactory("filename"));



        try {
            ResultSet rs = dbHandler.getInfo();
            while (rs.next()) {
                String dbName = rs.getString("TABLE_CAT");
                if (DBHandler.DB_NAME.equals(dbName)) {
                    createDB.setDisable(true);
                    updateList();
                    tableView.setItems(list);
                } else {
                    tableView.setItems(list);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    public void btnCreateDB(ActionEvent actionEvent) {
        try {
            dbHandler.createDB();
            createDB.setDisable(true);
//            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnRemove(ActionEvent actionEvent) {
            DBData selectedItem = (DBData) tableView.getSelectionModel().getSelectedItem();
        try {
            dbHandler.removeItem(selectedItem.getId());
            updateList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAdd(ActionEvent actionEvent) {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select a photo");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images","*.png", "*.jpg");
            fc.getExtensionFilters().add(extFilter);
            File file = fc.showOpenDialog(pane.getScene().getWindow());
            if (file != null) {
                dbHandler.add(file);
                updateList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnView(ActionEvent actionEvent) {
        DBData selectedItem = (DBData) tableView.getSelectionModel().getSelectedItem();
        try {
            rs = dbHandler.getPhoto(selectedItem.getId());
            Image img;
            while (rs.next()) {
                InputStream is = rs.getBinaryStream("photo");
                img = new Image(is);
                ImageView view = new ImageView(img);

                Stage imgWindow = new Stage();
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(view);
                imgWindow.setScene(new Scene(stackPane));

                imgWindow.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        System.out.println(selectedItem.getId());

    }

    public void btnSettings(ActionEvent actionEvent) {
        try {
            SettingsView settingsView = new SettingsView();
            settingsView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
