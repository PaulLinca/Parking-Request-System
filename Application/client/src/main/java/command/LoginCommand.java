package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import output.UserDTO;
import server.RequestToServer;
import viewmodel.LoginController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginCommand implements Command<UserDTO>
{
   private LoginController loginController;

   public LoginCommand(LoginController loginController)
   {
       this.loginController = loginController;
   }

   @Override
    public UserDTO execute()
    {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("mail", loginController.getMail());
        myRequest.getParams().put("password", loginController.getPassword());
        myRequest.getParams().put("command", "login");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        UserDTO userDTO = null;

        try (Socket clientSocket = new Socket("127.0.0.1", 1997))
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            userDTO = gson.fromJson(fromServer, UserDTO.class);
            System.out.println(userDTO);

            if (userDTO == null)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid username or password");
                alert.show();
            }
            else if (userDTO.isAdmin())
            {
                loginController.changeToAdminScene();
            }
            else
            {
                loginController.changeToUserScene();
                new SubscribeCommand().execute();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return userDTO;
    }

    @Override
    public UserDTO undo()
    {
        return null;
    }
}
