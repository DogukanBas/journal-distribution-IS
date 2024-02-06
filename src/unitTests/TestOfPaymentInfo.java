package unitTests;
import dogukanbas.*;
import junit.framework.TestCase;

public class TestOfPaymentInfo extends TestCase {
    private PaymentInfo paymentInfo;
    public void setUp(){
        paymentInfo = new PaymentInfo(0.1,0);
    }
    public void testGetReceivedPayment(){
        double expected1 = 0;
        double actual1 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected1,actual1);
        paymentInfo.increasePayment(100);
        double expected2 = 100;
        double actual2 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected2,actual2);
    }
    public void testIncreasePayment(){
        double expected1 = 0;
        double actual1 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected1,actual1);
        paymentInfo.increasePayment(100);
        double expected2 = 100;
        double actual2 = paymentInfo.getReceivedPayment();
        TestCase.assertEquals(expected2,actual2);
    }

}
