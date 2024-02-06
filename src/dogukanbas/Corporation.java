package dogukanbas;

import java.io.Serializable;

public class Corporation extends Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bankCode;
    private String bankName;
    private int issueDay;
    private int issueMonth;
    private int issueYear;
    private int accountNumber;

    public Corporation(String name, String address,int bankCode, String bankName, int issueDay, int issueMonth, int issueYear, int accountNumber) {
        super(name, address);
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.issueDay = issueDay;
        this.issueMonth = issueMonth;
        this.issueYear = issueYear;
        this.accountNumber = accountNumber;
    }

    public String getBillingInformation() {
        return "Name: " + super.getName() + "\n" +
                "Address: " + super.getAddress() + "\n" +
                "Bank code: " + bankCode + "\n" +
                "Bank name: " + bankName + "\n" +
                "Issue date: " + issueDay + "/" + issueMonth + "/" + issueYear + "\n" +
                "Account number: " + accountNumber + "\n";
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return getBillingInformation();
    }
}