package dogukanbas;

import java.io.Serializable;

public abstract class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;

    public Subscriber(String name, String address) {


        this.name = name;
        this.address = address;
    }
    public abstract String getBillingInformation();
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

}
