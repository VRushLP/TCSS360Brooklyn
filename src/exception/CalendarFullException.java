package exception;

/**
 * A CalendarFullException is thrown when a Park Manager attempts to add a job
 * to a Calendar that already has the maximum number of jobs in it through the
 * ParkManagerDriver.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public class CalendarFullException extends Exception
{
    private static final long serialVersionUID = -2918016418440174331L;
}
