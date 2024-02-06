package gui;

import dogukanbas.*;

import javax.swing.*;
import java.awt.*;

public class ReportPage implements Runnable{
    @Override
    public void run() {
        JFrame f=new JFrame("Report Page");

        JButton b=new JButton("Subscriptions that will expire after given date");
        b.setBounds(50, 50, 300, 30);
        JButton b1=new JButton("Received annual payments in a given year range ");
        b1.setBounds(50, 100, 300, 30);
        JPanel bottomButtonPanel = new JPanel(new FlowLayout());
        bottomButtonPanel.add(b);
        bottomButtonPanel.add(b1);
        f.add(bottomButtonPanel, BorderLayout.SOUTH);



    b.addActionListener(e -> {
                JFrame f1=new JFrame();
                JLabel l1,l2;


                l1=new JLabel("Enter month");

                l2=new JLabel("Enter year");

                JTextField t1,t2;
                t1=new JTextField();

                t2=new JTextField();

                JButton b2=new JButton("Submit");

                f1.setLayout(new GridLayout(3,2));


                f1.setSize(400,400);


                f1.add(l1);f1.add(t1);f1.add(l2);f1.add(t2);f1.add(b2);
                f1.setVisible(true);
                b2.addActionListener(e1 -> {
                    Distributor distributor = new Distributor();
                    distributor.loadState("state.bin");

                    String report=" ";
                    int month = Integer.parseInt(t1.getText());
                    int year = Integer.parseInt(t2.getText());
                    for(Journal journal: distributor.getJournals().values()){
                        for(Subscription subscription: journal.getSubscriptions()){

                            if(subscription.getDates().getEndYear()<year || (subscription.getDates().getEndYear()==year && subscription.getDates().getEndMonth()<month)){
                                report += subscription.toString() + "\n";
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(f, report);

                    f1.dispose();
                });
            });
    b1.addActionListener(e -> {

                JFrame f1=new JFrame();
                JLabel l1,l2;
                l1= new JLabel("Enter start year");
                l2= new JLabel("Enter end year");
                JTextField t1,t2;
                t1=new JTextField();
                t2=new JTextField();
                JButton b2=new JButton("Submit");
                f1.setLayout(new GridLayout(3,2));
                f1.setSize(400,400);
                f1.add(l1);f1.add(t1);f1.add(l2);f1.add(t2);f1.add(b2);
                f1.setVisible(true);
                b2.addActionListener(e1 -> {
                    Distributor distributor = new Distributor();
                    distributor.loadState("state.bin");
                    String report=" ";
                    int totalPayment =0;
                    int startYear = Integer.parseInt(t1.getText());
                    int endYear = Integer.parseInt(t2.getText());
                    for(Journal journal: distributor.getJournals().values()){
                        for(Subscription subscription: journal.getSubscriptions()){

                            if(subscription.getDates().getStartYear()>=startYear && subscription.getDates().getStartYear()<=endYear){
                                report += subscription.getJournal().getName()+ " " + subscription.getSubscriber().getName() + "Payment:" + subscription.getPayment() + "\n";
                                totalPayment += subscription.getPayment();
                            }
                        }
                    }
                    report += "\n\nTotal payment: " + totalPayment;
                    JOptionPane.showMessageDialog(f, report);
                    f1.dispose();
                });
            });


        f.setSize(800 , 200);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        }


}
