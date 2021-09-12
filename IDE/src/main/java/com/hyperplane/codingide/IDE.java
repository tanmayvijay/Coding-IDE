package com.hyperplane.codingide;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IDE extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    String clipboardText="";
    String savedText = "";
    
    FileNameExtensionFilter[] supportedFileNameExtensionFilters = {
        new FileNameExtensionFilter("Plain text", "txt"),
        new FileNameExtensionFilter("Python", "py"),
        new FileNameExtensionFilter("Java", "java"),
        new FileNameExtensionFilter("C", "c"),
        new FileNameExtensionFilter("C++", "cpp"),
        new FileNameExtensionFilter("Go Lang", "go"),
        new FileNameExtensionFilter("Java Script", "js"),
        new FileNameExtensionFilter("HTML", "html"),
        new FileNameExtensionFilter("CSS", "css")
    };
    
    IDE(){
        
        setBounds(10,10,1366,720);

        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem newFileMenuItem = new JMenuItem("New File");
        newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newFileMenuItem.addActionListener(this);
        
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMenuItem.addActionListener(this);
        
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(this);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(this);
        
        fileMenu.add(newFileMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        
        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutMenuItem.addActionListener(this);
        
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyMenuItem.addActionListener(this);
        
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteMenuItem.addActionListener(this);
        
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAllMenuItem.addActionListener(this);
        
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(selectAllMenuItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(this);
        
        helpMenu.add(aboutMenuItem);

        // Adding menus to menuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        
        // Text area
        this.textArea = new JTextArea();
        this.textArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        
        this.scrollPane = new JScrollPane(this.textArea);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(this.scrollPane, BorderLayout.CENTER);
        
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("New File")){
            if (this.textArea.getText().equals(this.savedText)){
                this.textArea.setText("");
                this.savedText = "";
            }
            else{
                new ConfirmCloseDialog(this, false);
            }
        }
        else if (e.getActionCommand().equals("Open")){
            if (!this.textArea.getText().equals(this.savedText)){
                new ConfirmCloseDialog(this, true);
            }
            else{
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);

                for (int i=0; i<this.supportedFileNameExtensionFilters.length; i++)
                    fileChooser.addChoosableFileFilter(this.supportedFileNameExtensionFilters[i]);

                int action = fileChooser.showOpenDialog(this);
                if (action != JFileChooser.APPROVE_OPTION) return;

                File fileName = fileChooser.getSelectedFile();
                try {
                    BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
                    this.textArea.read(inputFile, null);
                    this.savedText = textArea.getText();
                }
                catch (Exception ex){}
            }
        }
        else if (e.getActionCommand().equals("Save")){
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setApproveButtonText("Save");
            int action = saveFileChooser.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) return;
            
            File fileName = new File(saveFileChooser.getSelectedFile() + ".txt");
            BufferedWriter outputFile = null;
            try {
                outputFile = new BufferedWriter(new FileWriter(fileName));
                this.textArea.write(outputFile);
                this.savedText = textArea.getText();
            }
            catch(IOException ex) {}
        }
        else if (e.getActionCommand().equals("Exit")){
            System.exit(0);
        }
        else if (e.getActionCommand().equals("Cut")){
            this.clipboardText = this.textArea.getSelectedText();
            this.textArea.replaceSelection("");
        }
        else if (e.getActionCommand().equals("Copy")){
            this.clipboardText = this.textArea.getSelectedText();
        }
        else if (e.getActionCommand().equals("Paste")){
            this.textArea.replaceSelection("");
            this.textArea.insert(this.clipboardText, this.textArea.getCaretPosition());
        }
        else if (e.getActionCommand().equals("Select All")){
            this.textArea.selectAll();
        }
        else if (e.getActionCommand().equals("About")){
            new About().setVisible(true);
        }
    }

    public static void main(String[] args){
        new IDE().setVisible(true);
    }
}
