package com.hyperplane.codingide;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class About extends JFrame implements ActionListener {
    JButton okButton;
    About(){
        setBounds(650, 300, 400, 300);
        setLayout(null);
        
//        ImageIcon editorIcon = new ImageIcon((Image) ClassLoader.getSystemResources(""));
//        Image editorImage = editorIcon.getImage().getScaledInstance(400, 80, Image.SCALE_DEFAULT);
//        ImageIcon editorIcon2 = new ImageIcon(editorImage);
//        
//        JLabel imageLabel = new JLabel(editorIcon2);
//        imageLabel.setBounds(150, 40, 200, 30);
//        add(imageLabel);
        
        JLabel label = new JLabel("Coding IDE");
        label.setBounds(50, 50, 150, 30);
        label.setFont(new Font("Consolas", Font.PLAIN, 18));
        add(label);
        
        okButton = new JButton("OK");
        okButton.setBounds(50, 100, 200, 30);
        okButton.addActionListener(this);
        add(okButton);
    }
    
    public static void main(String[] args){
        new About().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.setVisible(false);
    }
}
