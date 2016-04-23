package com;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HTMLRenderer implements ViewerHTML {

    private String htmlUrl = "";

    @Override
    public void setHTMLUrl(String html) {
        htmlUrl = html;
    }

    @Override
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                //Create a panel
                JEditorPane jEditorPane = new JEditorPane();
                jEditorPane.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(jEditorPane);

                //Try to access the html file
                File htmlFile = new File(htmlUrl);
                try {
                    jEditorPane.setPage(htmlFile.toURI().toURL());
                } catch (IOException e) {
                    jEditorPane.setText("<h1>Error : couldn't load file</h1>");
                }

                // Add all to a windows
                JFrame j = new JFrame("Newsletter");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                //Set proper close operation
                j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //Display the window
                j.setSize(new Dimension(1024, 768));
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
        });
    }
}
