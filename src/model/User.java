package model;

/**
 * An interface which defines methods that all types of users must have. Most of
 * these are getters and setters.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public interface User
{
    @Override
    public boolean equals(Object o);

    public String getEmail();

    public String getFirstName();

    public String getLastName();

    public void setEmail(String theEmail);

    public void setFirstName(String theFirstName);

    public void setLastName(String theLastName);

    @Override
    public String toString();
}
