package gui;

import dogukanbas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubscriberPage {

    public void run(Distributor distributor,JFrame frame) {

        JFrame typeFrame = new JFrame("Select Subscriber Type");
        typeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel typeLabel = new JLabel("Select Subscriber Type:");
        typeLabel.setBounds(50, 50, 200, 30);

        String[] typeOptions = {"Individual", "Corporation"};
        JComboBox<String> typeComboBox = new JComboBox<>(typeOptions);
        typeComboBox.setBounds(50, 100, 200, 30);

        JButton selectTypeButton = new JButton("Next");
        selectTypeButton.setBounds(50, 150, 200, 30);

        typeFrame.add(typeLabel);
        typeFrame.add(typeComboBox);
        typeFrame.add(selectTypeButton);


        typeFrame.setSize(300, 250);
        typeFrame.setLayout(null);
        typeFrame.setVisible(true);


        JFrame individualFrame = createIndividualFrame(distributor,frame);
        JFrame corporationFrame = createCorporationFrame(distributor,frame);


        selectTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String subscriberType = (String) typeComboBox.getSelectedItem();

                typeFrame.dispose();


                if (subscriberType.equals("Individual")) {
                    individualFrame.setVisible(true);
                } else if (subscriberType.equals("Corporation")) {
                    corporationFrame.setVisible(true);
                }
                distributor.saveState("state.bin");
            }

        });
    }

    private JFrame createIndividualFrame(Distributor distributor,JFrame frame) {
        JFrame individualFrame = new JFrame("Add New Individual");


        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(200, 30));


        JLabel addressLabel = new JLabel("Address:");
        JTextField addressTextField = new JTextField();
        addressTextField.setPreferredSize(new Dimension(200, 30));

        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        JTextField creditCardTextField = new JTextField();
        creditCardTextField.setPreferredSize(new Dimension(200, 30));

        JLabel expireMonthLabel = new JLabel("Expire Month:");
        JTextField expireMonthTextField = new JTextField();
        expireMonthTextField.setPreferredSize(new Dimension(200, 30));

        JLabel expireYearLabel = new JLabel("Expire Year:");
        JTextField expireYearTextField = new JTextField();
        expireYearTextField.setPreferredSize(new Dimension(200, 30));

        JLabel ccvLabel = new JLabel("CCV:");
        JTextField ccvTextField = new JTextField();
        ccvTextField.setPreferredSize(new Dimension(200, 30));

        JButton addButton = new JButton("Add Individual");


        individualFrame.setLayout(new BoxLayout(individualFrame.getContentPane(), BoxLayout.Y_AXIS));
        individualFrame.add(nameLabel);
        individualFrame.add(nameTextField);
        individualFrame.add(addressLabel);
        individualFrame.add(addressTextField);

        individualFrame.add(creditCardLabel);
        individualFrame.add(creditCardTextField);
        individualFrame.add(expireMonthLabel);
        individualFrame.add(expireMonthTextField);
        individualFrame.add(expireYearLabel);
        individualFrame.add(expireYearTextField);
        individualFrame.add(ccvLabel);
        individualFrame.add(ccvTextField);
        individualFrame.add(addButton);


        individualFrame.setSize(300, 400);
        individualFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        individualFrame.setVisible(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(individualFrame, "Adding Individual Subscriber: " + nameTextField.getText());

                distributor.addSubscriber(new Individual(nameTextField.getText(), addressTextField.getText(), creditCardTextField.getText(), Integer.parseInt(expireMonthTextField.getText()), Integer.parseInt(expireYearTextField.getText()), Integer.parseInt(ccvTextField.getText())));


                frame.dispose();
                individualFrame.dispose();
                distributor.saveState("state.bin");
                DistributorPage distributorPage = new DistributorPage();
                distributor.loadState("state.bin");

                distributorPage.run();


            }
        });

        return individualFrame;
    }

    private JFrame createCorporationFrame(Distributor distributor,JFrame frame) {
        JFrame corporationFrame = new JFrame("Add New Corporation");


        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(200, 30));

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressTextField = new JTextField();
        addressTextField.setPreferredSize(new Dimension(200, 30));


        JLabel bankCodeLabel = new JLabel("Bank Code:");
        JTextField bankCodeTextField = new JTextField();
        bankCodeTextField.setPreferredSize(new Dimension(200, 30));

        JLabel bankNameLabel = new JLabel("Bank Name:");
        JTextField bankNameTextField = new JTextField();
        bankNameTextField.setPreferredSize(new Dimension(200, 30));

        JLabel issueDayLabel = new JLabel("Issue Day:");
        JTextField issueDayTextField = new JTextField();
        issueDayTextField.setPreferredSize(new Dimension(200, 30));

        JLabel issueMonthLabel = new JLabel("Issue Month:");
        JTextField issueMonthTextField = new JTextField();
        issueMonthTextField.setPreferredSize(new Dimension(200, 30));

        JLabel issueYearLabel = new JLabel("Issue Year:");
        JTextField issueYearTextField = new JTextField();
        issueYearTextField.setPreferredSize(new Dimension(200, 30));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberTextField = new JTextField();
        accountNumberTextField.setPreferredSize(new Dimension(200, 30));

        JButton addButton = new JButton("Add Corporation");


        corporationFrame.setLayout(new BoxLayout(corporationFrame.getContentPane(), BoxLayout.Y_AXIS));
        corporationFrame.add(nameLabel);
        corporationFrame.add(nameTextField);
        corporationFrame.add(addressLabel);
        corporationFrame.add(addressTextField);

        corporationFrame.add(bankCodeLabel);
        corporationFrame.add(bankCodeTextField);
        corporationFrame.add(bankNameLabel);
        corporationFrame.add(bankNameTextField);
        corporationFrame.add(issueDayLabel);
        corporationFrame.add(issueDayTextField);
        corporationFrame.add(issueMonthLabel);
        corporationFrame.add(issueMonthTextField);
        corporationFrame.add(issueYearLabel);
        corporationFrame.add(issueYearTextField);
        corporationFrame.add(accountNumberLabel);
        corporationFrame.add(accountNumberTextField);
        corporationFrame.add(addButton);


        corporationFrame.setSize(300, 500);
        corporationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        corporationFrame.setVisible(false);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(corporationFrame, "Adding Corporation Subscriber: " + nameTextField.getText());

                distributor.addSubscriber(new Corporation(nameTextField.getText(), addressTextField.getText(),Integer.parseInt(bankCodeTextField.getText()), bankNameTextField.getText(), Integer.parseInt(issueDayTextField.getText()), Integer.parseInt(issueMonthTextField.getText()), Integer.parseInt(issueYearTextField.getText()), Integer.parseInt(accountNumberTextField.getText())));


                corporationFrame.dispose();
                distributor.saveState("state.bin");
                DistributorPage distributorPage = new DistributorPage();
                distributor.loadState("state.bin");

                distributorPage.run();

            }

        });

        return corporationFrame;
    }

}