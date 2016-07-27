package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.ObjectDefinition;

public class ObjectDefinitionLoader extends ProtocolWrapperLoader<ObjectDefinition> {

    public ObjectDefinitionLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(16));
    }

    @Override
    public ObjectDefinition load(final int id) {
        FileData data = getValidFile(id);
        ObjectDefinition ret = new ObjectDefinition(this, id);
        ret.decode(data.getDataAsStream());
        fixObject(ret);
        return ret;
    }

    private void fixObject(final ObjectDefinition ret) {
        if (ret.walkable2) {
            ret.walkable = false;
            ret.blockType = 0;
        }
    }
}
