package unitTests;
import dogukanbas.*;
import junit.framework.TestCase;


public class TestOfDateInfo  extends TestCase{
    private DateInfo dateInfo;
    public void setUp(){
        dateInfo = new DateInfo(1,2011,2);
    }
    public void testStartMonth(){
        int expected1 = 1;
        int actual1 = dateInfo.getStartMonth();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testStartYear(){
        int expected1 = 2011;
        int actual1 = dateInfo.getStartYear();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testEndMonth(){
        int expected1 = 2;
        int actual1 = dateInfo.getEndMonth();
        TestCase.assertEquals(expected1,actual1);
    }

}
