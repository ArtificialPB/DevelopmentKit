package com.artificial.developmentkit;

import com.artificial.cachereader.GameType;
import com.artificial.cachereader.fs.CacheSystem;
import com.artificial.cachereader.wrappers.Dynamic;
import com.artificial.cachereader.wrappers.Script;
import com.artificial.cachereader.wrappers.Wrapper;
import com.artificial.cachereader.wrappers.WrapperLoader;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

class DevelopmentKit extends JFrame {
    private final CacheSystem cache;
    private final TypeLoader[] typeLoaders;
    private static final List<Wrapper<?>> CACHED_DEFINITIONS = new ArrayList<>();
    private static final List<String> CACHED_DISPLAY_NAMES = new ArrayList<>();
    private static final int LOAD_LIMIT = 175000;
    private final LoadedChildrenFrame loadedChildrenFrame = new LoadedChildrenFrame();
    private ComponentExplorer componentExplorer = null;
    private TypeLoader currentLoader = null;
    private Wrapper<?> selectedDefinition = null;

    DevelopmentKit(final CacheSystem cache, final TypeLoader[] typeLoaders) {
        this.cache = cache;
        this.typeLoaders = typeLoaders;
        initComponents();
        /*initial load*/
        currentLoader = typeLoaders[0];
        loadDefinitions(currentLoader);
        if (cache.getCacheSource().getSourceSystem().getGameType() == GameType.RT4) {
            final JMenuBar menuBar = new JMenuBar();
            final JMenu explorerItem = new JMenu("Component Explorer");
            explorerItem.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    if (componentExplorer == null) {
                        componentExplorer = new ComponentExplorer(cache);
                    }
                    componentExplorer.setVisible(true);
                    componentExplorer.requestFocus();
                }

                @Override
                public void menuDeselected(MenuEvent e) {

                }

                @Override
                public void menuCanceled(MenuEvent e) {

                }
            });
            explorerItem.setPopupMenuVisible(false);
            menuBar.add(explorerItem);
            this.setJMenuBar(menuBar);
        }
    }

    private void filteredObjectsListValueChanged() {
        final String selected = filteredObjectsList.getSelectedValue();
        if (selected != null) {
            final int id = Integer.parseInt(selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")")));
            loadAttributes(id);
        }
    }

    private void loadAttributes(final int id) {
        for (final Wrapper<?> def : CACHED_DEFINITIONS) {
            if (def.id() == id) {
                selectedDefinition = def;
                break;
            }
        }
        if (selectedDefinition == null) {
            return;
        }
        final List<Object[]> properties = new LinkedList<>();
        for (final Map.Entry<String, Object> entry : selectedDefinition.getDeclaredFields().entrySet()) {
            properties.add(new Object[]{entry.getKey(), entry.getValue()});
        }
        attributesTable.setModel(new DefaultTableModel(properties.toArray(new Object[properties.size()][2]), new Object[]{"Field", "Value"}));
        showChildrenButton.setEnabled(selectedDefinition instanceof Dynamic && ((Dynamic) selectedDefinition).dynamic());
    }

    private void loaderComboActionPerformed() {
        final TypeLoader selected = (TypeLoader) loaderCombo.getSelectedItem();
        /*to prevent loading the same definitions again*/
        if (selected != currentLoader) {
            loadDefinitions(selected);
            currentLoader = selected;
        }
    }

    private void loadDefinitions(final TypeLoader typeLoader) {
        CACHED_DEFINITIONS.clear();
        CACHED_DISPLAY_NAMES.clear();
        final WrapperLoader<?, ?> loader = cache.getLoader(typeLoader.getWrapperClass());
        final DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < LOAD_LIMIT; i++) {
            if (loader.canLoad(i)) {
                final Wrapper<?> def;
                CACHED_DEFINITIONS.add(def = loader.load(i));
                final StringBuilder builder = new StringBuilder();
                if (def.getDeclaredFields().containsKey("name")) {
                    builder.append(def.getDeclaredFields().get("name")).append(" (").append(i).append(")");
                } else {
                    builder.append(i);
                }
                listModel.addElement(builder.toString());
                CACHED_DISPLAY_NAMES.add(builder.toString());
            }
        }
        filteredObjectsList.setModel(listModel);
    }

    private void filterTextFieldKeyReleased() {
        filteredObjectsList.clearSelection();
        final DefaultListModel<String> listModel = new DefaultListModel<>();
        final List<String> arr = filterObjects(filterTextField.getText());
        arr.forEach(listModel::addElement);
        filteredObjectsList.setModel(listModel);
    }

    private List<String> filterObjects(String text) {
        final List<String> list = new ArrayList<>();
        text = text.toLowerCase();
        for (final String i : CACHED_DISPLAY_NAMES) {
            if (i.toLowerCase().contains(text)) {
                list.add(i);
            }
        }
        return list;
    }

    private void showChildrenButtonActionPerformed() {
        if (loadedChildrenFrame.isVisible()) {
            loadedChildrenFrame.requestFocus();
            return;
        }
        final Dynamic object = (Dynamic) selectedDefinition;
        if (object.dynamic()) {
            final WrapperLoader<?, ?> defLoader = cache.getLoader(currentLoader.getWrapperClass());
            final List<Object[]> children = new LinkedList<>();
            final int[] childrenIds = object.childrenIds();
            for (int i = 0; i < childrenIds.length; i++) {
                final int id = childrenIds[i];
                if (id == -1 || !defLoader.canLoad(id)) continue;
                final Wrapper<?> def = defLoader.load(id);
                children.add(new Object[]{i, id, def.getDeclaredFields().get("name"), def.getDeclaredFields().get("actions")});
            }
            final int configId = object.configId();
            final int scriptId = object.scriptId();
            String varpString = configToString(configId);
            if (scriptId != -1) {
                final WrapperLoader<?, ?> scriptLoader = cache.getLoader(currentLoader.getScriptClass());
                if (!scriptLoader.canLoad(scriptId)) {
                    loadedChildrenFrame.loadChildren("Cache does not contain script definition for " + scriptId, Collections.emptyList());
                    return;
                }
                final Script scriptDef = (Script) scriptLoader.load(scriptId);
                final int varp = scriptDef.configId();
                final int lowerBitIndex = scriptDef.lowerBitIndex();
                final int upperBitIndex = scriptDef.upperBitIndex();
                varpString = scriptToString(varp, lowerBitIndex, upperBitIndex);
            }
            loadedChildrenFrame.loadChildren(
                    varpString,
                    children
            );
            loadedChildrenFrame.setVisible(true);
        }
    }

    private static String scriptToString(final int varp, final int lowerBitIndex, final int upperBitIndex) {
        return "ctx.varpbits.varpbit(" + varp + ", " + lowerBitIndex + ", 0x" + Integer.toHexString(getMask(upperBitIndex, lowerBitIndex)) + ");";
    }

    private static String configToString(final int varp) {
        return "ctx.varpbits.varpbit(" + varp + ");";
    }

    private static int getMask(final int upperBitIndex, final int lowerBitIndex) {
        return (1 << (upperBitIndex - lowerBitIndex) + 1) - 1;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        filteredObjectsList = new JList<>();
        filterTextField = new JTextField();
        panel2 = new JPanel();
        showChildrenButton = new JButton();
        loaderCombo = new JComboBox<TypeLoader>(new DefaultComboBoxModel<TypeLoader>(typeLoaders));
        panel5 = new JPanel();
        scrollPane2 = new JScrollPane();
        attributesTable = new JTable();

        //======== this ========
        setTitle("Development Kit by Artificial");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setName("panel1");

            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

            //======== panel3 ========
            {
                panel3.setName("panel3");
                panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

                //======== panel4 ========
                {
                    panel4.setName("panel4");
                    panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setPreferredSize(new Dimension(250, 350));
                        scrollPane1.setName("scrollPane1");

                        //---- filteredObjectsList ----
                        filteredObjectsList.setName("filteredObjectsList");
                        filteredObjectsList.addListSelectionListener(e -> filteredObjectsListValueChanged());
                        scrollPane1.setViewportView(filteredObjectsList);
                    }
                    panel4.add(scrollPane1);
                }
                panel3.add(panel4);

                //---- filterTextField ----
                filterTextField.setName("filterTextField");
                filterTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        filterTextFieldKeyReleased();
                    }
                });
                panel3.add(filterTextField);

                //======== panel2 ========
                {
                    panel2.setName("panel2");
                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

                    //---- showChildrenButton ----
                    showChildrenButton.setText("Show Children");
                    showChildrenButton.setEnabled(false);
                    showChildrenButton.setName("showChildrenButton");
                    showChildrenButton.addActionListener(e -> showChildrenButtonActionPerformed());
                    panel2.add(showChildrenButton);

                    //---- loaderCombo ----
                    loaderCombo.setName("loaderCombo");
                    loaderCombo.addActionListener(e -> loaderComboActionPerformed());
                    panel2.add(loaderCombo);
                }
                panel3.add(panel2);
            }
            panel1.add(panel3);

            //======== panel5 ========
            {
                panel5.setName("panel5");
                panel5.setLayout(new BorderLayout());

                //======== scrollPane2 ========
                {
                    scrollPane2.setName("scrollPane2");

                    //---- attributesTable ----
                    attributesTable.setName("attributesTable");
                    scrollPane2.setViewportView(attributesTable);
                }
                panel5.add(scrollPane2, BorderLayout.CENTER);
            }
            panel1.add(panel5);
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JList<String> filteredObjectsList;
    private JTextField filterTextField;
    private JPanel panel2;
    private JButton showChildrenButton;
    private JComboBox<TypeLoader> loaderCombo;
    private JPanel panel5;
    private JScrollPane scrollPane2;
    private JTable attributesTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
