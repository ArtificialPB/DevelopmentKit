package com.artificial.cachereader.wrappers.rt6;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt6.loaders.WrapperLoader;

public abstract class StreamedWrapper extends Wrapper {

    public StreamedWrapper(final WrapperLoader<?> loader, final int id) {
        super(loader, id);
    }

    public abstract void decode(final Stream stream);

}
