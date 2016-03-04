package exception;

/**
 * A JobWorksTooHardException is thrown when the combined total of each work
 * load's available is greater than the maximum allowable number of Volunteers,
 * as defined by the Job class.
 * 
 * @author Robert
 * @version 3/1/2016
 */
public class JobWorksTooHardException extends Exception
{
    private static final long serialVersionUID = 381164527939312696L;
}
