package command;

import viewmodel.AdminHomeController;

public class RetractSpotCommand implements Command<Void>
{
    AdminHomeController adminHomeController;

    public RetractSpotCommand(AdminHomeController adminHomeController)
    {
        this.adminHomeController = adminHomeController;
    }


    @Override
    public Void execute()
    {
        adminHomeController.retractSpot();

        return null;
    }

    @Override
    public Void undo() {
        return null;
    }
}
