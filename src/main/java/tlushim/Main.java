/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlushim;

import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
/**
 *
 * @author gchaim
 */
public class Main {

    // instance variables - replace the example below with your own
    private static String user = "326988425";
    private static String pass = "Sdrm1415";
    private static String site = "https://www.tlushim.co.il/main.php?op=start";

    /**
     * Constructor for objects of class main
     */
    public static void main(String[] args)
    {
        HtmlParser parse = new HtmlParser(user,pass,site);
        //String data = parse.getData();
        //System.out.println(data);
        JFrame f = new JFrame("A JFrame");
        f.setSize(250, 250);
        f.setLocation(300,200);
        final JTextArea textArea = new JTextArea(10, 40);
        f.getContentPane().add(BorderLayout.CENTER, textArea);
        final JButton button = new JButton("Click Me");
        f.getContentPane().add(BorderLayout.SOUTH, button);
        button.addActionListener((ActionEvent e) -> { textArea.append("Button was clicked\n"); }); 

        f.setVisible(true);       
    }
    
}
