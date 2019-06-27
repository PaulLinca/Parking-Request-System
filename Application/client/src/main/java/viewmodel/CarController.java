package viewmodel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import output.CarDTO;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CarController implements Initializable
{
    @FXML
    TextField vinField;

    @FXML
    TextField ptiField;

    public CarDTO addCar()
    {
        CarDTO newCar = new CarDTO(getVin());
        newCar.setPti(new Date(Calendar.getInstance().getTime().getTime() + 100));

        return newCar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public String getVin()
    {
        return vinField.getText();
    }
}
