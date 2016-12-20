package com.artificial.developmentkit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


class LoadedChildrenFrame extends JFrame {
    LoadedChildrenFrame() {
        initComponents();
    }

    void loadChildren(final String varpbitText, final java.util.List<Object[]> children) {
        varpbitTextField.setText(varpbitText);
        final Object[][] rows = new Object[children.size()][];
        int index = 0;
        for (final Object[] child : children) {
            rows[index++] = child;
        }
        childrenTable.setModel(new DefaultTableModel(rows, new Object[]{"State", "Id", "Name", "Actions"}));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        childrenTable = new JTable();
        varpbitTextField = new JTextField();

        //======== this ========
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- childrenTable ----
            childrenTable.setName("childrenTable");
            scrollPane1.setViewportView(childrenTable);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);

        //---- varpbitTextField ----
        varpbitTextField.setName("varpbitTextField");
        contentPane.add(varpbitTextField, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTable childrenTable;
    private JTextField varpbitTextField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
