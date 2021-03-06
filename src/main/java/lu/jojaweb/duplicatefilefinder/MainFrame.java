/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lu.jojaweb.duplicatefilefinder;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Joshua
 */
public class MainFrame extends javax.swing.JFrame {

    DuplicateFileFinder dff = new DuplicateFileFinder();
    Thread currentScan;
    private boolean duplicateFilePathListValueChangedPaused = false;
    private boolean originalFilePathListValueChangedPaused = false;
    boolean darkmode = false;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        File configFile = new File("settings.conf");
        try ( BufferedReader in = new BufferedReader(new FileReader(configFile))) {
            String line;
            System.out.println("File:" + configFile.getAbsolutePath());
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Theme")) {
                    if (line.contains("Dark")) {
                        setToDarkMode();
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No setting file found.");
        } catch (IOException ex) {
            System.out.println("An io exception occured while trying to read settings.conf");
        }

        initComponents();
        if (darkmode) {
            jCheckBoxMenuItemDarkMode.setSelected(true);
        }
        originalFilePathList.setDoubleBuffered(true);
        duplicateFilePathList.setDoubleBuffered(true);
        dff.initOutputField(jTextArea1);
        dff.initScannedFilesCountLabel(scannedFilesCountLabel);
        dff.initDuplicateFilesCountLabel(duplicateFilesCountLabel);
        dff.initOriginalFilePathList(originalFilePathList);
        dff.initDuplicateFilePathList(duplicateFilePathList);
        DefaultCaret caret = (DefaultCaret) jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        currentScan = new Thread(new Runnable() {
            @Override
            public void run() {
                dff.scanAllFilesOnDrive("D");
                //String[] paths = {"D:\\Pictures","C:\\Users\\Joshua\\OneDrive\\Bilder","D:\\Documents\\Note8-BackUp-Caroline","D:\\Documents\\S9_Backup","D:\\Documents\\Xperia Z5 Compact"};
                //String[] paths = {"D:\\Pictures\\Xperia Z5 Compact\\WhatsApp\\Media\\WhatsApp Images", "D:\\Pictures\\S9_Backup\\WhatsApp Images"};
                //dff.scanAllFilesInPaths(paths);
            }
        });
        currentScan.start();

        hideDetailsTextArea();

        //sync scrolls of both jlists
        originalFilePathListScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (duplicateFilePathListScrollPane.getVerticalScrollBar().getValue() != originalFilePathListScrollPane.getVerticalScrollBar().getValue()) {
                    duplicateFilePathListScrollPane.getVerticalScrollBar().setValue(originalFilePathListScrollPane.getVerticalScrollBar().getValue());
                }
            }
        });
        duplicateFilePathListScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (duplicateFilePathListScrollPane.getVerticalScrollBar().getValue() != originalFilePathListScrollPane.getVerticalScrollBar().getValue()) {
                    originalFilePathListScrollPane.getVerticalScrollBar().setValue(duplicateFilePathListScrollPane.getVerticalScrollBar().getValue());
                }
            }
        });

        new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentScan.isAlive()) {
                    originalFilePathList.repaint();
                    duplicateFilePathList.repaint();
                }
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        scannedFilesCountLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        duplicateFilesCountLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        originalFilePathListScrollPane = new javax.swing.JScrollPane();
        originalFilePathList = new javax.swing.JList<>();
        duplicateFilePathListScrollPane = new javax.swing.JScrollPane();
        duplicateFilePathList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItemDarkMode = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DFF");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setFocusable(false);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Scanned files:");

        scannedFilesCountLabel.setText("None");

        jLabel2.setText("Duplicate files:");

        duplicateFilesCountLabel.setText("None");

        jButton1.setText("Toggle show details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(0);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.1);

        jSplitPane1.setResizeWeight(0.5);

        originalFilePathList.setAutoscrolls(false);
        originalFilePathList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                originalFilePathListMouseClicked(evt);
            }
        });
        originalFilePathList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                originalFilePathListKeyPressed(evt);
            }
        });
        originalFilePathList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                originalFilePathListValueChanged(evt);
            }
        });
        originalFilePathListScrollPane.setViewportView(originalFilePathList);

        jSplitPane1.setLeftComponent(originalFilePathListScrollPane);

        duplicateFilePathList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                duplicateFilePathListMouseClicked(evt);
            }
        });
        duplicateFilePathList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duplicateFilePathListKeyPressed(evt);
            }
        });
        duplicateFilePathList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                duplicateFilePathListValueChanged(evt);
            }
        });
        duplicateFilePathListScrollPane.setViewportView(duplicateFilePathList);

        jSplitPane1.setRightComponent(duplicateFilePathListScrollPane);

        jSplitPane2.setBottomComponent(jSplitPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1008, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel1);

        jMenu1.setText("File");
        jMenu1.setEnabled(false);
        jMenuBar1.add(jMenu1);

        jMenu2.setText("View");

        jCheckBoxMenuItemDarkMode.setText("Dark Mode");
        jCheckBoxMenuItemDarkMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemDarkModeActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItemDarkMode);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scannedFilesCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(duplicateFilesCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addComponent(jSplitPane2)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(scannedFilesCountLabel)
                    .addComponent(jLabel2)
                    .addComponent(duplicateFilesCountLabel)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dff.toggleShowDetails();
        if (!jScrollPane1.isVisible()) {
            jScrollPane1.setVisible(true);
            SwingUtilities.updateComponentTreeUI(this);
        } else {
            hideDetailsTextArea();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void originalFilePathListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_originalFilePathListValueChanged
        if (!originalFilePathListValueChangedPaused) {
            duplicateFilePathListValueChangedPaused = true;
            duplicateFilePathList.setSelectedIndices(originalFilePathList.getSelectedIndices());
            duplicateFilePathListValueChangedPaused = false;
        }
    }//GEN-LAST:event_originalFilePathListValueChanged

    private void duplicateFilePathListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_duplicateFilePathListValueChanged
        if (!duplicateFilePathListValueChangedPaused) {
            originalFilePathListValueChangedPaused = true;
            originalFilePathList.setSelectedIndices(duplicateFilePathList.getSelectedIndices());
            originalFilePathListValueChangedPaused = false;
        }
    }//GEN-LAST:event_duplicateFilePathListValueChanged

    private void duplicateFilePathListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_duplicateFilePathListMouseClicked
        if (evt.getClickCount() > 1) {
            String doubleClickedPath = duplicateFilePathList.getSelectedValue().toString();
            //Desktop.getDesktop().open(new File(doubleClickedPath.substring(0,doubleClickedPath.lastIndexOf("\\")+1)));
            openFileOrFolder(doubleClickedPath);
        }
    }//GEN-LAST:event_duplicateFilePathListMouseClicked

    private void originalFilePathListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_originalFilePathListMouseClicked

        if (evt.getClickCount() > 1) {
            String doubleClickedPath = originalFilePathList.getSelectedValue().toString();
            //Desktop.getDesktop().open(new File(doubleClickedPath.substring(0,doubleClickedPath.lastIndexOf("\\")+1)));
            openFileOrFolder(doubleClickedPath);
        }
    }//GEN-LAST:event_originalFilePathListMouseClicked

    private void duplicateFilePathListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duplicateFilePathListKeyPressed
        if (duplicateFilePathList.getSelectedIndex() != -1) {
            Object[] selectedPaths = duplicateFilePathList.getSelectedValuesList().toArray();
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                //Desktop.getDesktop().open(new File(doubleClickedPath.substring(0,doubleClickedPath.lastIndexOf("\\")+1)));
                openFileOrFolder(selectedPaths[0].toString());
            }
            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                int[] indices = duplicateFilePathList.getSelectedIndices();
                int deletionConfirmation = JOptionPane.CANCEL_OPTION;
                if (indices.length > 1) {
                    deletionConfirmation = JOptionPane.showConfirmDialog(null, "Delete " + indices.length + " files?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                } else if (indices.length == 1) {
                    deletionConfirmation = JOptionPane.showConfirmDialog(null, "Delete \"" + selectedPaths[0] + "\"?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                }
                if (deletionConfirmation == JOptionPane.OK_OPTION) {
                    for (Object o : duplicateFilePathList.getSelectedValuesList().toArray()) {
                        try {
                            if (!Files.deleteIfExists(Paths.get(o.toString()))) {
                                JOptionPane.showMessageDialog(null, "An error occured while trying to delete the file/folder '" + o.toString() + "'\n\tReason: File/Folder does no longer exist", "Error", JOptionPane.ERROR_MESSAGE);
                                System.out.println("An error occured while trying to delete the file/folder '" + o.toString() + "'\n\tReason: File/Folder does no longer exist");
                            } else {
                                System.out.println(o.toString() + " was successfully deleted.");
                                dff.setFileDeleted(o.toString());
                            }
                        } catch (IOException ex) {
                            System.out.println("An IOException occured while trying to delete '" + o.toString() + "' -> " + ex.getMessage());
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_duplicateFilePathListKeyPressed

    private void originalFilePathListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_originalFilePathListKeyPressed
        if (originalFilePathList.getSelectedIndex() != -1) {
            Object[] selectedPaths = originalFilePathList.getSelectedValuesList().toArray();
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                //Desktop.getDesktop().open(new File(doubleClickedPath.substring(0,doubleClickedPath.lastIndexOf("\\")+1)));
                openFileOrFolder(selectedPaths[0].toString());
            }
            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                int[] indices = originalFilePathList.getSelectedIndices();
                int deletionConfirmation = JOptionPane.CANCEL_OPTION;
                if (indices.length > 1) {
                    deletionConfirmation = JOptionPane.showConfirmDialog(null, "Delete " + indices.length + " files?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                } else if (indices.length == 1) {
                    deletionConfirmation = JOptionPane.showConfirmDialog(null, "Delete \"" + selectedPaths[0] + "\"?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                }
                if (deletionConfirmation == JOptionPane.OK_OPTION) {
                    for (Object o : originalFilePathList.getSelectedValuesList().toArray()) {
                        try {
                            if (!Files.deleteIfExists(Paths.get(o.toString()))) {
                                JOptionPane.showMessageDialog(null, "An error occured while trying to delete the file/folder '" + o.toString() + "'\n\tReason: File/Folder does no longer exist", "Error", JOptionPane.ERROR_MESSAGE);
                                System.out.println("An error occured while trying to delete the file/folder '" + o.toString() + "'\n\tReason: File/Folder does no longer exist");
                            } else {
                                System.out.println(o.toString() + " was successfully deleted.");
                                dff.setFileDeleted(o.toString());
                            }
                        } catch (IOException ex) {
                            System.out.println("An IOException occured while trying to delete '" + o.toString() + "' -> " + ex.getMessage());
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_originalFilePathListKeyPressed

    private void jCheckBoxMenuItemDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemDarkModeActionPerformed
        JCheckBoxMenuItem darkModeJCheckbox = (JCheckBoxMenuItem) evt.getSource();
        if (darkModeJCheckbox.isSelected()) {
            try ( FileWriter out = new FileWriter(new File("settings.conf"))) {
                out.append("Theme: Dark");
                if (!darkmode) {
                    JOptionPane.showMessageDialog(null, "Darkmode will be activated once the program is restarted.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("No setting file found.");
            } catch (IOException ex) {
                System.out.println("An io exception occured while trying to read settings.conf");
            }
        } else {
            try ( FileWriter out = new FileWriter(new File("settings.conf"))) {
                out.append("Theme: Light");
                if (darkmode) {
                    JOptionPane.showMessageDialog(null, "Darkmode will be deactivated once the program is restarted.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("No setting file found.");
            } catch (IOException ex) {
                System.out.println("An io exception occured while trying to read settings.conf");
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
    }//GEN-LAST:event_jCheckBoxMenuItemDarkModeActionPerformed

    private void openFileOrFolder(String path) {

        if ((new File(path)).exists()) {
            try {
                System.out.println("Opening " + path);
                Desktop.getDesktop().open(new File(path));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occured while trying to open the file/folder '" + path + "'\n\tReason: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("An error occured while trying to open the file/folder '" + path + "'\n\tReason: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "An error occured while trying to open the file/folder '" + path + "'\n\tReason: File/Folder does no longer exist", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("An error occured while trying to open the file/folder '" + path + "'\n\tReason: File/Folder does no longer exist");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<Object> duplicateFilePathList;
    private javax.swing.JScrollPane duplicateFilePathListScrollPane;
    private javax.swing.JLabel duplicateFilesCountLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemDarkMode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList<Object> originalFilePathList;
    private javax.swing.JScrollPane originalFilePathListScrollPane;
    private javax.swing.JLabel scannedFilesCountLabel;
    // End of variables declaration//GEN-END:variables

    private void setToDarkMode() {
        //https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html#primary
        UIManager.put("nimbusBase", new Color(0, 0, 0)); //SCROLLBAR CARRET color

        UIManager.put("nimbusBlueGrey", new Color(30, 33, 35)); //shine effect over components
        UIManager.put("nimbusSelectionBackground", new Color(22, 58, 64)); // background color of selected text
        UIManager.put("nimbusSelectedText", new Color(220, 220, 220)); // foreground color of selected text

        UIManager.put("MenuBar:Menu[Enabled].textForeground", new Color(200, 200, 200)); //Menu text color
        UIManager.put("MenuBar:Menu[Disabled].textForeground", new Color(120, 120, 120)); //Menu text color

        UIManager.put("TextArea.background", new Color(70, 73, 75)); //JTextArea background color

        UIManager.put("List.background", new Color(70, 73, 75));//JList background color

        //UIManager.put("nimbusInfoBlue", new Color(255, 0, 0)); //?
        UIManager.put("nimbusFocus", new Color(200, 200, 200));// shadow around button and other selected components
        UIManager.put("control", new Color(60, 63, 65)); // window background [60,63,65] 
        UIManager.put("text", new Color(200, 200, 200));//[187,187,187]
        darkmode = true;
    }

    private void hideDetailsTextArea() {
        jScrollPane1.setVisible(false);
        SwingUtilities.updateComponentTreeUI(this);
    }

}
