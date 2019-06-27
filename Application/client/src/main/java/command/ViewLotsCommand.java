package command;

import viewmodel.AdminHomeController;

public class ViewLotsCommand implements Command<Void>
{
    AdminHomeController adminHomeController;

    public ViewLotsCommand(AdminHomeController adminHomeController)
    {
        this.adminHomeController = adminHomeController;
    }


    @Override
    public Void execute()
    {
        adminHomeController.setLots();
        return null;
    }

    @Override
    public Void undo() {
        return null;
    }
}
