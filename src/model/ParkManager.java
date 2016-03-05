
package model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A Kind of user that is able to submit new jobs, as well as edit or remove old
 * jobs.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class ParkManager extends AbstractUser
{
    private static final long serialVersionUID = 4121511740399438884L;

    private Collection<Park> myParks;

    /**
     * Creates a new ParkManager with the given email, as well as first and last
     * names.
     */
    public ParkManager(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>();
    }

    /**
     * Adds a given park to the list of managed parks
     * 
     * @return True or false, depending on whether the operation was successful
     *         or not
     */
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

    /**
     * 
     * @return An unmodifiable version of the parks job Collection
     */
    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }
}
