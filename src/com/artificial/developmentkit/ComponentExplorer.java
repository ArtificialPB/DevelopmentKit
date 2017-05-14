package com.artificial.developmentkit;

import com.artificial.cachereader.fs.CacheSystem;
import com.artificial.cachereader.wrappers.rt4.Component;
import com.artificial.cachereader.wrappers.rt4.loaders.ComponentLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ComponentExplorer extends JFrame {
    private final CacheSystem cacheSystem;

    ComponentExplorer(final CacheSystem cacheSystem) {
        this.cacheSystem = cacheSystem;
        initialize();
    }

    private void initialize() {
        this.setTitle("Component Explorer");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        final JTable componentInfoTable = new JTable(new Object[1][2], new Object[]{"Field", "Value"});
        final JTree tree = new JTree();
        populateTree(tree);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null || !(node.getUserObject() instanceof Component)) return;
            final Component c = (Component) node.getUserObject();
            final List<Object[]> properties = new LinkedList<>();
            for (final Map.Entry<String, Object> entry : c.getDeclaredFields().entrySet()) {
                properties.add(new Object[]{entry.getKey(), entry.getValue()});
            }
            componentInfoTable.setModel(new DefaultTableModel(properties.toArray(new Object[properties.size()][2]), new Object[]{"Field", "Value"}));
        });

        final JScrollPane treePane = new JScrollPane(tree);
        treePane.setPreferredSize(new Dimension(150, 450));
        final JScrollPane tablePane = new JScrollPane(componentInfoTable);
        this.add(treePane);
        this.add(tablePane);
        this.pack();
        this.setVisible(true);
    }

    private void populateTree(final JTree tree) {
        final List<Component> components = ((ComponentLoader) cacheSystem.getLoader(Component.class)).loadAll();
        final DefaultMutableTreeNode widgets = new DefaultMutableTreeNode("Root", true);
        final TreeModel model = new DefaultTreeModel(widgets);
        final Map<Integer, List<Component>> widgetMap = new LinkedHashMap<>();
        for (final Component c : components) {
            final int widget = (c.id >>> 16);
            if (!widgetMap.containsKey(widget)) {
                widgetMap.put(widget, new LinkedList<>());
            }
            widgetMap.get(widget).add(c);
        }
        for (final Map.Entry<Integer, List<Component>> entry : widgetMap.entrySet()) {
            final int widget = entry.getKey();
            final List<Component> children = entry.getValue();
            final DefaultMutableTreeNode widgetRoot = new DefaultMutableTreeNode(widget);
            for (int i = 0; i < children.size(); i++) {
                final Component c = children.get(i);
                c.index = i;
                widgetRoot.add(new DefaultMutableTreeNode(c));
            }
            widgets.add(widgetRoot);
        }
        tree.setModel(model);
    }

}
