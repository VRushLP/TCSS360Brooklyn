import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

public class ParkManager extends AbstractUser
{
    // Collections<myParks> managedParks;
    private Collection<Park> myParks;

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Park thePark)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>();
        myParks.add(thePark);
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Collection<Park> theParks)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>(theParks);
    }

    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }
}
