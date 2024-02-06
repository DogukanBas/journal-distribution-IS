package unitTests;
import dogukanbas.*;
import junit.framework.TestCase;

public class TestOfSubscription extends TestCase {
    private Subscription subscription;
    private Subscriber subscriber;
    private PaymentInfo paymentInfo;
    private Journal journal;
    private DateInfo dateInfo;
    public void setUp(){
        dateInfo = new DateInfo(1,2011,2);
        subscriber = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);
        journal = new Journal("Journal1","123123",2,100.0);
        paymentInfo = new PaymentInfo(0.1,0);
        subscription = new Subscription(dateInfo,1,journal,subscriber);
        subscription.setPaymentInfo(paymentInfo);
    }
    public void testAcceptPayment(){
        double expected1 = 0;
        double actual1 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected1,actual1);
        subscription.acceptPayment(100);
        double expected2 = 100;
        double actual2 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected2,actual2);
    }
    public void testCanSend(){
        boolean expected1 = false;
        boolean actual1 = subscription.canSend(1,2010);
        TestCase.assertEquals(expected1,actual1);

    }
    public void testGetCopies(){
        int expected1 = 1;
        int actual1 = subscription.getCopies();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetSubscriber(){
        Subscriber expected1 = subscriber;
        Subscriber actual1 = subscription.getSubscriber();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testSetCopies(){
        int expected1 = 1;
        int actual1 = subscription.getCopies();
        TestCase.assertEquals(expected1,actual1);
        subscription.setCopies(2);
        int expected2 = 2;
        int actual2 = subscription.getCopies();
        TestCase.assertEquals(expected2,actual2);
    }
    public void testGetJournal(){
        Journal expected1 = journal;
        Journal actual1 = subscription.getJournal();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testSetJournal(){
        Journal expected1 = journal;
        Journal actual1 = subscription.getJournal();
        TestCase.assertEquals(expected1,actual1);
        Journal journal2 = new Journal("Journal2","123123",2,100.0);
        subscription.setJournal(journal2);
        Journal expected2 = journal2;
        Journal actual2 = subscription.getJournal();
        TestCase.assertEquals(expected2,actual2);
    }
    public void testGetPayment(){
        double expected1 = 0;
        double actual1 = subscription.getPayment();
        TestCase.assertEquals(expected1,actual1);
        subscription.acceptPayment(100);
        double expected2 = 100;
        double actual2 = subscription.getPayment();
        TestCase.assertEquals(expected2,actual2);
    }

    @Override
    public String toString() {
        return "TestOfSubscription{" +
                "subscription=" + subscription +
                ", subscriber=" + subscriber +
                ", paymentInfo=" + paymentInfo +
                ", journal=" + journal +
                ", dateInfo=" + dateInfo +
                '}';
    }
}
