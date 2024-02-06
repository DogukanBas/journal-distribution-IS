package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.*;

public class RunAllTestCases {
    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestOfIndividual.class);
        suite.addTestSuite(TestOfCorporation.class);
        suite.addTestSuite(TestOfDateInfo.class);
        suite.addTestSuite(TestOfPaymentInfo.class);
        suite.addTestSuite(TestOfDistributor.class);
        suite.addTestSuite(TestOfJournal.class);
        suite.addTestSuite(TestOfSubscription.class);

        return suite;
    }


}
