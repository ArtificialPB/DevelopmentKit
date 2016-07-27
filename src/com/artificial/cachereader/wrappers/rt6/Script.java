package com.artificial.cachereader.wrappers.rt6;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt6.loaders.ScriptLoader;

public class Script extends ProtocolWrapper {

    public int configId = -1;
    public int configType = -1;
    public int lowerBitIndex = -1;
    public int upperBitIndex = -1;

    public Script(final ScriptLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (opcode == 1) {
            configType = stream.getUByte();
            configId = stream.getBigSmart();
        } else if (opcode == 2) {
            lowerBitIndex = stream.getUByte();
            upperBitIndex = stream.getUByte();
        }
    }
}
