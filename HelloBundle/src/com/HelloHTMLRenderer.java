package com;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HelloHTMLRenderer {

    public HelloHTMLRenderer(){

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JEditorPane jEditorPane = new JEditorPane();
                jEditorPane.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(jEditorPane);

                // add an html editor kit
                HTMLEditorKit kit = new HTMLEditorKit();
                jEditorPane.setEditorKit(kit);

                // add some styles to the html
                StyleSheet styleSheet = kit.getStyleSheet();
                styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");

                // create some simple html as a string
                String htmlString = "<html>\n"
                        + "<body>\n"
                        + "<h1>Welcome World!</h1>\n"
                        + "<h2>This is an H2 header</h2>\n"
                        + "<p>This is some sample text</p>\n"
                        + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n"
                        + "</body>\n";

                // create a document, set it on the jeditorpane, then add the html
                Document doc = kit.createDefaultDocument();
                jEditorPane.setDocument(doc);
                jEditorPane.setText(htmlString);


                // now add it all to a frame
                JFrame j = new JFrame("Hello Bundle Test");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // make it easy to close the application
                j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // display the frame
                j.setSize(new Dimension(300,200));

                // center the jframe, then make it visible
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
        });
    }
}
