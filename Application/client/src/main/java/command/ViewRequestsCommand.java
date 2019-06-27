package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import output.RequestDTO;
import output.RequestListDTO;
import server.RequestToServer;
import viewmodel.AdminHomeController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ViewRequestsCommand implements Command<Void>
{
    AdminHomeController adminHomeController;

    public ViewRequestsCommand(AdminHomeController adminHomeController)
    {
        this.adminHomeController = adminHomeController;
    }


    @Override
    public Void execute()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("lotId", Long.toString(adminHomeController.getSpotsCombo().getValue().getId()));
        myRequest.getParams().put("command", "viewrequests");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        RequestListDTO requestListDTO;

        try (Socket clientSocket = new Socket("127.0.0.1", 1997))
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            requestListDTO = gson.fromJson(fromServer, RequestListDTO.class);

            ObservableList<RequestDTO> result = FXCollections.observableArrayList();
            for(RequestDTO r: requestListDTO.getList())
            {
                result.add(r);
            }

            adminHomeController.setRequests(result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Void undo()
    {
        return null;
    }
}
