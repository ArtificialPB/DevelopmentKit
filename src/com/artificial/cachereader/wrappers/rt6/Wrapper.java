package com.artificial.cachereader.wrappers.rt6;


import com.artificial.cachereader.wrappers.rt6.loaders.WrapperLoader;

public abstract class Wrapper extends com.artificial.cachereader.wrappers.Wrapper<WrapperLoader<?>> {

    public Wrapper(final WrapperLoader<?> loader, final int id) {
        super(loader, id);
    }

}
