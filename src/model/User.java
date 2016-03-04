package model;

/**
 * An interface which defines methods that all types of users must have. Most of
 * these are getters and setters.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public interface User
{
    public String toString();

    public boolean equals(Object o);

    public String getLastName();

    public void setLastName(String theLastName);

    public String getFirstName();

    public void setFirstName(String theFirstName);

    public String getEmail();

    public void setEmail(String theEmail);
}
