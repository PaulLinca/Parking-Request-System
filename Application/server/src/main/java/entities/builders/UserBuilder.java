package entities.builders;

import entities.User;

import java.util.Objects;

public class UserBuilder
{
    private User underConstruction;

    private UserBuilder()
    {
        underConstruction = new User();
    }

    public static UserBuilder createUserBuilder()
    {
        return new UserBuilder();
    }

    public UserBuilder name(String name)
    {
        underConstruction.setName(name);
        return this;
    }

    public UserBuilder mail(String mail)
    {
        underConstruction.setMail(mail);
        return this;
    }

    public UserBuilder admin(Boolean admin)
    {
        underConstruction.setAdmin(admin);
        return this;
    }

    public UserBuilder address(String address)
    {
        underConstruction.setAddress(address);
        return this;
    }

    public UserBuilder password(String password)
    {
        underConstruction.setPassword(password);
        return this;
    }

    public User build()
    {
        checkValid(underConstruction);
        return underConstruction;
    }

    private void checkValid(User underConstruction)
    {
        Objects.requireNonNull(underConstruction.getMail());
        Objects.requireNonNull(underConstruction.getName());
    }
}
