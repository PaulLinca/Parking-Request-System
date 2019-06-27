package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import output.RequestDTO;
import server.RequestToServer;
import viewmodel.AdminHomeController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AssignSpotCommand implements Command<RequestDTO>
{
    AdminHomeController adminHomeController;

    public AssignSpotCommand(AdminHomeController adminHomeController)
    {
        this.adminHomeController = adminHomeController;
    }


    @Override
    public RequestDTO execute()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("vin", adminHomeController.getRequestCombo().getValue().getVin());
        myRequest.getParams().put("command", "assignspot");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        RequestDTO requestDTO;

        try (Socket clientSocket = new Socket("127.0.0.1", 1997))
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            requestDTO = gson.fromJson(fromServer, RequestDTO.class);

            return requestDTO;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RequestDTO undo() {
        return null;
    }
}
