package com.artificial.cachereader.wrappers.rt6;

import com.artificial.cachereader.wrappers.rt6.loaders.WrapperLoader;

public class Widget extends Wrapper {
    public Component[] components = null;

    public Widget(WrapperLoader<?> loader, int id) {
        super(loader, id);
    }
}
