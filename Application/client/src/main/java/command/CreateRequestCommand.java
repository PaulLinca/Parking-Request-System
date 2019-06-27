package command;

import viewmodel.UserHomeController;

public class CreateRequestCommand implements Command<Void>
{
    UserHomeController userHomeController;

    public CreateRequestCommand(UserHomeController userHomeController)
    {
        this.userHomeController = userHomeController;
    }

    @Override
    public Void execute()
    {
        userHomeController.createRequestWindow();

        return null;
    }

    @Override
    public Void undo()
    {
        return null;
    }
}
