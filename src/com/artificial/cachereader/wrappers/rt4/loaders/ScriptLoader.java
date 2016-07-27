package com.artificial.cachereader.wrappers.rt4.loaders;


import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.Script;

public class ScriptLoader extends ProtocolWrapperLoader<Script> {
    public ScriptLoader(final RT4CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(2).getArchive(14));
    }

    @Override
    public Script load(final int id) {
        final FileData data = getValidFile(id);
        final Script definition = new Script(this, id);
        definition.decode(data.getDataAsStream());
        return definition;
    }

}
