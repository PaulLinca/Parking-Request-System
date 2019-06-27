package command;

import viewmodel.UserHomeController;

public class DeleteCarCommand implements Command<Void>
{
    private UserHomeController userHomeController;

    public DeleteCarCommand(UserHomeController userHomeController)
    {
        this.userHomeController = userHomeController;
    }

    @Override
    public Void execute()
    {
        userHomeController.deleteCarWindow();
        return null;
    }

    @Override
    public Void undo() {
        return null;
    }
}
