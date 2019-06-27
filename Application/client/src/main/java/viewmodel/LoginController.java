package viewmodel;

import command.LoginCommand;
import command.RegisterCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import output.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    Button loginUserButton;
    @FXML
    Button loginAdminButton;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField mailField;
    @FXML
    Button exitButton;
    @FXML
    Button registerButton;

    private UserDTO user = null;

    //button listeners
    @FXML
    private void loginUserAction()
    {
        user = new LoginCommand(this).execute();
    }

    @FXML
    private void loginAdminAction()
    {
        UserDTO user = new LoginCommand(this).execute();
        if(user != null)
        {
            changeToAdminScene();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid username or password");
            alert.show();
        }
    }

    @FXML
    private void registerAction()
    {
        user = new RegisterCommand(this).execute();
    }

    @FXML
    private void exitAction()
    {
        System.out.println("Exiting application...");
        System.exit(1);
    }

    //scene changers
    public void changeToUserScene()
    {
        Scene scene = loginUserButton.getScene();
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user_home.fxml"));
            Parent root = loader.load();

            UserHomeController userHomeController = loader.getController();
            userHomeController.setUserMail(getMail());

            Stage stage = (Stage) scene.getWindow();
            stage.close();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void changeToAdminScene()
    {
        Scene scene = loginAdminButton.getScene();
        Parent newFxml = null;
        try
        {
            newFxml = FXMLLoader.load(getClass().getResource("/admin_home.fxml"));
            scene.setRoot(newFxml);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    //setters and getters
    public String getMail()
    {
        return mailField.getText();
    }

    public String getPassword()
    {
        return passwordField.getText();
    }

    public void clearTextBoxes()
    {
        passwordField.clear();
        mailField.clear();
    }

    //initialize method
    public void initialize(URL location, ResourceBundle resources)
    {
    }
}
