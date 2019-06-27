package command;

import viewmodel.UserHomeController;

public class DeleteRequestCommand implements Command<Void>
{
    UserHomeController userHomeController;

    public DeleteRequestCommand(UserHomeController userHomeController)
    {
        this.userHomeController = userHomeController;
    }

    @Override
    public Void execute()
    {
        userHomeController.deleteRequestWindow();

        return null;
    }

    @Override
    public Void undo()
    {
        return null;
    }
}
