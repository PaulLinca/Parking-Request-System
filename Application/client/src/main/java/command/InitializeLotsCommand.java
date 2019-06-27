package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import output.ParkingLotListDTO;
import server.RequestToServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InitializeLotsCommand implements Command<ParkingLotListDTO>
{
    @Override
    public ParkingLotListDTO execute()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("command", "initializelots");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        ParkingLotListDTO parkingLotListDTO;

        try (Socket clientSocket = new Socket("127.0.0.1", 1997))
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            parkingLotListDTO = gson.fromJson(fromServer, ParkingLotListDTO.class);
            return parkingLotListDTO;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ParkingLotListDTO undo() {
        return null;
    }
}
