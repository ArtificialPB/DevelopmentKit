package com.artificial.cachereader.wrappers.rt4.loaders;


import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.ObjectDefinition;

public class ObjectDefinitionLoader extends ProtocolWrapperLoader<ObjectDefinition> {
    public ObjectDefinitionLoader(final RT4CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(2).getArchive(6));
    }

    @Override
    public ObjectDefinition load(final int id) {
        FileData data = getValidFile(id);
        ObjectDefinition ret = new ObjectDefinition(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }
}
