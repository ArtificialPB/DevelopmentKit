package com.artificial.cachereader.wrappers.rt4;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt4.loaders.WrapperLoader;

import java.util.LinkedHashMap;
import java.util.Map;

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

    protected Map<Integer, Object> decodeParams(final Stream stream) {
        int h = stream.getUByte();
        Map<Integer, Object> params = new LinkedHashMap<Integer, Object>();
        for (int m = 0; m < h; m++) {
            boolean r = stream.getUByte() == 1;
            int key = stream.getUInt24();
            Object value = (r) ? stream.getString() : stream.getInt();
            params.put(key, value);
        }
        return params;
    }

}
