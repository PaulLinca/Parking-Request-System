package command;

import viewmodel.UserHomeController;

public class UpdateRequestCommand implements Command<Void>
{
    UserHomeController userHomeController;

    public UpdateRequestCommand(UserHomeController userHomeController)
    {
        this.userHomeController = userHomeController;
    }

    @Override
    public Void execute()
    {
        userHomeController.updateRequestWindow();
        return null;
    }

    @Override
    public Void undo() {
        return null;
    }
}
