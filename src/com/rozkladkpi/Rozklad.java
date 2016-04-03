package com.rozkladkpi;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kruku on 02.04.2016.
 */

/**
 * GUI form class
 */
public class Rozklad extends JFrame {
    /**
     * Params of form
     */
    private JPanel panel;
    private JList list1;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton showScheduleButton;
    private JComboBox comboBox2;
    /**
     * Information for comboboxes
     */
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] weeks = {"1", "2"};

    /**
     * Constructor
     * Initialise GUI window with params
     */
    public Rozklad() {
        super("Rozklad");
        panel.setPreferredSize(new Dimension(600, 400));
        setContentPane(panel);
        pack();
        initComboBox(days, weeks);
        initAction();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Initialise comboboxes of days and weeks
     *
     * @param days  days names
     * @param weeks weeks numbers
     */
    public void initComboBox(String[] days, String[] weeks) {
        for (String s : days) {
            comboBox1.addItem(s);
        }
        for (String i : weeks) {
            comboBox2.addItem(i);
        }
    }

    /**
     * Action listener for button
     * For each press schedule downloads and updates
     */
    public void initAction() {
        showScheduleButton.addActionListener(new ActionListener() {
            PrepareInfo prepareInfo = new PrepareInfo();

            @Override
            public void actionPerformed(ActionEvent e) {
                String group = textField1.getText();
                int index = comboBox1.getSelectedIndex();
                int week = comboBox2.getSelectedIndex();
                week++;
                try {
                    prepareInfo.initArray("http://api.rozklad.org.ua/v2/groups/" + group + "/lessons/?week=" + week, index + 1);
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(panel, "Can't show schedule,because its not find", "Schedule not find", JOptionPane.INFORMATION_MESSAGE);
                }
                list1.setListData(prepareInfo.getArrayLists().toArray());
            }
        });
    }
}
