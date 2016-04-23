package com;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HelloHTMLRenderer implements ViewerHTML {

    private String htmlUrl = "";

    @Override
    public void setHTMLUrl(String html) {
        htmlUrl = html;
    }

    @Override
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JEditorPane jEditorPane = new JEditorPane();
                jEditorPane.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(jEditorPane);

                File htmlFile = new File(htmlUrl);

                try {
                    jEditorPane.setPage(htmlFile.toURI().toURL());
                } catch (IOException e) {
                    jEditorPane.setText("<h1>Error : couldn't load file</h1>");
                    e.printStackTrace();
                }

                // now add it all to a frame
                JFrame j = new JFrame("Newsletter");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // make it easy to close the application
                j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // display the frame
                j.setSize(new Dimension(300, 200));

                // center the jframe, then make it visible
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
        });
    }
}
