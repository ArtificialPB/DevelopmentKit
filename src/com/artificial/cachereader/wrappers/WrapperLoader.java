package com.artificial.cachereader.wrappers;

import com.artificial.cachereader.fs.CacheSystem;

public abstract class WrapperLoader<T extends Wrapper, C extends CacheSystem> {

    protected final C cacheSystem;

    public WrapperLoader(final C cacheSystem) {
        this.cacheSystem = cacheSystem;
    }

    public C getCacheSystem() {
        return cacheSystem;
    }

    public abstract T load(final int id);

    public abstract boolean canLoad(final int id);
}
