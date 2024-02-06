package gui;

import dogukanbas.*;

import javax.swing.*;

public class AddJournalPage {

    public void run(Distributor distributor) {
        JFrame f=new JFrame();

        JLabel l1,l2,l3,l4;
        l1=new JLabel("  ISSN");
        l1.setBounds(50,50, 400,30);
        l2=new JLabel(" Name");
        l2.setBounds(50,100, 400,30);
        l3=new JLabel(" Frequency");
        l3.setBounds(50,150, 400,30);
        l4=new JLabel(" Issue Price");
        l4.setBounds(50,200, 400,30);

        JTextField t1,t2,t3,t4;
        t1=new JTextField();
        t1.setBounds(50,50, 400,30);
        t2=new JTextField();
        t2.setBounds(50,100, 400,30);
        t3=new JTextField();
        t3.setBounds(50,150, 400,30);
        t4=new JTextField();
        t4.setBounds(50,200, 400,30);

        JButton b=new JButton("Add Journal");
        b.setBounds(50,250,400,30);

        JButton b1=new JButton("Back");
        b1.setBounds(50,300,400,30);

        JButton b2=new JButton("Log Out");
        b2.setBounds(50,350,400,30);
        f.add(l1);f.add(l2);f.add(l3);f.add(l4);

        f.add(t1);f.add(t2);f.add(t3);f.add(t4);
        f.add(b);f.add(b1);f.add(b2);
        f.setSize(700,700);
        f.setLayout(null);
        f.setVisible(true);
        b.addActionListener(e -> {

            String issn = t1.getText();
            String name = t2.getText();
            int frequency = Integer.parseInt(t3.getText());
            double issuePrice = Double.parseDouble(t4.getText());

            if (distributor.searchJournal(issn) != null) {

                JOptionPane.showMessageDialog(f, "Journal already exists");
            } else {
                distributor.addJournal(new Journal(name, issn, frequency, issuePrice));
                JOptionPane.showMessageDialog(f, "Journal added successfully");
            }


            f.dispose();

            DistributorPage distributorPage = new DistributorPage();

            distributor.saveState("state.bin");
            distributorPage.run();
        });

        b1.addActionListener(e -> {


            f.dispose();

            DistributorPage distributorPage = new DistributorPage();
            distributor.saveState("state.bin");
            distributorPage.run();
        });



    }
}
