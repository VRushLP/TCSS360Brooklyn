package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JobTest.class, ParkTest.class, ParkManagerTest.class,
        UrbanParkCalendarTest.class, VolunteerTest.class })
public class AllTests
{
}
