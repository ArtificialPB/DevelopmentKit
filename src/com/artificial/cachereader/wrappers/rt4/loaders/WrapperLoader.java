package com.artificial.cachereader.wrappers.rt4.loaders;


import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.Wrapper;

public abstract class WrapperLoader<T extends Wrapper> extends com.artificial.cachereader.wrappers.WrapperLoader<T, RT4CacheSystem> {


    public WrapperLoader(final RT4CacheSystem cacheSystem) {
        super(cacheSystem);
    }

}
