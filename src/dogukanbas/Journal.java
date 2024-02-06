package dogukanbas;

import java.io.Serializable;
import java.util.*;

public class Journal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name,issn;
    private int frequency;
    private double issuePrice;

    private Vector<Subscription> subscriptions;
    public Journal(String name, String issn, int frequency, double issuePrice) {
        this.name = name;
        this.issn = issn;
        this.frequency = frequency;
        this.issuePrice = issuePrice;
        subscriptions = new Vector<Subscription>();
    }
    public void addSubscription(Subscription subscription){

        for(Subscription sub : subscriptions){
            if(sub.getSubscriber().getName().equals(subscription.getSubscriber().getName())){
                sub.setCopies(subscription.getCopies()+1);

                return;
            }
        }

        subscriptions.add(subscription);






    }
    public String getName() {
        return name;
    }
    public Vector<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public double getIssuePrice() {
        return issuePrice;
    }

    public String getIssn() {
        return issn;
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                ", issn='" + issn + '\'' +
                ", frequency=" + frequency +
                ", issuePrice=" + issuePrice ;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setIssuePrice(double issuePrice) {
        this.issuePrice = issuePrice;
    }

    public void setSubscriptions(Vector<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}

