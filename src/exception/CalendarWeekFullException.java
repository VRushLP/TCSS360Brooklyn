package exception;

/**
 * A CalendarWeekFullException is thrown when a Park Manager attempts to add a
 * job to a Calendar that already has the maximum number of jobs in a particular
 * week through the ParkManagerDriver.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class CalendarWeekFullException extends Exception
{
    private static final long serialVersionUID = -5439249982829822719L;
}
