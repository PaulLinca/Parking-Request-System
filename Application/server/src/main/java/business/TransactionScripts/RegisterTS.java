package business.TransactionScripts;

import dao.DaoFactory;
import entities.User;
import entities.builders.UserBuilder;
import output.UserDTO;

public class RegisterTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
    private final String mail;
    private final String password;

    public RegisterTS(String mail, String password)
    {
        this.mail = mail;
        this.password = password;
    }

    @Override
    public UserDTO execute()
    {
        UserDTO toReturn;
        User user = daoFactory.getUserDao().findByMail(mail);

        if(user == null)
        {
            user = UserBuilder.createUserBuilder().mail(mail).password(password).admin(false).name("New user").build();
            toReturn = new UserDTO(mail);
            toReturn.setAdmin(user.isAdmin());
            toReturn.setName(user.getName());
            toReturn.setMessage("Logged in.");

            daoFactory.getUserDao().insert(user);

            return toReturn;
        }

        return null;
    }
}
