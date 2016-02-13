import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrimaryDriver
{
    // Input file name.
    private static final String inputPath = ".\\users.txt";

    // Data Structure to store everything in
    private static Map<String, AbstractUser> loginList;
    private static BufferedReader inputFileReader;

    // A calendar field
    private static UrbanParkCalendar UPCalendar;

    private PrimaryDriver()
    {
    }

    /*
     * Main begins here!
     */
    public static void main(String[] args)
    {
        // Get all user information from csv file and put it in a map
        loginList = new HashMap<>();
        File inputFile = new File(inputPath);
        // Initialize Urban Parks Calendar
        UPCalendar = new UrbanParkCalendar();
        ParkManager alexanderTheGreat = new ParkManager("email@doesntexist.net",
                "Alexander", "The Great");

        Park saltWaterState = new Park("Saltwater State Park",
                alexanderTheGreat);
        alexanderTheGreat.addParkToJurisdiction(saltWaterState);

        ParkManager theodoreRoosevelt = new ParkManager("email@doesntexist.net",
                "Alexander", "The Great");

        Park wildWaves = new Park("Wild Waves Theme Park", theodoreRoosevelt);
        Park dashPoint = new Park("Dash Point State Park", theodoreRoosevelt);

        theodoreRoosevelt.addParkToJurisdiction(wildWaves);
        theodoreRoosevelt.addParkToJurisdiction(dashPoint);

        Volunteer firstVolunteer = new Volunteer("forgetfulFerg@email.net",
                "Robert", "Ferguson");
        Volunteer secondVolunteer = new Volunteer("bobthebanshee@email.net",
                "Bob", "A. Ghost");
        Volunteer thirdVolunteer = new Volunteer("invalidemail@gmail.com",
                "Richard", "Tricky");

        UPCalendar.addVolunteer(firstVolunteer);
        UPCalendar.addVolunteer(secondVolunteer);
        UPCalendar.addVolunteer(thirdVolunteer);

        Job bigfoot = new Job(dashPoint, 30, new Date(), new Date(),
                "Bigfoot Hunting", "We'll get him this time.");
        Job yetis = new Job(dashPoint, 30, new Date(), new Date(),
                "Yeti Extermination", "They're everywhere!.");
        Job garbageCollect = new Job(wildWaves, 30, new Date(), new Date(),
                "Garbage Collection", "Not as exciting, I know");
        Job sweep = new Job(wildWaves, 30, new Date(), new Date(),
                "Sweeping up the beach.",
                "Getting rid of the sand. It gets /everywhere/");
        Job drainLake = new Job(saltWaterState, 30, new Date(), new Date(),
                "Drain the Lake",
                "We're going to need at least 20 able bodied men and women to drink all that water.");

        UPCalendar.addJob(bigfoot);
        UPCalendar.addJob(yetis);
        UPCalendar.addJob(garbageCollect);
        UPCalendar.addJob(sweep);
        UPCalendar.addJob(drainLake);

        try
        {
            inputFileReader = new BufferedReader(new FileReader(inputFile));
            String nextLine;

            while ((nextLine = inputFileReader.readLine()) != null)
            {
                // System.out.println(nextLine);
                String[] splitLine = nextLine.split(",");
                if (splitLine.length != 4)
                    continue;
                String currentEmail = splitLine[0];
                String currentFirstName = splitLine[1];
                String currentLastName = splitLine[2];
                String UserType = splitLine[3];

                /*
                 * Here, the loginList map is getting something put into it. The
                 * thing that is put is a userName, which is simply a String,
                 * and an AbstractUser of the type read in from the file. The
                 * problem with this approach is that it assumes well-formed
                 * input. If the input file is off by just a little, this will
                 * break hilariously badly. This is just a temporary solution:
                 * Eventually we will be moving to ObjectReaders anyway.
                 */
                if (!loginList.containsKey(currentEmail))
                {
                    String className = UserType;
                    Class<?> cl = Class.forName(className);
                    @SuppressWarnings("unchecked")
                    Constructor<AbstractUser> constructor = (Constructor<AbstractUser>) cl
                            .getConstructor(String.class);
                    AbstractUser currentUser = (AbstractUser) constructor
                            .newInstance(currentEmail);

                    currentUser.setFirstName(currentFirstName);
                    currentUser.setLastName(currentLastName);

                    loginList.put(currentEmail, currentUser);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputFileReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Scanner in = new Scanner(System.in);

        // Prompt user to log
        System.out.println("Enter your email: ");
        // String userName = in.nextLine();

        // Hardcoded version for easier running later.
        String userName = "testPM@doesntexist.net";

        // Uncomment out the line below to test the Volunteer class
        // and comment out the line above
        // String userName = "testVolunteer@please.net";

        User currentUser = login(userName);

        if (currentUser instanceof ParkManager)
        {
            ParkManagerDriver.run((ParkManager) currentUser, in, UPCalendar);
        }
        else if (currentUser instanceof UrbanParkStaffMember)
        {
            System.out.println("The user was an instance of "
                    + currentUser.getClass() + "!");
        }
        else if (currentUser instanceof Volunteer)
        {
            VolunteerDriver.run((Volunteer) currentUser, in);
        }
        else
        {
            System.out.println("Login failed");
        }

        in.close();
    }

    public static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }
}
