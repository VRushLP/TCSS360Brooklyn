package driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.AbstractUser;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.UrbanParkStaffMember;
import model.User;
import model.Volunteer;

public class PrimaryDriver
{
    private static final String EXIT_STRING = "quit";

    // Input file name.
    private static final String[] filePaths = { "jobList.ser",
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

        // Only call one of these!
        // fabricateInformation();
        deserializeData();

        Scanner in = new Scanner(System.in);

        // Prompt user to log
        System.out.println("Please enter your email to log in: ");
        String userInput;

        do
        {
            userInput = in.nextLine();
            // Hardcoded versions for easier testing.
            // userName = "presidenttheo@whitehouse.gov"; //Park Manager
            // userName = "rmfarc@email.net"; //Volunteer
            // userName = "thedude@aol.com"; //Urban Park Staff Member

            User currentUser = login(userInput);

            if (currentUser instanceof ParkManager)
            {
                ParkManagerDriver.run((ParkManager) currentUser, in,
                        UPCalendar);
            }
            else if (currentUser instanceof UrbanParkStaffMember)
            {
                UrbanParkStaffMemberDriver.run(
                        (UrbanParkStaffMember) currentUser, in, UPCalendar);
            }
            else if (currentUser instanceof Volunteer)
            {
                VolunteerDriver.run((Volunteer) currentUser, in, UPCalendar);
            }
            else if (!userInput.equalsIgnoreCase(EXIT_STRING))
            {
                System.out.println(
                        "Login failed. Please try again or type 'Quit' to terminate the program.");
            }
            else
            {
                System.out.println("Thank you for using Urban Parks.");
            }
        } while (!userInput.equalsIgnoreCase(EXIT_STRING));
        in.close();

        serializeData();
    }

    private static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }

    private static void fabricateInformation()
    {

    }

    private static void deserializeData()
    {
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

    }

    private static void serializeData()
    {
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
    }
}
