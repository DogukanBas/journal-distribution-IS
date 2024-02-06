package unitTests;
import junit.framework.TestCase;
import dogukanbas.*;


public class TestOfJournal extends TestCase{
    private Journal journal;
    public void setUp(){
        journal = new Journal("Journal1","123123",2,100.0);
    }
    public void testAddSubscription(){
        Individual individual = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);
        Subscription subscription = new Subscription(new DateInfo(1,2011,2),1,journal,individual);
        journal.addSubscription(subscription);
        Subscription expected1 = subscription;
        Subscription actual1 = journal.getSubscriptions().get(0);
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetSubscriptions(){
        Individual individual = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);
        Subscription subscription = new Subscription(new DateInfo(1,2011,2),1,journal,individual);
        journal.addSubscription(subscription);
        Subscription expected1 = subscription;
        Subscription actual1 = journal.getSubscriptions().get(0);
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetName(){
        String expected1 = "Journal1";
        String actual1 = journal.getName();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetIssuePrice(){
        double expected1 = 100.0;
        double actual1 = journal.getIssuePrice();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetIssn(){
        String expected1 = "123123";
        String actual1 = journal.getIssn();
        TestCase.assertEquals(expected1,actual1);
    }




}
