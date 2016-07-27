package com.artificial.cachereader.wrappers.rt4;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt4.loaders.WrapperLoader;

public abstract class ProtocolWrapper extends StreamedWrapper {


    public ProtocolWrapper(final WrapperLoader<?> loader, final int id) {
        super(loader, id);
    }

    @Override
    public void decode(final Stream stream) {
        int opcode;
        while ((opcode = stream.getUByte()) != 0) {
            decodeOpcode(stream, opcode);
        }
    }

    protected abstract void decodeOpcode(final Stream stream, final int opcode);

}
