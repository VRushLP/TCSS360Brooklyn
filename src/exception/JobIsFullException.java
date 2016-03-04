package exception;

/**
 * A JobIsFullException occurs when a Volunteer attempts to sign up for a job
 * that already has the maximum number of volunteers in that work category.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public class JobIsFullException extends Exception
{
    private static final long serialVersionUID = 2120169394337869133L;
}
