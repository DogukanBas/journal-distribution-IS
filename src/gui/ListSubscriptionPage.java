package gui;

import dogukanbas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

public class ListSubscriptionPage {
    public void run(Journal selectedJournal, Distributor distributor) {

        JFrame frame = new JFrame("List Subscriptions for " + selectedJournal.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultListModel<String> subscriptionListModel = new DefaultListModel<>();
        JList<String> subscriptionList = new JList<>(subscriptionListModel);
        JScrollPane scrollPane = new JScrollPane(subscriptionList);

        JButton addSubscriptionButton = new JButton("Add New Subscription");

        addSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddSubscriptionPage addSubscriptionPage = new AddSubscriptionPage();
                addSubscriptionPage.run(selectedJournal, distributor);
                updateSubscriptionList(subscriptionList, selectedJournal);
                frame.dispose();
                distributor.saveState("state.bin");
            }

        });

        JButton deleteSubscriptionButton = new JButton("Delete Selected Subscription");
        deleteSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriptionList.getSelectedIndex();
                if (selectedIndex != -1) {
                    selectedJournal.getSubscriptions().removeElementAt(selectedIndex);
                    updateSubscriptionList(subscriptionList, selectedJournal);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscription to delete.");
                }
                distributor.saveState("state.bin");
            }
        });
        JButton viewDetailsButton = new JButton(" Details");
viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriptionList.getSelectedIndex();
                if (selectedIndex != -1) {

                    String selectedSubscription = subscriptionListModel.getElementAt(selectedIndex);
                    String[] parts = selectedSubscription.split(" - Copies: ");
                    for(Journal journal : distributor.getJournals().values()){
                        if(journal.getName().equals(selectedJournal.getName())){

                            for(Subscription subscription : journal.getSubscriptions()){
                                if(subscription.getSubscriber().getName().equals(parts[0])){

                                    JOptionPane.showMessageDialog(null, "Subscription Details for " + selectedJournal.getName() + "\n\n" + subscription.toString());
                                }
                            }
                        }
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscription to view details.");
                }
                distributor.saveState("state.bin");
            }
        });



        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addSubscriptionButton);
        buttonPanel.add(deleteSubscriptionButton);
        buttonPanel.add(viewDetailsButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);


        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        updateSubscriptionList(subscriptionList, selectedJournal);
    }

    public void run(Subscriber selectedSubscriber, Distributor distributor) {
        JFrame frame = new JFrame("Manage Subscriptions for " + selectedSubscriber.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> subscriptionListModel = new DefaultListModel<>();
        JList<String> subscriptionList = new JList<>(subscriptionListModel);
        JScrollPane scrollPane = new JScrollPane(subscriptionList);

        JButton addSubscriptionButton = new JButton("Add New Subscription");
        JButton deleteSubscriptionButton = new JButton("Delete Selected Subscription");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addSubscriptionButton);
        buttonPanel.add(deleteSubscriptionButton);



        populateSubscriptionList(subscriptionListModel, selectedSubscriber, distributor);


        addSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddSubscriptionPage addSubscriptionPage = new AddSubscriptionPage();
                addSubscriptionPage.run(selectedSubscriber, distributor);
                updateSubscriptionList(subscriptionList, selectedSubscriber, distributor);
                frame.dispose();
                distributor.saveState("state.bin");


            }
        });

        deleteSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriptionList.getSelectedIndex();

                if (selectedIndex != -1) {


                    String selectedSubscription = subscriptionListModel.getElementAt(selectedIndex);
                    Journal selectedJournal = findJournalBySubscription(selectedSubscription, distributor);



                    for(int i=0;i<selectedJournal.getSubscriptions().size();i++){
                        if(selectedJournal.getSubscriptions().get(i).getSubscriber().getName().equals(selectedSubscriber.getName())){
                            selectedJournal.getSubscriptions().remove(i);
                        }
                    }

                    populateSubscriptionList(subscriptionListModel, selectedSubscriber, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscription to delete.");
                }
                distributor.saveState("state.bin");
            }

        });

        JButton makePayment = new JButton("Make Payment");
        makePayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedIndex = subscriptionList.getSelectedIndex();
                if (selectedIndex != -1) {

                    String selectedSubscription = subscriptionListModel.getElementAt(selectedIndex);

                    Journal selectedJournal = findJournalBySubscription(selectedSubscription, distributor);


                    Subscription selectedSubscription2 = null;
                    for(int i=0;i<selectedJournal.getSubscriptions().size();i++){
                        if(selectedJournal.getSubscriptions().get(i).getSubscriber().getName().equals(selectedSubscriber.getName())){
                            selectedSubscription2 = selectedJournal.getSubscriptions().get(i);
                        }
                    }

                    JFrame f=new JFrame();

                    JLabel l1;
                    l1=new JLabel("  Amount of payment you want to make:");
                    l1.setBounds(50,50, 400,30);

                    JTextField t1;
                    t1=new JTextField();
                    t1.setBounds(50,100, 400,30);

                    JButton b=new JButton("Make Payment");
                    b.setBounds(50,150,400,30);
                    f.add(l1);
                    f.add(t1);
                    f.add(b);
                    f.setSize(500,500);
                    f.setLayout(null);
                    f.setVisible(true);

                    Subscription finalSelectedSubscription = selectedSubscription2;
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            double payment = Double.parseDouble(t1.getText());

                            finalSelectedSubscription.acceptPayment(payment);

                            f.dispose();
                            JOptionPane.showMessageDialog(null, "Payment added succesfully");

                        }
                    });


                    populateSubscriptionList(subscriptionListModel, selectedSubscriber, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscription to make payment.");
                }
                distributor.saveState("state.bin");

            }
        });
        buttonPanel.add(makePayment);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setVisible(true);


    }

    private void populateSubscriptionList(DefaultListModel<String> subscriptionListModel, Subscriber selectedSubscriber, Distributor distributor) {

        subscriptionListModel.clear();


        for (Journal journal : distributor.getJournals().values()) {
            for (Subscription subscription : journal.getSubscriptions()) {
                if (subscription.getSubscriber().equals(selectedSubscriber)) {
                    subscriptionListModel.addElement(journal.getName());
                    break;
                }
            }
        }
    }

    private Journal findJournalBySubscription(String subscriptionName, Distributor distributor) {

        for (Journal journal : distributor.getJournals().values()) {
            if (journal.getName().equals(subscriptionName)) {
                return journal;
            }
        }
        return null;
    }




    private void populateJournalList(DefaultListModel<String> journalListModel, Collection<Journal> journals) {
        for (Journal journal : journals) {
            journalListModel.addElement(journal.getIssn() + " - " + journal.getName());
        }
    }

    private void showSubscriptionDetails(Journal selectedJournal, Subscriber selectedSubscriber, Distributor distributor) {
        JFrame detailsFrame = new JFrame("Subscription Details for " + selectedJournal.getName());
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        detailsFrame.setSize(400, 400);
        detailsFrame.setVisible(true);
    }


    private void updateSubscriptionList(JList<String> subscriptionList, Journal selectedJournal) {
        DefaultListModel<String> model = (DefaultListModel<String>) subscriptionList.getModel();
        model.clear();

        Vector<Subscription> subscriptions = selectedJournal.getSubscriptions();
        for (Subscription subscription : subscriptions) {
            Subscriber subscriber = subscription.getSubscriber();
            model.addElement(subscriber.getName() + " - Copies: " + subscription.getCopies());
        }
    }



    private void updateSubscriptionList(JList<String> subscriptionList, Subscriber selectedSubscriber, Distributor distributor) {
        DefaultListModel<String> model = (DefaultListModel<String>) subscriptionList.getModel();
        model.clear();

        for (Journal journal : distributor.getJournals().values()) {
            model.addElement(journal.getName() );
        }
    }
}
