package gui;

import dogukanbas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubscriptionPage {

    public void run(Journal selectedJournal, Distributor distributor) {
        JFrame frame = new JFrame("Add Subscription for " + selectedJournal.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultListModel<String> subscriberListModel = new DefaultListModel<>();


        for (int i = 0; i < distributor.getSubscribers().size(); i++) {
            subscriberListModel.addElement(distributor.getSubscribers().get(i).getName());
        }

        JList<String> subscriberList = new JList<>(subscriberListModel);
        JScrollPane scrollPane = new JScrollPane(subscriberList);
        JButton addSubscriberButton = new JButton("Select the subscriber");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(addSubscriberButton, BorderLayout.SOUTH);

        frame.setSize(400, 400);
        frame.setVisible(true);

        addSubscriberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = subscriberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Subscriber selectedSubscriber = distributor.getSubscribers().get(selectedIndex);


                    JFrame f = new JFrame();


                    JLabel l1, l2, l3, l;
                    l = new JLabel("Copies");
                    l1 = new JLabel("Start Month");
                ;
                    l3 = new JLabel("Start Year");


                    JTextField t1, t2, t3, t;
                    t = new JTextField();
                    t1 = new JTextField();

                    t3 = new JTextField();

                    JButton b = new JButton("Add Subscription");

                    f.setLayout(new GridLayout(5, 2));
                    f.add(l);
                    f.add(t);
                    f.add(l1);
                    f.add(t1);

                    f.add(l3);
                    f.add(t3);
                    f.add(b);


                    f.setSize(400, 200);
                    f.setVisible(true);

                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int startMonth=Integer.parseInt(t1.getText());
                            int endMonthDefine;
                            if(startMonth==1){endMonthDefine=12;}
                            else{endMonthDefine=startMonth-1;}
                            int startYear=Integer.parseInt(t3.getText());

                            distributor.addSubscription(selectedJournal.getIssn(), selectedSubscriber,
                                    new Subscription(new DateInfo(startMonth,startYear,endMonthDefine),
                                            Integer.parseInt(t.getText()), selectedJournal, selectedSubscriber));

                            f.dispose();


                            frame.dispose();

                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a subscriber.");
                }
                distributor.saveState("state.bin");
            }

        });
    }


    public void run(Subscriber selectedSubscriber, Distributor distributor) {
        JFrame frame = new JFrame("Add Subscription for " + selectedSubscriber.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultListModel<String> journalListModel = new DefaultListModel<>();


        for (Journal journal : distributor.getJournals().values()) {
            journalListModel.addElement(journal.getName()+"- "+journal.getIssn());
        }

        JList<String> journalList = new JList<>(journalListModel);
        JScrollPane scrollPane = new JScrollPane(journalList);
        JButton addSubscriptionButton = new JButton("Select the journal");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(addSubscriptionButton, BorderLayout.SOUTH);

        frame.setSize(400, 400);
        frame.setVisible(true);

        addSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = journalList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String issn=journalList.getSelectedValue().split("- ")[1];
                    Journal selectedJournal = distributor.searchJournal(issn);



                    JFrame f = new JFrame();


                    JLabel l1, l2, l3, l;
                    l = new JLabel("Copies");
                    l1 = new JLabel("Start Month");

                    l3 = new JLabel("Start Year");

                    JTextField t1, t2, t3, t;
                    t = new JTextField();
                    t1 = new JTextField();
                    t3 = new JTextField();

                    JButton b = new JButton("Add Subscription");


                    f.setLayout(new GridLayout(5, 2));
                    f.add(l);
                    f.add(t);
                    f.add(l1);
                    f.add(t1);
                    f.add(l3);
                    f.add(t3);
                    f.add(b);

                    f.setSize(400, 200);
                    f.setVisible(true);


                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int startMonth=Integer.parseInt(t1.getText());
                            int endMonthDefine;
                            if(startMonth==1){endMonthDefine=12;}
                            else{endMonthDefine=startMonth-1;}
                            int startYear=Integer.parseInt(t3.getText());

                            distributor.addSubscription(selectedJournal.getIssn(), selectedSubscriber,
                                    new Subscription(new DateInfo(startMonth,startYear,endMonthDefine),
                                            Integer.parseInt(t.getText()), selectedJournal, selectedSubscriber));


                            f.dispose();

                            frame.dispose();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a journal.");
                }
                distributor.saveState("state.bin");
            }

        });
    }


    private void updateSubscriptionList(JList<String> subscriptionList, Subscriber selectedSubscriber, Distributor distributor) {
        DefaultListModel<String> model = (DefaultListModel<String>) subscriptionList.getModel();
        model.clear();

        for (Journal journal : distributor.getJournals().values()) {
            if (journal != null && journal.getSubscriptions() != null) {
                for (Subscription subscription : journal.getSubscriptions()) {
                    if (subscription != null && subscription.getSubscriber() != null &&
                            subscription.getSubscriber().getName().equals(selectedSubscriber.getName())) {
                        model.addElement(journal.getName() + " - Copies: " + subscription.getCopies());
                    }
                }
            }
        }
    }
}