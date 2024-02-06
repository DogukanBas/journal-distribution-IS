package dogukanbas;

import java.io.*;
import java.util.*;

public class Distributor implements Serializable {

    private static final long serialVersionUID = 1L;
    private Hashtable<String, Journal> journals;
    private Vector<Subscriber> subscribers;
    public Distributor(){

        journals = new Hashtable<String, Journal>();
        subscribers = new Vector<Subscriber>();
    }
    public boolean addJournal(Journal journal){
        if(journals.containsKey(journal.getIssn())){
            return false;
        }
        journals.put(journal.getIssn(), journal);
        return true;
    }

    public Journal searchJournal(String issn){
        if(!journals.containsKey(issn)){
            return null;
        }
        return journals.get(issn);
    }
    public boolean addSubscriber(Subscriber subscriber){
        if(subscribers.contains(subscriber)){
            return false;
        }
        subscribers.add(subscriber);
        return true;
    }
    public Subscriber searchSubscriber(String name){
        for(Subscriber subscriber : subscribers){
            if(subscriber.getName().equals(name)){
                return subscriber;
            }
        }
        return null;
    }

    public boolean addSubscription(String issn,Subscriber subscriber, Subscription subscription){
        Journal journal = searchJournal(issn);
        if(journal == null){
            return false;
        }
        if(!subscribers.contains(subscriber)){
            return false;
        }

        journal.addSubscription(subscription);

        return true;
    }

    public ArrayList<Subscription> ListAllSendingOrders(int month, int year){

        ArrayList<Subscription> sendingorders = new ArrayList<Subscription>();
        for(Journal journal: journals.values()){
            for(Subscription subscription : journal.getSubscriptions()){
                if(subscription.canSend(month, year)){
                    sendingorders.add(subscription);
                }
            }
        }
        return sendingorders;

    }
    public ArrayList<Subscription> listInCompletePayment(){
        ArrayList<Subscription> incomplete = new ArrayList<Subscription>();
        for(Journal journal: journals.values()){
            for(Subscription subscription : journal.getSubscriptions()){
                if(subscription.getPayment()< subscription.getCopies()*journal.getIssuePrice()){
                    incomplete.add(subscription);
                }
            }
        }
        return incomplete;

    }
    public ArrayList<Subscription> listSendingOrders(String issn, int month, int year){
        ArrayList<Subscription> sendingorders = new ArrayList<Subscription>();
        Journal journal = searchJournal(issn);
        if(journal == null){

            return sendingorders;
        }
        for(Subscription subscription : journal.getSubscriptions()){
            if(subscription.canSend(month, year)){
                sendingorders.add(subscription);
            }
        }
        return sendingorders;

    }

    public Vector<Subscription> listSubscriptionsSubscriber(String subscriber){
        Subscriber sub = searchSubscriber(subscriber);
        if(sub == null){
            return null;
        }
        Vector<Subscription> subscriptions = new Vector<Subscription>();
        for(Journal journal : journals.values()){
            for(Subscription subscription : journal.getSubscriptions()){
                if(subscription.getSubscriber().getName().equals(subscriber)){
                    subscriptions.add(subscription);
                }
            }
        }
        return subscriptions;
    }
    public Vector<Subscription> listSubscriptionsIssn(String issn){
        Journal journal = searchJournal(issn);
        if(journal == null){
            return null;
        }
        return journal.getSubscriptions();

    }
    public Hashtable<String, Journal> getJournals() {
        return journals;
    }
    public Vector<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void saveState(String filename){

        try {

            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void loadState(String filename){

        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Distributor obj = (Distributor) objectIn.readObject();
            objectIn.close();

            this.journals = obj.getJournals();
            this.subscribers = obj.getSubscribers();

        } catch (Exception ex) {

            saveState(filename);
        }
    }

    public void setJournals(Hashtable<String, Journal> journals) {
        this.journals = journals;
    }

    public void setSubscribers(Vector<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void report(){




    }




}
