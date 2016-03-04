package exception;

/**
 * A DuplicateJobExistsException is thrown when a ParkManager attempts to add a
 * job that already exists in the Calendar, or attempts to edit a job into
 * another job that already exists in the Calendar.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public class DuplicateJobExistsException extends Exception
{
    private static final long serialVersionUID = 8094695038797090869L;
}
