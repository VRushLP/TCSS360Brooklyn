import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class PrimaryDriver
{
    // Input file name.
    private static final String inputPath = ".\\users.txt";

    // Data Structure to store everything in
    static Map<String, AbstractUser> loginList;
    private static BufferedReader inputFileReader;

    // private constructor to make instantiation harder.
    private PrimaryDriver()
    {
    }

    /*
     * Main begins here!
     */
    public static void main(String[] args)
    {
        // Get all user information from csv file
        loginList = new HashMap<>();
        File inputFile = new File(inputPath);
        try
        {
            inputFileReader = new BufferedReader(new FileReader(inputFile));
            String nextLine = inputFileReader.readLine();
            String[] splitLine = nextLine.split(",");
            String userName = splitLine[0];
            String UserType = splitLine[splitLine.length - 1];

            /*
             * So this bullshit: Here, the loginList map is getting something
             * put into it. The thing that is put is a userName, which is simply
             * a String, and an AbstractUser of the type read in from the file.
             * The problem with this approach is that it assumes well-formed
             * input. If the input file is off by just a little, this will break
             * hilariously badly. This is just a temporary solution: Eventually
             * we will be moving to ObjectReaders anyway.
             */
            loginList.put(userName, (AbstractUser) Class.forName(UserType)
                    .newInstance());
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

        // Prompt user to log
        System.out.println("Enter your username: ");
        String userName = "testPM@doesntexist.net";
        // Hardcoded for now

        User currentUser = login(userName);

        if (currentUser instanceof ParkManager)
        {
            System.out.println("The user was an instance of Park Manager!");
        }
        else if (currentUser instanceof UrbanParksStaffMember)
        {
            System.out
                    .println("The user was an instance of UrbanParksStaffMember!");
        }
        else if (currentUser instanceof Volunteer)
        {
            System.out.println("The user was an instance of Volunteer!");
        }
        else
        {
            // login failed
            // User should be prompted to try again.
        }
    }

    public static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }
}
