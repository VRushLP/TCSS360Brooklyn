package model;

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
