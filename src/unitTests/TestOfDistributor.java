package unitTests;
import junit.framework.TestCase;
import dogukanbas.*;
import java.util.ArrayList;



public class TestOfDistributor extends TestCase {
    private Distributor distributor;
    private Journal journal;
    private Individual individual;
    private Subscription subscription;

    public void setUp(){
        distributor = new Distributor();
    }
    public void testAddJournal(){
        Journal journal = new Journal("Journal1","123123",2,100.0);
        distributor.addJournal(journal);
        Journal expected1 = journal;
        Journal actual1 = distributor.getJournals().get(journal.getIssn());
        TestCase.assertEquals(expected1,actual1);
    }
    public void testSearchJournal(){
        Journal journal = new Journal("Journal1","123123",2,100.0);
        distributor.addJournal(journal);
        Journal expected1 = journal;
        Journal actual1 = distributor.searchJournal("123123");
        TestCase.assertEquals(expected1,actual1);

    }
    public void testAddSubscriber(){
        Individual individual = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);
        distributor.addSubscriber(individual);
        Individual expected1 = individual;
        Individual actual1 = (Individual) distributor.getSubscribers().get(0);
        TestCase.assertEquals(expected1,actual1);
    }
    public void testSearchSubscriber(){
        Individual individual = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);
        distributor.addSubscriber(individual);
        Individual expected1 = individual;
        Individual actual1 = (Individual) distributor.searchSubscriber("Dogukan Bas");
        TestCase.assertEquals(expected1,actual1);
    }
    public void testAddSubscription() {
        Distributor distributor = new Distributor();

        Journal journal = new Journal("Ejderhanı Nasıl Egitirsin", "ISSN123", 12, 20.0);
        distributor.addJournal(journal);

        Subscriber subscriber = new Individual("Dogukan Bas", "belikduzu", "1234567890", 12, 2022, 123);
        distributor.addSubscriber(subscriber);

        Subscription subscription = new Subscription(new DateInfo(1, 12, 2022), 1, journal, subscriber);
        assertTrue(distributor.addSubscription(journal.getIssn(), subscriber, subscription));

        assertEquals(1, journal.getSubscriptions().size());

    }



}
