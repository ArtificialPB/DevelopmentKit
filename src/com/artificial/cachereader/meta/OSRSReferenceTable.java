package com.artificial.cachereader.meta;

import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.fs.CacheSource;

public class OSRSReferenceTable extends ReferenceTable {

    public OSRSReferenceTable(final CacheSource cache, final int id) {
        super(cache, id);
    }

    @Override
    protected void decode(Stream data) {
        this.format = data.getUByte();
        if (format >= MINIMUM_FORMAT_FOR_VERSION) {
            this.version = data.getInt();
        }
        this.flags = data.getUByte();
        final int count = data.getUShort();
        final int[] ids = getIds(count);

        for (int id : ids) {
            entries.put(id, new ArchiveMeta(id));
        }

        if ((flags & FLAG_IDENTIFIERS) == FLAG_IDENTIFIERS) {
            for (int id : ids) {
                entries.get(id).setIdentifier(data.getInt());
            }
        }

        for (int id : ids) {
            entries.get(id).setCrc(data.getInt());
        }

        if ((flags & FLAG_UNKNOWN_2) == FLAG_UNKNOWN_2) {
            for (int id : ids) {
                int value = data.getInt();
            }
        }

        if ((flags & FLAG_WHIRLPOOL) == FLAG_WHIRLPOOL) {
            for (int id : ids) {
                final byte[] rawWhirlpool = entries.get(id).getAndInitializeWhirlpool();
                data.getBytes(rawWhirlpool);
            }
        }
        if ((flags & FLAG_UNKNOWN_1) == FLAG_UNKNOWN_1) {
            for (int id : ids) {
                int value1 = data.getInt();
                int value2 = data.getInt();
            }
        }

        for (int id : ids) {
            entries.get(id).setVersion(data.getInt());
        }

        for (int id : ids) {
            int childCount = data.getUShort();
            entries.get(id).setChildCount(childCount);
        }

        final int[][] children = new int[ids.length][];
        for (int i = 0; i < ids.length; ++i) {
            ArchiveMeta currentEntry = getEntry(ids[i]);
            int childCount = currentEntry.getChildCount();
            children[i] = getIds(childCount);
            for (int id : children[i]) {
                currentEntry.addChild(id);
            }
        }

        if ((flags & FLAG_IDENTIFIERS) == FLAG_IDENTIFIERS) {
            for (int i = 0; i < ids.length; ++i) {
                for (int childId : children[i]) {
                    entries.get(ids[i]).getChild(childId).setIdentifier(data.getInt());
                }
            }
        }

    }


    private int[] getIds(int size) {
        int[] ids = new int[size];
        int accumulator = 0;
        for (int i = 0; i < ids.length; ++i) {
            int delta = data.getUShort();
            accumulator += delta;
            ids[i] = accumulator;
        }
        return ids;
    }
}
