package com.artificial.cachereader.fs;

import com.artificial.cachereader.DataSource;
import com.artificial.cachereader.GameType;
import com.artificial.cachereader.meta.*;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;


public class CacheType {

    private Map<Integer, Archive> archiveCache = new WeakHashMap<Integer, Archive>();

    private final ReferenceTable table;
    private final IndexFile index;
    private final DataSource source;
    private final int id;

    public CacheType(CacheSource source, int id) {
        this.source = source.getSourceSystem();
        this.id = id;
        this.table = source.getSourceSystem().getGameType() == GameType.RT6 ? new RS3ReferenceTable(source, id) : new OSRSReferenceTable(source, id);
        this.index = new IndexFile(this.source, id);
    }

    public Archive getArchive(int archive) {
        Archive ret = archiveCache.get(archive);
        if (ret == null) {
            byte[] data = getArchiveData(archive);
            if (data == null)
                return null;
            ret = new Archive(this, archive, data);
        }
        archiveCache.put(archive, ret);
        return ret;
    }

    private byte[] getArchiveData(int archive) {
        ArchiveRequest query = index.getArchiveMeta(archive);
        ArchiveMeta meta = table.getEntry(archive);
        if (query == null || meta == null)
            return null;
        else
            return source.readArchive(query);
    }

    public ReferenceTable getTable() {
        return table;
    }

    public IndexFile getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public boolean init() {
        try {
            this.getTable().init();
            this.getIndex().init();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
