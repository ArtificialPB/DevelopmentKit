package com.artificial.cachereader.fs;


import com.artificial.cachereader.DataSource;

public class MetaIndexFile extends IndexFile {

    public MetaIndexFile(DataSource system) {
        super(system, DataSource.META_INDEX_FILE_NUM);
    }
}
