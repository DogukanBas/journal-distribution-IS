package dogukanbas;

import java.io.Serializable;

public class Subscription implements Serializable {
    private DateInfo dates;
    private static final long serialVersionUID = 1L;
    private PaymentInfo paymentInfo;
    private int copies;
    private Journal journal;
    private Subscriber subscriber;

    public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber)  {
        this.dates = dates;
        if(subscriber instanceof Individual){
            this.paymentInfo = new PaymentInfo(0.1,0);
        }
        else{
            this.paymentInfo = new PaymentInfo(0.2,0);
        }
        this.copies = copies;
        this.journal = journal;
        this.subscriber = subscriber;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void acceptPayment(double amount){
        paymentInfo.increasePayment(amount);
    }
    public boolean canSend(int issueMonth,int issueYear){
        if(issueYear < dates.getStartYear()){
            return false;
        }
        else if(issueYear == dates.getStartYear()){
            if(issueMonth < dates.getStartMonth()){
                return false;
            }
        }

        if(issueYear > dates.getEndYear()){
            return false;
        }
        else if(issueYear == dates.getEndYear()){
            if(issueMonth > dates.getEndMonth()){
                return false;
            }
        }


        return true;
    }


    public int getCopies() {
        return copies;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
    public double getPayment(){
        return paymentInfo.getReceivedPayment();
    }
    public DateInfo getDates() {
        return dates;
    }
    @Override
    public String toString() {
        return "Subscription{" +
                "dates=" + dates +
                ", paymentInfo=" + paymentInfo +
                ", copies=" + copies +
                ", journal=" + journal +
                ", subscriber=" + subscriber +
                '}';
    }
}
