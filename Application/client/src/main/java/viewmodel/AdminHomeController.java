package viewmodel;

import command.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import output.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable
{
    //scene views
    @FXML
    Button logoutButton;
    @FXML
    Button viewLotsButton;
    @FXML
    Button clearButton;
    @FXML
    ListView listView;
    @FXML
    Button exitButton;
    @FXML
    Button viewSpotsButton;
    @FXML
    Button viewRequestsButton;
    @FXML
    ComboBox<ParkingLotDTO> spotsCombo;
    @FXML
    ComboBox<RequestDTO> requestCombo;
    @FXML
    Button assignButton;
    @FXML
    Button retractButton;

    //observable lists
    private ObservableList<RequestDTO> requestList =  FXCollections.observableArrayList();
    private ObservableList<ParkingLotDTO> lotList =  FXCollections.observableArrayList();
    private ObservableList<ParkingSpotDTO> spotList =  FXCollections.observableArrayList();

    //actions
    @FXML
    private void logoutAction()
    {
        System.out.println("Go to login window.");
        Scene scene = logoutButton.getScene();
        Parent newFxml = null;
        try
        {
            newFxml = FXMLLoader.load(getClass().getResource("/login.fxml"));
            scene.setRoot(newFxml);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void assignAction()
    {
        RequestDTO requestDTO = new AssignSpotCommand(this).execute();
        for(RequestDTO r: requestList)
        {
            if (r.getVin() == requestDTO.getVin())
            {
                requestList.removeAll(r);
                requestList.add(requestDTO);
            }
        }
    }

    @FXML
    private void retractAction()
    {
        new RetractSpotCommand(this).execute();
    }

    @FXML
    private void viewLotsAction()
    {
        new ViewLotsCommand(this).execute();
    }

    @FXML
    private void clearAction()
    {
        listView.setVisible(false);
        clearButton.setVisible(false);
    }

    @FXML
    private void viewSpotsAction()
    {
        new ViewSpotsCommand(this).execute();
    }

    @FXML
    private void viewRequestsAction()
    {
        new ViewRequestsCommand(this).execute();
    }

    @FXML
    private void exitAction()
    {
        System.out.println("Exiting application...");
        System.exit(1);
    }

    //helper functions
    public void setSpots()
    {
        ObservableList<ParkingSpotDTO> temp =  FXCollections.observableArrayList();

        for(ParkingSpotDTO p: spotList)
        {
            if(p.getLotId() == spotsCombo.getValue().getId())
            {
                temp.add(p);
            }
        }

        listView.setVisible(true);
        listView.setItems(temp);
        clearButton.setVisible(true);
    }

    public void setLots()
    {
        listView.setVisible(true);
        listView.setItems(lotList);
        clearButton.setVisible(true);
    }

    public void setRequests(ObservableList<RequestDTO> temp)
    {
        listView.setVisible(true);
        listView.setItems(temp);
        clearButton.setVisible(true);
    }

    public void assignSpot()
    {
        RequestDTO appliedFor = requestCombo.getValue();
        if(appliedFor.getPSpot() == 0)
        {
            for(ParkingLotDTO p: lotList)
            {
                if(p.getId() == appliedFor.getPLot())
                {
                    for(ParkingSpotDTO s: spotList)
                    {
                        if(s.isFree())
                        {
                            appliedFor.setStatus("Assigned spot.");
                            appliedFor.setPSpot(s.getParkingNo());
                            appliedFor.setMessage("Done");
                            s.setFree(false);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void retractSpot()
    {
        RequestDTO current = requestCombo.getValue();
        if(current.getPSpot() != 0)
        {
            for(ParkingSpotDTO s: spotList)
            {
                if(s.getParkingNo() == current.getPSpot())
                {
                    s.setFree(true);
                    break;
                }
            }
            current.setPSpot(0);
            current.setStatus("Failed to pay.");
            current.setMessage("The parking spot has been retracted");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
//        ParkingSpotDTO spot1 = new ParkingSpotDTO(42, 1);
//        ParkingSpotDTO spot2 = new ParkingSpotDTO(200, 1);
//        ParkingSpotDTO spot3 = new ParkingSpotDTO(65, 1);
//        ParkingSpotDTO spot4 = new ParkingSpotDTO(2, 2);
//        ParkingSpotDTO spot5 = new ParkingSpotDTO(3, 2);
//        ParkingSpotDTO spot6 = new ParkingSpotDTO(123, 3);
//
//        spot4.setFree(false);
//        spot5.setFree(false);
//        spot1.setFree(false);
//
//        spotList.add(spot1);
//        spotList.add(spot2);
//        spotList.add(spot3);
//        spotList.add(spot4);
//        spotList.add(spot5);
//        spotList.add(spot6);
//
//        //add a request to the request list
//        RequestDTO firstRequest = new RequestDTO(new Date(1000000));
//        firstRequest.setStatus("Parking spot assigned.");
//        firstRequest.setMessage("This is the initial request.");
//        firstRequest.setVin("CJ1");
//        firstRequest.setPLot(1);
//        firstRequest.setPSpot(42);
//
//        //add a request to the request list
//        RequestDTO secondRequest = new RequestDTO(new Date(1000000));
//        secondRequest.setStatus("Pending");
//        secondRequest.setMessage("This is the initial request.");
//        secondRequest.setVin("CJ2");
//        secondRequest.setPLot(1);
//
//        requestList.add(firstRequest);
//        requestList.add(secondRequest);
        ParkingLotListDTO lList = new InitializeLotsCommand().execute();
        lotList.addAll(lList.getList());

        RequestListDTO rList = new InitializeRequestsCommand().execute();
        requestList.addAll(rList.getList());

        {
            ParkingLotDTO lot1 = new ParkingLotDTO(1);
            lot1.setAddress("Parking Lot 1");
            ParkingLotDTO lot2 = new ParkingLotDTO(2);
            lot2.setAddress("Parking Lot 2");

            lotList.add(lot1);
            lotList.add(lot2);
        }

        spotsCombo.setItems(lotList);
        requestCombo.setItems(requestList);
    }

    public ComboBox<RequestDTO> getRequestCombo() {
        return requestCombo;
    }

    public ComboBox<ParkingLotDTO> getSpotsCombo() {
        return spotsCombo;
    }
}
