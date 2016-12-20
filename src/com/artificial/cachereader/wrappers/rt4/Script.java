package com.artificial.cachereader.wrappers.rt4;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt4.loaders.ScriptLoader;

public class Script extends ProtocolWrapper implements com.artificial.cachereader.wrappers.Script {

    public int configId = -1;
    public int lowerBitIndex = -1;
    public int upperBitIndex = -1;

    public Script(final ScriptLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (opcode == 1) {
            configId = stream.getUShort();
            lowerBitIndex = stream.getUByte();
            upperBitIndex = stream.getUByte();
        }
    }

    @Override
    public int configId() {
        return configId;
    }

    @Override
    public int lowerBitIndex() {
        return lowerBitIndex;
    }

    @Override
    public int upperBitIndex() {
        return upperBitIndex;
    }
}
