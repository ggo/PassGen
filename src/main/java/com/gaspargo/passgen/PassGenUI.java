/*---------------------------------------------------------------------------------------------
 *  Copyright (c) ggo. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/
package com.gaspargo.passgen;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
import javax.swing.UIManager.*;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * PassGenUI: Swing UI for PassGen.
 * 
 * @author ggo 
 */
public class PassGenUI {

    // Property archive
    private ResourceBundle rb;

    // Instance of PassGen
    private PassGen passGen;

    // Language (ISO 639)
    private String BUNDLE_LANGUAGE = "en";

    // Country (ISO 3166)
    private String BUNDLE_COUNTRY = "US";

    // Password lenght
    private int passwordLength = 6;

    // Include special character on password
    private boolean includeSpecialCharacter = false;

    // UI controls ...
    private JComboBox<String> comboBox;

    private JCheckBox checkBox;

    private JTextArea textArea;

    public PassGenUI() {
        loadResources();
        passGen = new PassGen();
    }

    /**
     * Load the resources file.
     */
    private void loadResources() {
        Locale.setDefault(new Locale(BUNDLE_LANGUAGE, BUNDLE_COUNTRY));
        rb = ResourceBundle.getBundle("passgenui");
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private void createAndShowGUI() {

        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Try to set Nimbus como Look & Feel, else keep Metal
        setLookAndFeel();

        // Create and set up the window
        JFrame frame = new JFrame("PassGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set initial size
        frame.setPreferredSize(new Dimension(640, 480));
        frame.pack();
        
        // Load the icon for the main windows
        ClassLoader rl = Thread.currentThread().getContextClassLoader();
        URL url = rl.getResource("icon.png");

        // Se the icon to the JFrame
        ImageIcon img = new ImageIcon(url);        
        frame.setIconImage(img.getImage());

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2 );

        // Main Panel
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        //mainPane.setBackground(Color.GREEN);

        // Title
        JLabel label = new JLabel(rb.getString("main.title"));
        label.setFont(new Font("Helvetica", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPane.add(label);
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));

        // ....
        // Password length list
        String s1[] = { "6", "8", "16", "32", "64", "128", "256", "512", "1024" };
        comboBox = new JComboBox<String>(s1);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboPasswordLengthChanged();
            }
        });
        comboBox.setEditable(true);

        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setMaximumSize(new Dimension(1024, 50));

        JLabel label1 = new JLabel(rb.getString("main.passwordlenght"));
        // Include special character checkbox
        checkBox = new JCheckBox(rb.getString("main.combospecialcharacter"));
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkboxSpecialCharacterPressed();
            }
        });

        panel1.add(label1);
        panel1.add(comboBox);
        panel1.add(checkBox);  
        
        //panel1.setBackground(Color.ORANGE);
        mainPane.add(panel1);   

        // Generate Button & the action listener
        JButton button = new JButton(rb.getString("main.button.generate"));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonGeneratePressed();
            }
        });
        mainPane.add(button);

        // Separator
        mainPane.add(Box.createRigidArea(new Dimension(0,5)));

        // generated password textField
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // To create scrollable Textarea
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setAlignmentY(Component.TOP_ALIGNMENT);
        mainPane.add(scroll);

        frame.add(mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * The logic when the Generate button is pressed 
     */
    public void buttonGeneratePressed() {

        passGen.setPassLen(passwordLength);
        passGen.setIncludeSpecialCharacter(includeSpecialCharacter);

        String password = passGen.generatePassword();
        textArea.setText(password);
    }

    /**
     * The logic when "Password length" combo change 
     */
    public void comboPasswordLengthChanged() {
        try {
            String selectedItem = (String)comboBox.getSelectedItem();
            int intSelectedItem = Integer.parseInt(selectedItem);

            passwordLength = intSelectedItem;
        } catch (NumberFormatException e) {
            // Textfield of combo is not a number, reset it
            comboBox.setSelectedItem("");
            comboBox.requestFocus();
        }
    }

    /**
     * The logic when checkbox "Include special character" is checked/unchecked
     */    
    public void checkboxSpecialCharacterPressed() {
        includeSpecialCharacter = checkBox.isSelected();
    }

    public void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, we keep the default & cross platform 
            // look and feel METAL.
        }        
    }

	public static void main(String[] args) {
        
        PassGenUI passGenUI = new PassGenUI();
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                passGenUI.createAndShowGUI();
            }
        });
    }

}