import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

public class ParkManager extends AbstractUser
{
    private static final long serialVersionUID = 2473533828616953096L;
    private Collection<Park> myParks;

    public ParkManager(String theEmail)
    {
        super(theEmail);
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }

    public ParkManager(String theEmail, String theFirstName,
            String theLastName, Park thePark)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>();
        myParks.add(thePark);
    }

    public ParkManager(String theEmail, String theFirstName,
            String theLastName, Collection<Park> theParks)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>(theParks);
    }

    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }
}
