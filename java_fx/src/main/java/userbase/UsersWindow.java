package userbase;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by v.davidenko on 03.02.2016.
 */
public class UsersWindow extends Application {
    @FXML
    public TableView tableUsers;
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableColumn<User, String> nameColumn;
    @FXML
    public TableColumn<User, String> passwordColumn;
    @FXML
    public TableColumn<User, Date> dateColumn;
    @FXML
    public PasswordField password;
    @FXML
    public TextField userName;
    @FXML
    public Label message;
    @FXML
    public DatePicker regDate;

    final static String REG_SUCCESS_MSG = "New user added successfully";
    final static String EMPTY_FIELDS_MSG = "Please, fill in all fields!";
    final static String REG_ERR_MSG = "Such user is already exist!";
    final static String NO_USERS_FOUND_MSG = "No users found!";

    @FXML
    private void initTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("date"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/userbase/reg_form.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Registration form");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onClickRegistration() {
        if (userName.getText().isEmpty() || password.getText().isEmpty() ||
                regDate.getEditor().getText().isEmpty()) {
            message.setText(EMPTY_FIELDS_MSG);
            return;
        }
        message.setText("");
        Date date = asDate(regDate.getValue());
        UserJDBCManager userMan = new UserJDBCManager();
        User newUser = new User(userName.getText().trim(), password.getText().trim(), date);
        int result = 0;
        try {
            result = userMan.create(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result == 1) {
            message.setText(REG_SUCCESS_MSG);
        } else {
            message.setText(REG_ERR_MSG);
        }
    }

    public void onClickShowList() {
        message.setText("");
        UserJDBCManager userMan = new UserJDBCManager();
        List<User> users = new ArrayList<User>();
        try {
            users = userMan.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users != null && !users.isEmpty()) {
            ObservableList<User> usersList = FXCollections.observableArrayList();
            for (User user : users) {
                usersList.add(user);
            }
            initTableView();
            tableUsers.setItems(usersList);
        } else {
            message.setText(NO_USERS_FOUND_MSG);
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }


}
