package command;

import viewmodel.AdminHomeController;

public class ViewSpotsCommand implements Command<Void>
{
    AdminHomeController adminHomeController;

    public ViewSpotsCommand(AdminHomeController adminHomeController)
    {
        this.adminHomeController = adminHomeController;
    }

    @Override
    public Void execute()
    {
        adminHomeController.setSpots();
        return null;
    }

    @Override
    public Void undo()
    {
        return null;
    }
}
