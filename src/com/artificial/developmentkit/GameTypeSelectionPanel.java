package com.artificial.developmentkit;

import com.artificial.cachereader.fs.CacheSystem;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.fs.RT6CacheSystem;

import javax.swing.*;
import java.awt.*;

public class GameTypeSelectionPanel extends JDialog {
    private CacheSystem cache;
    private TypeLoader[] typeLoaders;

    public GameTypeSelectionPanel() {
        initComponents();
    }

    public CacheSystem getCache() {
        return cache;
    }

    public TypeLoader[] getTypeLoaders() {
        return typeLoaders;
    }

    private void rs3ButtonActionPerformed() {
        this.cache = new RT6CacheSystem();
        this.typeLoaders = TypeLoader.RT6.values();
        dispose();
    }

    private void osrsButtonActionPerformed() {
        this.cache = new RT4CacheSystem();
        this.typeLoaders = TypeLoader.RT4.values();
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        rs3Button = new JButton();
        osrsButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Game Type Selection");
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setName("panel1");

            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("<html><center>Welcome to Artificial's Development Kit!</center><br><center><b>Select for which game type would you like to load the kit for:</center></b></html>");
            label1.setName("label1");
            panel1.add(label1);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setName("panel2");
            panel2.setLayout(new FlowLayout());

            //---- rs3Button ----
            rs3Button.setText("RS3");
            rs3Button.setName("rs3Button");
            rs3Button.addActionListener(e -> rs3ButtonActionPerformed());
            panel2.add(rs3Button);

            //---- osrsButton ----
            osrsButton.setText("OSRS");
            osrsButton.setName("osrsButton");
            osrsButton.addActionListener(e -> osrsButtonActionPerformed());
            panel2.add(osrsButton);
        }
        contentPane.add(panel2);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JButton rs3Button;
    private JButton osrsButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
