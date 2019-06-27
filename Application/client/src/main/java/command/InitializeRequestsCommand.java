package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import output.RequestListDTO;
import server.RequestToServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InitializeRequestsCommand implements Command<RequestListDTO>
{
    @Override
    public RequestListDTO execute()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("command", "initializerequests");

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

            return requestListDTO;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RequestListDTO undo() {
        return null;
    }
}
