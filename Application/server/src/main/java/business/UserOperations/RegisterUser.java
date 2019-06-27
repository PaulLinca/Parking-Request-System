package business.UserOperations;

import dao.DaoFactory;
import dao.UserDao;
import entities.User;
import entities.builders.UserBuilder;
import output.UserDTO;

public class RegisterUser
{
    private final DaoFactory daoFactory;
    private String mail;
    private String name;
    private String password;
    private String address;

    public RegisterUser(DaoFactory daoFactory, String mail, String password, String name, String address)
    {
        this.daoFactory = daoFactory;
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public UserDTO execute()
    {
        //initialize DAO
        UserDao userDao= daoFactory.getUserDao();
        User user;

        UserDTO userDto = new UserDTO(mail);

        //build user entity with the given data
        user = UserBuilder.createUserBuilder()
                .mail(mail)
                .name(name)
                .password(password)
                .admin(false)
                .address(address)
                .build();

        //insert the user in the DB
        userDao.insert(user);
        //update DTO with needed data
        userDto.setName(name);
        userDto.setMessage("Account registered: " + userDto.getMail() + ".");

        return userDto;
    }
}
