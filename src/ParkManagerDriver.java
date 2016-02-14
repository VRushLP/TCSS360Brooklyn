import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ParkManagerDriver
{
    static String input;
    static int choice;
    static String[] parsedInput;
    
    /**
     * 
     * @param theCurrentUser
     * @param in
     * @param UPCalendar
     * @author Lachezar, Robert
     */
    public static void run(ParkManager theCurrentUser, Scanner in, UrbanParkCalendar UPCalendar)
    {
        System.out.println("Welcome " + theCurrentUser.getEmail());
        
        
        // FOR TESTING PURPOSES
//        Park p1 = new Park("Gasworks Park", theCurrentUser);
//        Park p2 = new Park("Ravenna Park", theCurrentUser);
//        theCurrentUser.addParkToJurisdiction(p1);
//        theCurrentUser.addParkToJurisdiction(p2);
        
        while (choice != 6)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Submit a new job");
            System.out.println("2. Delete a job");
            System.out.println("3. Edit the details of a job.");
            System.out
                    .println("4. View summary of upcoming jobs in my parks.");
            System.out.println("5. View the volunteers for a job in the parks that I manage");
            System.out.println("6. Exit.");

            input = in.nextLine();
            parsedInput = input.split(" ");

            try
            {
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
                    submitNewJob(theCurrentUser, UPCalendar);
                    break;
                case 2:
                    deleteJob(theCurrentUser, UPCalendar);
                    break;
                case 3:
                    editJob(theCurrentUser, UPCalendar);
                    break;
                case 4:
                    viewJobsInParks(theCurrentUser, UPCalendar);
                    break;
                case 5:
                    viewVolunteers(theCurrentUser, UPCalendar);
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Please enter one of the number options");
            }
        }
    }

    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void submitNewJob(ParkManager theCurrentUser, UrbanParkCalendar uPCalendar)
    {
        Scanner in;
        final String jobTitle;
        final String jobDescription;
        String startDate;
        String endDate;
        int maxVolunteers;
        
        Park park = null;
        
        // Business Rule #1: Get total number of pending jobs to determine if the 
        // maximum number of pending jobs has been reached
        
        
        // TESING MAX. NO OF PENDING JOBS
//        ArrayList<Park> parkss = new ArrayList<Park>(theCurrentUser.getParks());
//        Park prk = parkss.get(1); 
//        for (int i = 0; i < 30; i++)
//        {
//            theCurrentUser.createJob(uPCalendar, prk, 5, "2/14/2016",
//                                    "2/16/2016", "Clean up Park", "Clean uppp parkkk");
//        }
        
        int noOfJobs = uPCalendar.getJobList().size();
        // Business Rule #1: ensure that the total number of pending jobs
        // is not currently 30
        if (noOfJobs == UrbanParkCalendar.MAX_JOBS)
        {
            System.out.println("Error: A job cannot be added."
                    + " The maximum number of pending jobs has been reached (30).");
        } 
        else
        {
            in = new Scanner(System.in); // set up scanner
            System.out.println("Submit New Job");         
            System.out.println("Please select one of the parks you manage to add a job for that park");
            
            ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());
           
            System.out.println();
            for (int i = 0; i < parks.size(); i++)
            {
                System.out.println((i + 1) + ") " + parks.get(i));
                System.out.println();
            }
            
            System.out.print("Enter park number:");

            input = in.nextLine();
            parsedInput = input.split(" ");
            
            try
            {
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here. Not needed?
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // Get desired park
            park = parks.get(choice - 1);
            
            // Testing
            // Print information from selected park
            //System.out.println(parks.get(choice - 1)); 
            
            System.out.print("Please enter job title: ");
            jobTitle = in.nextLine();
            //System.out.println();
            System.out.print("Job description: ");
            jobDescription = in.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = in.nextLine();
            
            // Validate entered start date (business rule #5)
            
            // TO DO: Keep examining user input even if
            // an error message has already been displayed           
            if (!validateStartDate(startDate))
            {
                System.out.println("Error: Start date cannot be in the past or"
                                   + " more than 3 months from today's date. ");
                System.out.print("Enter new Start Date (MM/DD/YYYY): ");
                startDate = in.nextLine();
            }
            
            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = in.nextLine();
            
            // Validate entered start and end dates (business rule #4)
            
            // TO DO: Keep examining user input even if
            // an error message has already been displayed
            if (!validateStartEndDate(startDate, endDate))
            {
                System.out.println("Error: A job may not be scheduled that "
                                    + "lasts more than two days.");
                System.out.print("Enter new end date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }
            
            // Validate start and end dates (make sure they are not the same)
            if (!validateStartEndDate2(startDate, endDate))
            {
                System.out.println("Error: Start date cannot be the same as end Date.");
                System.out.print("Enter new End Date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }
            System.out.print("Maximum number of volunteers: ");
            maxVolunteers = in.nextInt();

            // Call create job method in Park Manager class
            // and pass in the park that the PM wants to add a job to
            theCurrentUser.createJob(uPCalendar, park, maxVolunteers, startDate,
                                     endDate, jobTitle, jobDescription);
        }
    }
    
    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void deleteJob(ParkManager theCurrentUser, UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);
        Park park = null;
        
        // Desired job to delete
        Job j = null;
        
        System.out.println("Delete a Job");
        
        System.out.println("Please select one of the parks you manage to delete a job from that park");
        
        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());
       
        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }
        
        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");
        
        try
        {
            choice = Integer.parseInt(parsedInput[0].substring(0,
                    parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Get desired park
        park = parks.get(choice - 1);
        
        
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());
        
        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to delete.");
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println("Please enter the number of the job you would like to delete");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");
            
            input = in.nextLine();
            parsedInput = input.split(" ");
            
            try
            {
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here. Not needed?
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            // Get desired job to delete
            j = jobs.get(choice - 1);
            
            // Delete job
            theCurrentUser.deleteJob(uPCalendar, j, park);
        }


    }
    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void editJob(ParkManager theCurrentUser, UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);
        final String jobTitle;
        final String jobDescription;
        String startDate;
        String endDate;
        int maxVolunteers;
        Park park = null;
        
        // Desired job to delete
        Job j = null;
        
        System.out.println("Edit a Job");
        
        System.out.println("Please select one of the parks you manage to edit a job from that park");
        
        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());
       
        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }
        
        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");
        
        try
        {
            choice = Integer.parseInt(parsedInput[0].substring(0,
                    parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Get desired park
        park = parks.get(choice - 1);
        
        
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());
        
        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to edit.");
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println("Please enter the number of the job you would like to edit");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");
            
            input = in.nextLine();
            parsedInput = input.split(" ");
            
            try
            {
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here. Not needed?
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            // Get desired job to delete
            j = jobs.get(choice - 1);
            
            System.out.print("Please enter job title: ");
            jobTitle = in.nextLine();
            //System.out.println();
            System.out.print("Job description: ");
            jobDescription = in.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = in.nextLine();
            
            // Validate entered start date (business rule #5)
            
            // TO DO: Keep examining user input even if
            // an error message has already been displayed           
            if (!validateStartDate(startDate))
            {
                System.out.println("Error: Start date cannot be in the past or"
                                   + " more than 3 months from today's date. ");
                System.out.print("Enter new Start Date (MM/DD/YYYY): ");
                startDate = in.nextLine();
            }
            
            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = in.nextLine();
            
            // Validate entered start and end dates (business rule #4)
            
            // TO DO: Keep examining user input even if
            // an error message has already been displayed
            if (!validateStartEndDate(startDate, endDate))
            {
                System.out.println("Error: A job may not be scheduled that "
                                    + "lasts more than two days.");
                System.out.print("Enter new End Date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }
            
            // Validate start and end dates (make sure they are not the same)
            if (!validateStartEndDate2(startDate, endDate))
            {
                System.out.println("Error: Start date cannot be the same as end Date.");
                System.out.print("Enter new End Date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }
            System.out.print("Maximum number of volunteers: ");
            maxVolunteers = in.nextInt();
           
            theCurrentUser.editJob(uPCalendar, j, park, maxVolunteers, startDate, 
                                   endDate, jobTitle, jobDescription);
        }
    }
    // Do we want all the jobs in every park or
    // just at a specific park (as implemented below)?
    // TO DO: Decide the level of detail of the summary
    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void viewJobsInParks(ParkManager theCurrentUser, UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);
        Park park = null;
        
        System.out.println("View a summary of all jobs in my parks");
        System.out.println();
        System.out.println("Please select one of the parks you manage to view "
                + "a summary of all upcoming jobs in that park");
        
        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());
       
        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }
        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");
        
        try
        {
            choice = Integer.parseInt(parsedInput[0].substring(0,
                    parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Get desired park
        park = parks.get(choice - 1);
        
        
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());
        
        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.");
            System.out.println();
        }
        else
        {
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
                System.out.println();
            }
        }
        
    }
    
    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    private static void viewVolunteers(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);
        Park park = null;
        Job job = null;
        
        System.out.println("View volunteers for a job at one of my parks");
        System.out.println();
        System.out.println("Please select one of the parks you manage to view "
                + "the jobs");
        
        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());
       
        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }
        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");
        
        try
        {
            choice = Integer.parseInt(parsedInput[0].substring(0,
                    parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // Get desired park
        park = parks.get(choice - 1);
        
        
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());
        
        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.");
            System.out.println();
        }
        else
        {
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
                System.out.println();
            }
            System.out.print("Enter job number:");

            input = in.nextLine();
            parsedInput = input.split(" ");
            
            try
            {
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here. Not needed?
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            // Get desired job
            job = jobs.get(choice - 1);
            
            
            ArrayList<Volunteer> volunteers = new ArrayList<Volunteer>(job.getVolunteers());
            
            // Print volunteer information from collection of volunteers for 
            // selected job
            for (int i = 0; i < volunteers.size(); i++)
            {
                System.out.println((i + 1) + ") " + volunteers.get(i));
            }

        }
        
    }
    /**
     * This helper method enforces business rule #5. WORKS
     * @param endDate
     * @author Lachezar
     */
    private static boolean validateStartDate(String dateStart)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        float diffDays;
        Date startDate = null;
        try
        {
            startDate = format.parse(dateStart);
            
            
        }
        catch (ParseException e)
        {
            
            e.printStackTrace();
        }
        // Get system's current date
        Calendar calobj = Calendar.getInstance();
        
        // Set current time to 00:00:00 so that we can compare with entered start date
        calobj.set(Calendar.HOUR_OF_DAY, 0);
        calobj.set(Calendar.MINUTE, 0);
        calobj.set(Calendar.SECOND, 0);
        
        Date currentDate = calobj.getTime();

        // Get the difference between start date and current date in
        // milliseconds
        float diff = startDate.getTime() - currentDate.getTime();

        // Difference in days
        diffDays = diff / (24 * 60 * 60 * 1000);
//        System.out.println("Diff in days: " + diffDays);      
        if (diffDays < 0 || diffDays > 90)
        {
            return false;
        }
        return true;
        
    }
    
    /**
     * This helper method enforces business rule #4. 
     * @param endDate
     * @author Lachezar
     */
    private static boolean validateStartEndDate(String dateStart, String dateEnd)
    {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); // this repeats, move it up
        
        try
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        }
        catch (ParseException e)
        {
            
            e.printStackTrace();
        }
        // Get the difference in milliseconds
        float diff = endDate.getTime() - startDate.getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);
        
        if (diffDays > 2)
        {
            return false;
        }
        return true;
    }
    /**
     * This helper method checks if start and end dates are the same
     * and returns appropriate boolean value based on result
     * @param dateStart
     * @param dateEnd
     * @return
     * @author Lachezar
     */
    private static boolean validateStartEndDate2(String dateStart,
            String dateEnd)
    {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); // this repeats, move it up
        
        try
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
            // Get the difference in milliseconds
            float diff = endDate.getTime() - startDate.getTime();
            if (diff == 0)
            {
                return false;
            }
        }
        catch (ParseException e)
        {
            
            e.printStackTrace();
        }
        return true;
    }
    
}
