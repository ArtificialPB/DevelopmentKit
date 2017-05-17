package com.artificial.developmentkit;

import com.artificial.cachereader.GameType;
import com.artificial.cachereader.fs.CacheSystem;
import com.artificial.cachereader.wrappers.Wrapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
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
            if (node == null || !(node.getUserObject() instanceof Wrapper<?>)) return;
            final Wrapper<?> c = (Wrapper<?>) node.getUserObject();
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
        final List<? extends Wrapper> widgets = getWidgets();
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root", true);
        final TreeModel model = new DefaultTreeModel(root);
        for (final Wrapper w : widgets) {
            final DefaultMutableTreeNode widgetRoot = new DefaultMutableTreeNode(w.id());
            for (final Object c : getComponents(w)) {
                widgetRoot.add(new DefaultMutableTreeNode(c));
            }
            root.add(widgetRoot);
        }
        tree.setModel(model);
    }

    private List<? extends Wrapper> getWidgets() {
        if (cacheSystem.getCacheSource().getSourceSystem().getGameType() == GameType.RT6) {
            return ((com.artificial.cachereader.wrappers.rt6.loaders.WidgetLoader) cacheSystem.getLoader(com.artificial.cachereader.wrappers.rt6.Widget.class)).loadAll();
        }
        return ((com.artificial.cachereader.wrappers.rt4.loaders.WidgetLoader) cacheSystem.getLoader(com.artificial.cachereader.wrappers.rt4.Widget.class)).loadAll();
    }

    private Object[] getComponents(final Wrapper widget) {
        if (cacheSystem.getCacheSource().getSourceSystem().getGameType() == GameType.RT6) {
            return ((com.artificial.cachereader.wrappers.rt6.Widget) widget).components;
        }
        return ((com.artificial.cachereader.wrappers.rt4.Widget) widget).components;
    }

}
