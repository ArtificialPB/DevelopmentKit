package com.artificial.cachereader.wrappers.rt4.loaders;


import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.ProtocolWrapper;

public abstract class ProtocolWrapperLoader<T extends ProtocolWrapper> extends WrapperLoader<T> {

    protected final Archive archive;

    public ProtocolWrapperLoader(final RT4CacheSystem cacheSystem, final Archive archive) {
        super(cacheSystem);
        this.archive = archive;
    }


    protected FileData getValidFile(final int id) {
        FileData ret = getFile(id);
        if (ret == null)
            throw new IllegalArgumentException("Bad id");
        return ret;
    }

    protected FileData getFile(final int id) {
        final FileData fileData = archive.getFile(id);
        if (fileData == null)
            return null;
        return fileData;
    }

    @Override
    public boolean canLoad(final int id) {
        return getFile(id) != null;
    }

}
