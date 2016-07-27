package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.CacheType;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.ProtocolWrapper;

public abstract class ProtocolWrapperLoader<T extends ProtocolWrapper> extends WrapperLoader<T> {

    protected final CacheType cache;

    public ProtocolWrapperLoader(final RT6CacheSystem cacheSystem, final CacheType cache) {
        super(cacheSystem);
        this.cache = cache;
    }

    protected FileData getValidFile(final int id) {
        FileData ret = getFile(id);
        if (ret == null) {
            throw new IllegalArgumentException("Bad id");
        }
        return ret;
    }

    protected FileData getFile(final int id) {
        Archive archive = cache.getArchive(id >>> 8);
        if (archive == null) {
            return null;
        }
        return archive.getFile(id & 0xff);
    }

    @Override
    public boolean canLoad(final int id) {
        return getFile(id) != null;
    }

}
