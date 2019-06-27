package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import output.RequestDTO;
import output.RequestDTOBuilder;
import output.UserDTO;
import server.RequestToServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class RequestController implements Initializable
{
    @FXML
    TextField carField;

    @FXML
    TextField parkingLotField;

    @FXML
    TextField newParkingLotField;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public RequestDTO createRequest()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("vin", getCarVin());
        myRequest.getParams().put("lotId", getParkingLot());
        myRequest.getParams().put("command", "makerequest");
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        RequestDTO requestDTO = null;

        try (Socket clientSocket = new Socket("127.0.0.1", 1997))
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            requestDTO = gson.fromJson(fromServer, RequestDTO.class);
            System.out.println(requestDTO);

            if (requestDTO == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid data");
                alert.show();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return requestDTO;
    }

    public void setNewParkingLotFieldVisible()
    {
        newParkingLotField.setVisible(true);
    }

    public String getCarVin()
    {
        return carField.getText();
    }

    public String getParkingLot()
    {
        return parkingLotField.getText();
    }

    public String getNewParkingLot()
    {
        return newParkingLotField.getText();
    }
}
