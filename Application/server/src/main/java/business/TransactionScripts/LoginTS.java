package business.TransactionScripts;

import dao.DaoFactory;
import dao.UserDao;
import entities.User;
import output.DTO;
import output.UserDTO;

public class LoginTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
    private final String mail;
    private final String password;

    public LoginTS(String mail, String password)
    {
        this.mail = mail;
        this.password = password;
    }

    @Override
    public UserDTO execute()
    {
        UserDTO toReturn;
        User user = daoFactory.getUserDao().findByMail(mail);

        if(user != null)
        {
            if(user.getPassword().equals(password))
            {
                toReturn = new UserDTO(mail);
                toReturn.setAdmin(user.isAdmin());
                toReturn.setName(user.getName());
                toReturn.setMessage("Logged in.");

                return toReturn;
            }
        }

        return null;
    }
}
