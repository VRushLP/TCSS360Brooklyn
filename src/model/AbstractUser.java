package model;

import java.io.Serializable;

/**
 * @author Robert
 * @version 03/02/2016
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

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String theLastName)
    {
        this.lastName = theLastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String theFirstName)
    {
        this.firstName = theFirstName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String theEmail)
    {
        this.email = theEmail;
    }
}
