package gui;

import dogukanbas.Journal;
import dogukanbas.Subscriber;
import dogukanbas.Subscription;
import dogukanbas.Distributor;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class DistributorPage implements Runnable{


    @Override


    public void run() {

        JFrame frame = new JFrame("Distributor Application");
        Distributor distributor = new Distributor();
        distributor.loadState("state.bin");


        JTabbedPane tabs = new JTabbedPane();
        JPanel JournalOperations = createJournalTab(distributor,frame);
        JPanel SubscriberOperations = createSubscriberTab(distributor,frame);
        JPanel PaymentOperations = createPaymentTab( distributor,  frame);


        tabs.addTab("Journal Operations", JournalOperations);
        tabs.addTab("Subscriber Operations", SubscriberOperations);
        tabs.addTab("Payment Operations", PaymentOperations);


        frame.getContentPane().add(tabs);


        frame.setSize(1000 , 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private JPanel createJournalTab(Distributor distributor, JFrame frame) {
        JPanel JournalOperations = new JPanel(new BorderLayout());
        DefaultListModel<String> journalListModel = new DefaultListModel<>();
        JLabel searchLabel = new JLabel("Show details of journal by issn:");
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchTextField.getText();
                Journal foundJournal = distributor.searchJournal(searchTerm);

                if (foundJournal != null) {
                    JOptionPane.showMessageDialog(null, "Journal Found: " + foundJournal.getName());
                    displayDetails(foundJournal);
                } else {
                    JOptionPane.showMessageDialog(null, "Journal not found.");
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);

        Enumeration<String> issns = distributor.getJournals().keys();
        while (issns.hasMoreElements()) {
            String issn = issns.nextElement();
            Journal journal = distributor.getJournals().get(issn);
            journalListModel.addElement(journal.getIssn() + " || " + journal.getName() + "|| "+journal.getIssuePrice()+ " - tl");
        }

        JList<String> journalList = new JList<>(journalListModel);
        JScrollPane scrollPane = new JScrollPane(journalList);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.saveState("state.bin");
                frame.dispose();
            }
        });
        JButton selectButton = new JButton("List /add /delete subscriptions of selected journal");
        JButton seeDetailsButton = new JButton("See Details");

        Journal  selectedJournal = null;

        seeDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(journalList.isSelectedIndex(journalList.getSelectedIndex())){
                    String selectedIssn = (String) journalList.getSelectedValue().split(" ")[0];
                    Journal selectedJournal = distributor.searchJournal(selectedIssn);
                    JOptionPane.showMessageDialog(null, "Details:\n" + selectedJournal.toString(), "Details", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please select a journal to see details.");
                }
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = journalList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedIssn = (String) journalList.getSelectedValue().split(" ")[0];
                    Journal selectedJournal = distributor.searchJournal(selectedIssn);
                    ListSubscriptionPage listSubscriptionPage = new ListSubscriptionPage();
                    listSubscriptionPage.run(selectedJournal, distributor);
                    updateJournalList(journalList, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a journal to delete.");
                }
            }
        });

        JButton addJournalButton = new JButton("Add New Journal");
        addJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddJournalPage addJournalPage = new AddJournalPage();
                frame.dispose();
                addJournalPage.run(distributor);
            }
        });

        JButton deleteJournalButton = new JButton("Delete Selected Journal");
        deleteJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = journalList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedIssn = (String) journalList.getSelectedValue().split(" ")[0];
                    distributor.getJournals().remove(selectedIssn);
                    updateJournalList(journalList, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a journal to delete.");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(selectButton);
        buttonPanel.add(seeDetailsButton);
        buttonPanel.add(addJournalButton);
        buttonPanel.add(deleteJournalButton);
        buttonPanel.add(exitButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JournalOperations.add(topPanel, BorderLayout.NORTH);
        JournalOperations.add(panel, BorderLayout.CENTER);

        return JournalOperations;
    }


    private JPanel createSubscriberTab(Distributor distributor, JFrame frame) {
        JPanel subscriberOperations = new JPanel(new BorderLayout());
        DefaultListModel<String> subscriberListModel = new DefaultListModel<>();

        for (int i = 0; i < distributor.getSubscribers().size(); i++) {
            subscriberListModel.addElement(distributor.getSubscribers().get(i).getName());
        }

        JList<String> subscriberList = new JList<>(subscriberListModel);
        JScrollPane scrollPane = new JScrollPane(subscriberList);

        JButton addSubscriberButton = new JButton("Add New Subscriber");
        JButton deleteSubscriberButton = new JButton("Delete Selected Subscriber");
        JButton selectButton = new JButton("List Subscriptions of Selected Subscriber");
        JButton seeDetailsButton = new JButton("See Details");


        seeDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedName = (String) subscriberList.getSelectedValue();
                    Subscriber selectedSubscriber = distributor.searchSubscriber(selectedName);

                    JOptionPane.showMessageDialog(null, "Details:\n" + selectedSubscriber.getBillingInformation(), "Details", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscriber to see details.");
                }
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.saveState("state.bin");
                frame.dispose();
            }
        });



        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedName = (String) subscriberList.getSelectedValue();
                    Subscriber selectedSubscriber = distributor.searchSubscriber(selectedName);

                    ListSubscriptionPage listSubscriptionPage = new ListSubscriptionPage();

                    listSubscriptionPage.run(selectedSubscriber, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscriber to list subscriptions.");
                }
            }
        });


        addSubscriberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddSubscriberPage addSubscriberPage = new AddSubscriberPage();
                addSubscriberPage.run(distributor, frame);
                updateSubscriberList(subscriberList, distributor);

                frame.dispose();

            }
        });


        deleteSubscriberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedName = (String) subscriberList.getSelectedValue();
                    distributor.getSubscribers().remove(selectedIndex);
                    updateSubscriberList(subscriberList, distributor);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscriber to delete.");
                }
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(selectButton);
        buttonPanel.add(addSubscriberButton);
        buttonPanel.add(deleteSubscriberButton);
        buttonPanel.add(seeDetailsButton);



        JLabel searchLabel = new JLabel("Show details of subscriber:");
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchTextField.getText();

                Subscriber foundSubscriber = distributor.searchSubscriber(searchTerm);

                if (foundSubscriber != null) {
                    JOptionPane.showMessageDialog(null, "Subscriber Found: " + foundSubscriber.getName());
                    displayDetails(foundSubscriber);
                } else {
                    JOptionPane.showMessageDialog(null, "Subscriber not found.");
                }

            }
        });


        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);


        JPanel bottomButtonPanel = new JPanel(new FlowLayout());
        bottomButtonPanel.add(buttonPanel);
        bottomButtonPanel.add(seeDetailsButton);
        bottomButtonPanel.add(exitButton);




        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomButtonPanel, BorderLayout.SOUTH);

        subscriberOperations.add(panel, BorderLayout.CENTER);

        frame.dispose();
        return subscriberOperations;
    }
    private JPanel createPaymentTab(Distributor distributor, JFrame frame) {

        JPanel paymentOperations = new JPanel(new BorderLayout());
        JButton listIncompletePayments = new JButton("List Incomplete Payments");
        JButton listAllSendingOrders = new JButton("List All Sending Orders");
        JButton listSendingOrdersGivenIssn = new JButton("List Sending Orders Given Issn");
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.saveState("state.bin");
                frame.dispose();
            }
        });
        listIncompletePayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Subscription> incomplete = distributor.listInCompletePayment();
                if(incomplete.size() == 0){
                    JOptionPane.showMessageDialog(null, "No incomplete payments");
                }
                else{
                    String message = "";
                    for(Subscription subscription : incomplete){
                        message += "Subscriber name: " + subscription.getSubscriber().getName() + "\n" +
                                "Copies: " + subscription.getCopies() + "\n" +
                                "Journal name: " + subscription.getJournal().getName() + "\n" +
                                "Issue price: " + subscription.getJournal().getIssuePrice() + "\n" +
                                "Total price: " + subscription.getCopies()*subscription.getJournal().getIssuePrice() + "\n"+
                                "Received Payment " + subscription.getPayment()+"\n\n";
                    }

                    JOptionPane.showMessageDialog(null, message);
                }
                distributor.saveState("state.bin");
            }
        });
listAllSendingOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame();
                JLabel l1, l2;
                l1 = new JLabel("Month");
                l1.setBounds(50, 50, 200, 30);


                JTextField month = new JTextField();
                month.setBounds(260, 50, 200, 30);

                l2 = new JLabel("Year");
                l2.setBounds(50, 100, 200, 30);

                JTextField year = new JTextField();
                year.setBounds(260, 100, 200, 30);

                JButton b = new JButton("List All Sending Orders");
                b.setBounds(50, 200, 400, 30);

                f.add(l1);
                f.add(month);
                f.add(l2);
                f.add(year);
                f.add(b);


                f.setSize(700, 700);
                f.setLayout(null);
                f.setVisible(true);

                b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<Subscription> sendingorders = distributor.ListAllSendingOrders(Integer.parseInt(month.getText()),Integer.parseInt(year.getText()));
                        if(sendingorders.size() == 0){
                            JOptionPane.showMessageDialog(null, "No sending orders");
                            distributor.saveState("state.bin");
                        }
                        else{
                            String message = "";
                            for(Subscription subscription : sendingorders){
                                message += "Subscriber name: " + subscription.getSubscriber().getName() + "\n" +
                                        "Copies: " + subscription.getCopies() + "\n" +
                                        "Journal name: " + subscription.getJournal().getName() + "\n" +
                                        "Issue price: " + subscription.getJournal().getIssuePrice() + "\n" +
                                        "Total price: " + subscription.getCopies()*subscription.getJournal().getIssuePrice() + "\n\n\n";
                            }

                            JOptionPane.showMessageDialog(null, message);
                            distributor.saveState("state.bin");

                    }
                    }});


            }

            });
        listSendingOrdersGivenIssn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame();
                JLabel l1, l2, l3;
                l1 = new JLabel("Month");
                l1.setBounds(50, 50, 200, 30);
                JTextField month = new JTextField();
                month.setBounds(260, 50, 200, 30);
                l2 = new JLabel("Year");
                l2.setBounds(50, 100, 200, 30);
                JTextField year = new JTextField();
                year.setBounds(260, 100, 200, 30);
                l3 = new JLabel("ISSN");
                l3.setBounds(50, 150, 200, 30);
                JTextField issn = new JTextField();
                issn.setBounds(260, 150, 200, 30);
                JButton b = new JButton("List Sending Orders Given Issn");
                b.setBounds(50, 200, 400, 30);
                f.add(l1);
                f.add(month);
                f.add(l2);
                f.add(year);
                f.add(l3);
                f.add(issn);
                f.add(b);
                f.setSize(700, 700);
                f.setLayout(null);
                f.setVisible(true);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<Subscription> sendingorders = distributor.listSendingOrders(issn.getText(),Integer.parseInt(month.getText()),Integer.parseInt(year.getText()));
                        if(sendingorders.size() == 0){
                            JOptionPane.showMessageDialog(null, "No sending orders");
                        }
                        else{
                            String message = "";
                            for(Subscription subscription : sendingorders){
                                message += "Subscriber name: " + subscription.getSubscriber().getName() + "\n" +
                                        "Copies: " + subscription.getCopies() + "\n" +
                                        "Journal name: " + subscription.getJournal().getName() + "\n" +
                                        "Issue price: " + subscription.getJournal().getIssuePrice() + "\n" +
                                        "Total price: " + subscription.getCopies()*subscription.getJournal().getIssuePrice() + "\n\n\n";
                            }

                            JOptionPane.showMessageDialog(null, message);


                        }
                        distributor.saveState("state.bin");
                    }});


            }

        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(listIncompletePayments);
        buttonPanel.add(listAllSendingOrders);
        buttonPanel.add(listSendingOrdersGivenIssn);
        buttonPanel.add(exitButton);
        paymentOperations.add(buttonPanel, BorderLayout.CENTER);
        return paymentOperations;




    }

    private void displayDetails(Object object) {

        String details = object.toString();
        JOptionPane.showMessageDialog(null, "Details:\n" + details, "Details", JOptionPane.INFORMATION_MESSAGE);
    }
    private void filterSubscriberList(DefaultListModel<String> model, Distributor distributor, String searchQuery) {
        model.clear();

        for (Subscriber subscriber : distributor.getSubscribers()) {
            if (subscriber.getName().toLowerCase().contains(searchQuery)) {
                model.addElement(subscriber.getName());
            }
        }
    }



    private void updateJournalList(JList<String> journalList,Distributor distributor){
        DefaultListModel<String> model = (DefaultListModel<String>) journalList.getModel();
        model.clear();

        Enumeration<String> issns = distributor.getJournals().keys();
        while (issns.hasMoreElements()) {
            String issn = issns.nextElement();
            Journal journal = distributor.getJournals().get(issn);
            model.addElement(journal.getIssn() + " - " + journal.getName() + " - TL" + journal.getIssuePrice());
        }
    }
    private void updateSubscriberList(JList<String> subscriberList,Distributor distributor){
        DefaultListModel<String> model = (DefaultListModel<String>) subscriberList.getModel();
        model.clear();
        for(int i = 0; i < distributor.getSubscribers().size(); i++){
            model.addElement(distributor.getSubscribers().get(i).getName());
        }
    }



}
