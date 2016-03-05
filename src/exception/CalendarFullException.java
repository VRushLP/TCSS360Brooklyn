package exception;

/**
 * A CalendarFullException is thrown when a Park Manager attempts to add a job
 * to a Calendar that already has the maximum number of jobs in it through the
 * ParkManagerDriver.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class CalendarFullException extends Exception
{
    private static final long serialVersionUID = -2918016418440174331L;
}
