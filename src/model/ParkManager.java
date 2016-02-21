
package model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

public class ParkManager extends AbstractUser
{
    private Collection<Park> myParks;

    public ParkManager(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>();
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Park thePark)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>();
        addParkToManager(thePark);
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Collection<Park> theParks)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>(theParks);

        for (Park p : theParks)
        {
            addParkToManager(p);
        }
    }

    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }

    public boolean addParkToManager(Park thePark)
    {
        if (myParks.add(thePark))
        {
            thePark.setParkManagerEmail(getEmail());
            thePark.setParkManagerName(getFirstName() + " " + getLastName());
            return true;
        }
        else
        {
            return false;
        }
    }
}
