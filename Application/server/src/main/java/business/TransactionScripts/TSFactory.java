package business.TransactionScripts;

import server.RequestToServer;

public class TSFactory
{
    public static TransactionScript create(RequestToServer requestToServer)
    {
        String command = requestToServer.getParams().get("command");

        switch (command)
        {
            case "login":
                String username = requestToServer.getParams().get("mail");
                String password = requestToServer.getParams().get("password");
                return new LoginTS(username, password);
            case "makerequest":
                String mail = requestToServer.getParams().get("mail");
                String vin = requestToServer.getParams().get("vin");
                long lotId = Long.parseLong(requestToServer.getParams().get("lotId"));
                return new MakeRequestTS(mail, vin, lotId);
            case "viewrequests":
                lotId = Long.parseLong(requestToServer.getParams().get("lotId"));
                return new ViewRequestsTS(lotId);
            case "initializerequests":
                return new InitializeRequestsTS();
            case "initializelots":
                return new InitializeLotsTS();
            case "assignspot":
                vin = requestToServer.getParams().get("vin");
                return new AssignSpotTS(vin);
            case "register":
                username = requestToServer.getParams().get("mail");
                password = requestToServer.getParams().get("password");
                return new RegisterTS(username, password);
            default:
                return null;
        }
    }
}
