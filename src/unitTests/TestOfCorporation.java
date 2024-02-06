package unitTests;
import dogukanbas.Corporation;
import junit.framework.TestCase;
public class TestOfCorporation extends TestCase{
    private Corporation corporation,corporation2;
    public void setUp(){
        corporation = new Corporation("Bosch as","Istanbul",123,"Garanti",1,1,2021,123456);

    }
    public void testGetBillingInformation(){
        String expected1 = "Name: Bosch as\n" +
                "Address: Istanbul\n" +
                "Bank code: 123\n" +
                "Bank name: Garanti\n" +
                "Issue date: 1/1/2021\n" +
                "Account number: 123456\n";
        String actual1 = corporation.getBillingInformation();
        TestCase.assertEquals(expected1,actual1);




    }
    public void testToString(){
        String expected1 = "Name: Bosch as\n" +
                "Address: Istanbul\n" +
                "Bank code: 123\n" +
                "Bank name: Garanti\n" +
                "Issue date: 1/1/2021\n" +
                "Account number: 123456\n";
        String actual1 = corporation.toString();
        TestCase.assertEquals(expected1,actual1);


    }
    public void testGetName(){
        String expected1 = "Bosch as";
        String actual1 = corporation.getName();
        TestCase.assertEquals(expected1,actual1);
    }


}
