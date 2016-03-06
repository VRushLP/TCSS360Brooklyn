package exception;

/**
 * A JobToThePastException is thrown when a Job is attempted to be added to the
 * UrbanParkCalendar when that job should have already occured.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class JobToThePastException extends Exception
{
    private static final long serialVersionUID = 2639734161274756214L;
}
