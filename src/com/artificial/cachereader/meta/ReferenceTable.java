package com.artificial.cachereader.meta;

import com.artificial.cachereader.datastream.ByteStream;
import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.fs.CacheSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ReferenceTable {
    protected static final int MINIMUM_FORMAT_FOR_VERSION = 6;
    protected static final int FLAG_IDENTIFIERS = 0x1, FLAG_WHIRLPOOL = 0x2, FLAG_UNKNOWN_1 = 0x4, FLAG_UNKNOWN_2 = 0x8;
    private final CacheSource cache;
    private final int id;

    protected int flags;
    protected int version;
    protected int format;

    protected Map<Integer, ArchiveMeta> entries;

    protected Stream data;

    public ReferenceTable(CacheSource cache, int id) {
        this.cache = cache;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public int getFlags() {
        return flags;
    }

    public int getFormat() {
        return format;
    }

    public ArchiveMeta getEntry(int id) {
        return entries.get(id);
    }

    public Map<Integer, ArchiveMeta> getEntries() {
        return entries;
    }

    public void init() throws IOException {
        if (entries == null) {
            synchronized (this) {
                if (entries == null) {
                    entries = new HashMap<Integer, ArchiveMeta>();
                    ByteStream compressed = new ByteStream(getTableData());
                    this.data = new ByteStream(compressed.decompress());
                    this.entries = new HashMap<Integer, ArchiveMeta>();
                    decode(data);
                    this.data = null;
                }
            }
        }
    }

    private ArchiveRequest getQuery() {
        return cache.getMetaIndex().getArchiveMeta(id);
    }

    private byte[] getTableData() {
        ArchiveRequest query = getQuery();
        return cache.getSourceSystem().readArchive(query);
    }

    protected abstract void decode(Stream data);


}
