package com.artificial.cachereader.wrappers.rt4.loaders;


import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.NpcDefinition;

public class NpcDefinitionLoader extends ProtocolWrapperLoader<NpcDefinition> {
    public NpcDefinitionLoader(final RT4CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(2).getArchive(9));
    }

    @Override
    public NpcDefinition load(final int id) {
        FileData data = getValidFile(id);
        NpcDefinition ret = new NpcDefinition(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }
}
