package com.artificial.cachereader.wrappers.rt4;


import com.artificial.cachereader.wrappers.rt4.loaders.WrapperLoader;

public abstract class Wrapper extends com.artificial.cachereader.wrappers.Wrapper<WrapperLoader<?>> {

    public Wrapper(final WrapperLoader<?> loader, final int id) {
        super(loader, id);
    }

}
