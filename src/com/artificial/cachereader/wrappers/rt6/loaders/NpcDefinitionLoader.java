package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.NpcDefinition;

public class NpcDefinitionLoader extends ProtocolWrapperLoader<NpcDefinition> {

    public NpcDefinitionLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(18));
    }

    @Override
    public NpcDefinition load(final int id) {
        FileData data = getValidFile(id);
        NpcDefinition ret = new NpcDefinition(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }

    @Override
    protected FileData getFile(final int id) {
        Archive archive = cache.getArchive(id >>> 7);
        if (archive == null) {
            return null;
        }
        return archive.getFile(id & 0x7f);
    }

}
