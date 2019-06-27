package command;

import viewmodel.UserHomeController;

public class AddCarCommand implements Command<Void>
{
    UserHomeController userHomeController;

    public AddCarCommand(UserHomeController userHomeController)
    {
        this.userHomeController = userHomeController;
    }

    @Override
    public Void execute()
    {
        userHomeController.addCarWindow();
        return null;
    }

    @Override
    public Void undo()
    {
        return null;
    }
}
