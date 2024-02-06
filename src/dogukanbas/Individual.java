package dogukanbas;

import java.io.Serializable;

public class Individual extends Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String creditCardNr;
    private final int expireMonth;
    private final int expireYear;
    private final int CCV;

    public Individual(String name, String address, String creditCardNr, int expireMonth, int expireYear, int CCV) {
        super(name, address);
        this.creditCardNr = creditCardNr;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.CCV = CCV;
    }
    public String getBillingInformation() {
        return "Name: " + super.getName() + "\n" +
                "Address: " + super.getAddress() + "\n" +
                "Credit card number: " + creditCardNr + "\n" +
                "Expire date: " + expireMonth + "/" + expireYear + "\n" +
                "CCV: " + CCV + "\n";
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String toString() {
        return getBillingInformation();
    }
}
