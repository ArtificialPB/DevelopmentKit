package com.artificial.cachereader.wrappers.rt6.loaders;

import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.CacheType;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.meta.ArchiveMeta;
import com.artificial.cachereader.meta.FileMeta;
import com.artificial.cachereader.wrappers.rt6.Component;
import com.artificial.cachereader.wrappers.rt6.Widget;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WidgetLoader extends WrapperLoader<Widget> {
    private final CacheType cache;

    public WidgetLoader(RT6CacheSystem cacheSystem) {
        super(cacheSystem);
        cache = cacheSystem.getCacheSource().getCacheType(3);
    }

    @Override
    public Widget load(final int widgetId) {
        final Archive archive = getValidArchive(widgetId);
        final ArchiveMeta meta = cache.getTable().getEntries().get(archive.getId());
        final Widget ret = new Widget(this, widgetId);
        ret.components = new Component[meta.getChildren().size()];
        int index = 0;
        for (final Map.Entry<Integer, FileMeta> b : meta.getChildren().entrySet()) {
            final int fileId = b.getValue().getId();
            final FileData data = archive.getFile(fileId);
            final int componentId = (widgetId << 16) + fileId;
            final Component comp = new Component(this, componentId);
            comp.decode(data.getDataAsStream());
            ret.components[comp.index = index++] = comp;
        }
        return ret;
    }

    @Override
    public boolean canLoad(int id) {
        return getWidgetArchive(id) != null;
    }

    private Archive getValidArchive(final int widgetId) {
        Archive ret = getWidgetArchive(widgetId);
        if (ret == null)
            throw new IllegalArgumentException("Bad id");
        return ret;
    }

    private Archive getWidgetArchive(final int widgetId) {
        return cache.getArchive(widgetId);
    }

    public List<Widget> loadAll() {
        final List<Widget> ret = new LinkedList<>();
        for (final Map.Entry<Integer, ArchiveMeta> entry : cache.getTable().getEntries().entrySet()) {
            ret.add(load(entry.getKey()));
        }
        return ret;
    }
}
