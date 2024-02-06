package unitTests;

import dogukanbas.Individual;
import junit.framework.TestCase;

public class TestOfIndividual extends TestCase{
    private Individual individual,individual2;
    public void setUp(){
        individual = new Individual("Dogukan Bas","Istanbul","0000111122223333",12,1,123);

    }
    public void testGetBillingInformation(){
        String expected1 = "Name: Dogukan Bas\n" +
                "Address: Istanbul\n" +
                "Credit card number: 0000111122223333\n" +
                "Expire date: 12/1\n" +
                "CCV: 123\n";
        String actual1 = individual.getBillingInformation();
        TestCase.assertEquals(expected1,actual1);}
    public void testToString(){
        String expected1 = "Name: Dogukan Bas\n" +
                "Address: Istanbul\n" +
                "Credit card number: 0000111122223333\n" +
                "Expire date: 12/1\n" +
                "CCV: 123\n";
        String actual1 = individual.toString();
        TestCase.assertEquals(expected1,actual1);

    }
    public void testGetAddress(){
        String expected1 = "Istanbul";
        String actual1 = individual.getAddress();
        TestCase.assertEquals(expected1,actual1);
    }
    public void testGetName(){
        String expected1 = "Dogukan Bas";
        String actual1 = individual.getName();
        TestCase.assertEquals(expected1,actual1);
    }


}
