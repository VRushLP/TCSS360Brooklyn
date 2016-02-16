import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrimaryDriver
{
    // Input file name.
    private static final String[] filePaths = { "calendar.ser",
            "loginList.ser" };

    // Data Structure to store everything in
    private static Map<String, AbstractUser> loginList;
    private static InputStream inputFileReader;
    private static OutputStream outputFileWriter;

    // A calendar field
    private static UrbanParkCalendar UPCalendar;

    private PrimaryDriver()
    {
    }

    /*
     * Main begins here!
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        UPCalendar = new UrbanParkCalendar();
        loginList = new HashMap<>();

        /* Serializing Work starts here! */
        try
        {
            inputFileReader = new FileInputStream(filePaths[0]);
            ObjectInputStream objectReader = new ObjectInputStream(
                    inputFileReader);
            UPCalendar = (UrbanParkCalendar) objectReader.readObject();

            objectReader.close();
            inputFileReader.close();

            inputFileReader = new FileInputStream(filePaths[1]);
            objectReader = new ObjectInputStream(inputFileReader);
            loginList = (HashMap<String, AbstractUser>) objectReader
                    .readObject();
            objectReader.close();
            inputFileReader.close();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /* Input Serializing Work ends here! */

        // There's currently an issue serializing the Calendar. These are here a
        // temporary fix.
        ParkManager theodoreRoosevelt = new ParkManager(
                "presidenttheo@whitehouse.gov", "Alexander", "The Great");
        loginList.put(theodoreRoosevelt.getEmail(), theodoreRoosevelt);

        Park wildWaves = new Park("Wild Waves Theme Park", theodoreRoosevelt);
        Park dashPoint = new Park("Dash Point State Park", theodoreRoosevelt);

        theodoreRoosevelt.addParkToManager(wildWaves);
        theodoreRoosevelt.addParkToManager(dashPoint);

        Volunteer firstVolunteer = new Volunteer("forgetfulferg@email.net",
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

        UPCalendar.addJob(bigfoot);
        UPCalendar.addJob(yetis);
        UPCalendar.addJob(garbageCollect);
        UPCalendar.addJob(sweep);

        dashPoint.addJob(bigfoot);
        dashPoint.addJob(yetis);
        wildWaves.addJob(garbageCollect);
        wildWaves.addJob(sweep);

        Scanner in = new Scanner(System.in);

        // Prompt user to log
        System.out.println("Enter your email: ");
        String userName = in.nextLine();

        // Hardcoded versions for easier running later.
        // String userName = "presidenttheo@whitehouse.gov";
        // String userName = "testVolunteer@please.net";
        // String userName = "thedude@aol.com";

        User currentUser = login(userName);

        if (currentUser instanceof ParkManager)
        {
            ParkManagerDriver.run((ParkManager) currentUser, in, UPCalendar);
        }
        else if (currentUser instanceof UrbanParkStaffMember)
        {
            UrbanParkStaffMemberDriver.run((UrbanParkStaffMember) currentUser,
                    in, UPCalendar);
        }
        else if (currentUser instanceof Volunteer)
        {
            VolunteerDriver.run((Volunteer) currentUser, in, UPCalendar);
        }
        else
        {
            System.out.println("Login failed");
        }
        in.close();

        /* Write modified objects back out to files. */
        try
        {
            outputFileWriter = new FileOutputStream(filePaths[0]);
            ObjectOutputStream objectWriter = new ObjectOutputStream(
                    outputFileWriter);
            objectWriter.writeObject(UPCalendar);
            objectWriter.close();
            outputFileWriter.close();

            outputFileWriter = new FileOutputStream(filePaths[1]);
            objectWriter = new ObjectOutputStream(outputFileWriter);
            objectWriter.writeObject(loginList);
            objectWriter.close();
            outputFileWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /* Output Serializing Work ends here! */
    }

    public static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }
}
