package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.Script;

public class ScriptLoader extends WrapperLoader<Script> {

    private final Archive source;

    public ScriptLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem);
        this.source = cacheSystem.getCacheSource().getCacheType(2).getArchive(69);
    }

    @Override
    public Script load(final int id) {
        FileData data = source.getFile(id);
        if (data == null) {
            throw new IllegalArgumentException("Bad script id");
        }
        Script ret = new Script(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }

    @Override
    public boolean canLoad(final int id) {
        return source.getFile(id) != null;
    }

}
