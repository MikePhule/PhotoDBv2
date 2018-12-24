package PhotoDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.prefs.Preferences;

public class DBHandler {


    public static final String DB_NAME = "PhotoDB";
    private Statement statement;
    private PreparedStatement preparedStatement;

    private static Preferences preferences = Preferences.userNodeForPackage(MainView.class);

    public static void setUser(String user) {
        preferences.put("db_username", user);
    }

    public static String getUser() {
        return preferences.get("db_username", null);
    }

    public static void setPassword(String password) {
        preferences.put("db_password", password);
    }

    public static String getPassword() {
        return preferences.get("db_password",null);
    }


    private Connection connection;

    public DBHandler() throws SQLException {
        String conn_string;
        if (getUser() == null || getPassword() == null) {
//            conn_string="jdbc:mysql://localhost:3306/?user=root&password=root&serverTimezone=UTC";
            try {
                SettingsView settingsView = new SettingsView();
                settingsView.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        conn_string="jdbc:mysql://localhost:3306/?user=" + getUser() + "&password=" + getPassword() + "&serverTimezone=UTC";

        this.connection = DriverManager.getConnection(conn_string);
        statement = connection.createStatement();

    }



    public void createDB() throws SQLException {

        statement.executeUpdate("CREATE DATABASE PhotoDB");
        statement.executeUpdate("USE PhotoDB");
        statement.executeUpdate("CREATE TABLE photos (id int(8) AUTO_INCREMENT NOT NULL PRIMARY KEY, filename varchar(255), photo LONGBLOB)");
    }

    public ResultSet getInfo() throws SQLException {
        return connection.getMetaData().getCatalogs();
    }

    public ResultSet getTable() throws SQLException {
        statement.executeUpdate("USE PhotoDB");
        return statement.executeQuery("SELECT * FROM photos");

    }

    public void add(File file) throws SQLException {
        String sql = "INSERT INTO photos (filename, photo) VALUES (?,?)";
        preparedStatement = connection.prepareStatement(sql);
//        System.out.println(file.getName());

        preparedStatement.setString(1, file.getName());
        try {
            preparedStatement.setBinaryStream(2, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        preparedStatement.executeUpdate();
//        statement.executeUpdate("INSERT INTO photos (filename) VALUES ('test')");
    }

    public ResultSet getPhoto(int id) throws SQLException {
        String sql = "SELECT photo FROM photos WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    public void removeItem(int id) throws SQLException {
        String sql = "DELETE FROM photos WHERE id=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
