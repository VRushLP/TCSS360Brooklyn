import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ParkManager extends AbstractUser
{
    //Collections<myParks> managedParks;
    private List<String> myParks;
    
    public ParkManager(String myEmail, String myFirstName, String myLastName, List<String> myParks){
        super(myEmail, myFirstName, myLastName);
        this.myParks = new ArrayList<String>(myParks);
    }
    
    public List<String> getParks() {
        return Collections.unmodifiableList(myParks);
    }
}
