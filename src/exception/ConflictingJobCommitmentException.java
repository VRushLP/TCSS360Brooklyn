package exception;

/**
 * A ConflictingJobCommitmentException is thrown when a Volunteer attempts to
 * sign up for a Job on the same day that another job they were already signed
 * up for is occurring.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class ConflictingJobCommitmentException extends Exception
{
    private static final long serialVersionUID = -782442284235960846L;
}
