/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/
package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import output.NotificationDTO;
import server.RequestToServer;

import java.io.*;
import java.net.Socket;

public class SubscribeCommand implements Command<String>
{
    private Gson gson;
    private Socket clientSocket;

    public SubscribeCommand()
    {
    }

    @Override
    public String execute() {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("command", "subscribeToNotification");

        gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        try {
            clientSocket = new Socket("127.0.0.1", 1997);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(json);
            new Thread(() -> waitForNotification()).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/secondView.fxml";
    }

    private void waitForNotification()
    {
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                NotificationDTO notification = gson.fromJson(line, NotificationDTO.class);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(notification.getMessage());
                    alert.showAndWait();
                });
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String undo() {
        return "/secondView.fxml";
    }
}
