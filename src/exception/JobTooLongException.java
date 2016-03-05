package exception;

/**
 * A JobTooLongException is thrown when the difference between a Job's start
 * date and a Job's end date is greater than the maximum acceptable length as
 * defined in the Job class.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class JobTooLongException extends Exception
{
    private static final long serialVersionUID = 4637078049549883219L;
}
