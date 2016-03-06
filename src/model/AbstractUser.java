package model;

import java.io.Serializable;

/**
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public abstract class AbstractUser implements User, Serializable
{
    private static final long serialVersionUID = -8113206652437221361L;

    private String lastName;
    private String firstName;
    private String email;

    public AbstractUser(String theEmail, String theFirstName,
            String theLastName)
    {
        this.email = theEmail;
        this.firstName = theFirstName;
        this.lastName = theLastName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof AbstractUser)
        {
            AbstractUser comparison = (AbstractUser) o;

            return email.equals(comparison.email)
                    && firstName.equals(comparison.firstName)
                    && lastName.equals(comparison.lastName);
        }
        return false;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public void setEmail(String theEmail)
    {
        this.email = theEmail;
    }

    @Override
    public void setFirstName(String theFirstName)
    {
        this.firstName = theFirstName;
    }

    @Override
    public void setLastName(String theLastName)
    {
        this.lastName = theLastName;
    }

    @Override
    public String toString()
    {
        StringBuilder abstractUserString = new StringBuilder();
        abstractUserString.append("E-mail: ");
        abstractUserString.append(email);
        abstractUserString.append(" Name: ");
        abstractUserString.append(lastName);
        abstractUserString.append(", ");
        abstractUserString.append(firstName);
        return abstractUserString.toString();
    }
}
