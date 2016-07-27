package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.Wrapper;

public abstract class WrapperLoader<T extends Wrapper> extends com.artificial.cachereader.wrappers.WrapperLoader<T, RT6CacheSystem> {

    public WrapperLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem);
    }

}
