
package com.hyperplane.codingide;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConfirmCloseDialog extends JFrame implements ActionListener {
    String buttonPressed = "";
    public boolean closed = false;
    IDE ide = null;
    boolean open = false;
    
    ConfirmCloseDialog(IDE ide, boolean open){
        this.ide = ide;
        this.open = open;
        setBounds(50, 50,600, 400);
        setLayout(null);
        
        JLabel label = new JLabel("Would you like to save the file before closing?");
        label.setBounds(10, 10, 550, 240);
        label.setFont(new Font("Consolas", Font.PLAIN, 18));
        add(label);
        
        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(50, 250, 100, 50);
        yesButton.addActionListener(this);
        
        JButton noButton = new JButton("No");
        noButton.setBounds(200, 250, 100, 50);
        noButton.addActionListener(this);
        
        add(yesButton);
        add(noButton);
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        new ConfirmCloseDialog(null, false).setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        
        if (e.getActionCommand().equals("Yes")){
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setApproveButtonText("Save");
            int action = saveFileChooser.showOpenDialog(this.ide);
            if (action != JFileChooser.APPROVE_OPTION) return;

            File fileName = new File(saveFileChooser.getSelectedFile() + ".txt");
            BufferedWriter outputFile = null;
            try {
                outputFile = new BufferedWriter(new FileWriter(fileName));
                this.ide.textArea.write(outputFile);
                this.ide.savedText = "";
                this.ide.textArea.setText("");
            }
            catch(IOException ex) {}
            
            if(this.open){
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);

                for (int i=0; i<this.ide.supportedFileNameExtensionFilters.length; i++)
                    fileChooser.addChoosableFileFilter(this.ide.supportedFileNameExtensionFilters[i]);

                int action_ = fileChooser.showOpenDialog(this.ide);
                if (action_ != JFileChooser.APPROVE_OPTION) return;

                File fileName_ = fileChooser.getSelectedFile();
                try {
                    BufferedReader inputFile = new BufferedReader(new FileReader(fileName_));
                    this.ide.textArea.read(inputFile, null);
                    this.ide.savedText = this.ide.textArea.getText();
                }
                catch (Exception ex){}
            }
        }
    }
}
