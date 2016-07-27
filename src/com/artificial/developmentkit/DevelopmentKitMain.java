package com.artificial.developmentkit;

import com.artificial.cachereader.fs.CacheSystem;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class DevelopmentKitMain {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        final AtomicReference<CacheSystem> cache = new AtomicReference<>(null);
        final AtomicReference<TypeLoader[]> typeLoaders = new AtomicReference<>(null);

        SwingUtilities.invokeLater(() -> {
            final GameTypeSelectionPanel selectionPanel = new GameTypeSelectionPanel();
            selectionPanel.setVisible(true);
            selectionPanel.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cache.set(selectionPanel.getCache());
                    typeLoaders.set(selectionPanel.getTypeLoaders());
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            });
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ignored) {
            }
        }

        if (cache.get() == null || typeLoaders.get() == null) {
            JOptionPane.showMessageDialog(null, "Failed to load Development kit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> new DevelopmentKit(cache.get(), typeLoaders.get()).setVisible(true));
    }
}