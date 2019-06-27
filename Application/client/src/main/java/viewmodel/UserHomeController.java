package viewmodel;

import command.*;
import helper.DialogsHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import output.CarDTO;
import output.RequestDTO;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static helper.DialogsHelper.showCustomDialog;

public class UserHomeController implements Initializable
{
    //scene views
    @FXML
    Button logoutButton;
    @FXML
    Button viewRequestsButton;
    @FXML
    Button createRequestButton;
    @FXML
    Button updateRequestButton;
    @FXML
    Button deleteRequestButton;
    @FXML
    Button exitButton;
    @FXML
    Button addCarButton;
    @FXML
    Button deleteCarButton;
    @FXML
    Button viewCarsButton;
    @FXML
    Button clearButton;
    @FXML
    ListView listView;

    String userMail;

    //observable lists
    private ObservableList<RequestDTO> requestList =  FXCollections.observableArrayList();
    private ObservableList<CarDTO> carList = FXCollections.observableArrayList();

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
    private void viewRequestsAction()
    {
        listView.setVisible(true);
        listView.setItems(requestList);
        clearButton.setVisible(true);
    }

    @FXML
    private void createRequestAction()
    {
        System.out.println("Pop up create request window.");
        new CreateRequestCommand(this).execute();
    }

    @FXML
    private void updateAction()
    {
        System.out.println("Pop up update request window.");
        new UpdateRequestCommand(this).execute();
    }

    @FXML
    private void deleteAction()
    {
        System.out.println("Pop up delete request window.");
        new DeleteRequestCommand(this).execute();
    }

    @FXML
    private void addCarAction()
    {
        System.out.println("Pop up add car window.");
        new AddCarCommand(this).execute();
    }

    @FXML
    private void deleteCarAction()
    {
        System.out.println("Pop up delete car window.");
        new DeleteCarCommand(this).execute();
    }

    @FXML
    private void viewCarsAction()
    {
        listView.setVisible(true);
        listView.setItems(carList);
        clearButton.setVisible(true);
    }

    @FXML
    private void clearAction()
    {
        listView.setVisible(false);
        clearButton.setVisible(false);
        System.out.println(getUserMail());
    }

    @FXML
    private void exitAction()
    {
        System.out.println("Exiting application...");
        System.exit(1);
    }

    //pop up request window
    public void createRequestWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create_request.fxml"));
            Parent parent = fxmlLoader.load();
            RequestController dialogController = fxmlLoader.getController();
            showCustomDialog("Add Request", parent, () -> dialogController.createRequest())
                    .ifPresent(newRequest -> {
//                        if(isCarPresent(newRequest.getVin()))
//                        {
//                            requestList.add(newRequest);
//                        }
//                        else
//                        {
//                            System.out.println("CAR ISNT FOUND");
//                        }
                        requestList.add(newRequest);
                    });
        }
        catch (Exception e)
        {
            DialogsHelper.showErrorDialog("Unable to add request.");
            e.printStackTrace();
        }
    }

    //pop up request window
    public void deleteRequestWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create_request.fxml"));
            Parent parent = fxmlLoader.load();
            RequestController dialogController = fxmlLoader.getController();
            showCustomDialog("Delete Request", parent, () -> dialogController.createRequest())
                    .ifPresent(newRequest -> {
                        for(RequestDTO requestDTO: requestList)
                        {
                            if(requestDTO.getVin().equals(newRequest.getVin()) && requestDTO.getPLot() == newRequest.getPLot())
                            {
                                requestList.remove(requestDTO);
                                break;
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            DialogsHelper.showErrorDialog("Unable to add request.");
            e.printStackTrace();
        }
    }

    //pop up request window
    public void updateRequestWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create_request.fxml"));
            Parent parent = fxmlLoader.load();
            RequestController dialogController = fxmlLoader.getController();
            dialogController.setNewParkingLotFieldVisible();
            showCustomDialog("Update Request", parent, () -> dialogController.createRequest())
                    .ifPresent(newRequest -> {
                        for(RequestDTO requestDTO: requestList)
                        {
                            if(requestDTO.getVin().equals(newRequest.getVin()) && requestDTO.getPLot() == Integer.parseInt(dialogController.getParkingLot()))
                            {
                                requestList.remove(requestDTO);
                                requestList.add(newRequest);
                                break;
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            DialogsHelper.showErrorDialog("Unable to add request.");
            e.printStackTrace();
        }
    }

    //pop up car window
    public void addCarWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_car.fxml"));
            Parent parent = fxmlLoader.load();
            CarController dialogController = fxmlLoader.getController();
            showCustomDialog("Add Car", parent, () -> dialogController.addCar())
                    .ifPresent(newCar -> {
                        if(!isCarPresent(newCar.getVin()))
                        {
                            carList.add(newCar);
                        }
                    });
        }
        catch (Exception e)
        {
            DialogsHelper.showErrorDialog("Unable to add car.");
            e.printStackTrace();
        }
    }

    //pop up car window
    public void deleteCarWindow()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_car.fxml"));
            Parent parent = fxmlLoader.load();
            CarController dialogController = fxmlLoader.getController();
            showCustomDialog("Add Car", parent, () -> dialogController.addCar())
                    .ifPresent(newCar -> {
                        for(CarDTO carDTO: carList)
                        {
                            if(newCar.getVin().equals(carDTO.getVin()))
                            {
                                carList.remove(carDTO);
                                break;
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            DialogsHelper.showErrorDialog("Unable to add car.");
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        //add a car to the car list
        CarDTO firstCar = new CarDTO("C1");
        firstCar.setPti(new Date(Calendar.getInstance().getTime().getTime() + 10000));
        firstCar.setMessage("First car");

        carList.add(firstCar);

//        //add a request to the request list
//        RequestDTO firstRequest = new RequestDTO(new Date(1000000));
//        firstRequest.setStatus("Pending");
//        firstRequest.setMessage("This is the initial request.");
//        firstRequest.setVin(firstCar.getVin());
//        firstRequest.setPLot(42)
//
//        requestList.add(firstRequest);
    }

    //checks if car is in the list
    public boolean isCarPresent(String vin)
    {
        for(CarDTO carDTO: carList)
        {
            System.out.println(carDTO.getVin());
            if(carDTO.getVin().equals(vin))
            {
                return true;
            }
        }

        return false;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
