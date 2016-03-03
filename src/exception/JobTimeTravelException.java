package exception;

/**
 * A JobTimeTravelException is thrown when a Job's end date is before it's start
 * date.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public class JobTimeTravelException extends Exception
{
    private static final long serialVersionUID = 5511078843050817572L;
}
