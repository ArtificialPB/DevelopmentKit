package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.QuestDefinition;

public class QuestDefinitionLoader extends WrapperLoader<QuestDefinition> {

    private final Archive source;

    public QuestDefinitionLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem);
        source = cacheSystem.getCacheSource().getCacheType(2).getArchive(35);
    }

    @Override
    public QuestDefinition load(final int id) {
        FileData data = source.getFile(id);
        if (data == null) {
            throw new IllegalArgumentException("Bad quest id");
        }
        QuestDefinition ret = new QuestDefinition(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }

    @Override
    public boolean canLoad(final int id) {
        return source.getFile(id) != null;
    }

}
