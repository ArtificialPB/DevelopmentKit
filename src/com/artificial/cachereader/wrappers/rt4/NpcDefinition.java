package com.artificial.cachereader.wrappers.rt4;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.Dynamic;
import com.artificial.cachereader.wrappers.rt4.loaders.NpcDefinitionLoader;

public class NpcDefinition extends ProtocolWrapper implements Dynamic {

    public String name;
    public String[] actions = new String[5];

    public int combatLevel = -1;
    public int headIcon = -1;
    public boolean clickable = true;
    public boolean visible = true;

    public int scriptId = -1, configId = -1;
    public int[] childrenIds;
    public int[] modelIds;
    public short[] modifiedColors, originalColors;

    public NpcDefinition(final NpcDefinitionLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (1 == opcode) {
            final int s = stream.getUByte();
            this.modelIds = new int[s];
            for (int i = 0; i < s; ++i) {
                this.modelIds[i] = stream.getUShort();
            }
        } else if (opcode == 2) {
            this.name = stream.getString();
        } else if (12 == opcode) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 13) {
            skipValue(opcode, stream.getUShort());
        } else if (14 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 15) {
            skipValue(opcode, stream.getUShort());
        } else if (16 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 17) {
            skipValue(opcode, stream.getUShort(), stream.getUShort(), stream.getUShort(), stream.getUShort());
        } else if (opcode >= 30 && opcode < 35) {
            this.actions[opcode - 30] = stream.getString();
            if (this.actions[opcode - 30].equalsIgnoreCase("Hidden")) {
                this.actions[opcode - 30] = null;
            }
        } else if (opcode == 40) {
            final int s2 = stream.getUByte();
            this.modifiedColors = new short[s2];
            this.originalColors = new short[s2];
            for (int j = 0; j < s2; ++j) {
                this.modifiedColors[j] = (short) stream.getUShort();
                this.originalColors[j] = (short) stream.getUShort();
            }
        } else if (opcode == 41) {
            final int s3 = stream.getUByte();
            short[] f = new short[s3];
            short[] t = new short[s3];
            for (int k = 0; k < s3; ++k) {
                f[k] = (short) stream.getUShort();
                t[k] = (short) stream.getUShort();
            }
        } else if (60 == opcode) {
            final int s4 = stream.getUByte();
            for (int l = 0; l < s4; ++l) {
                stream.getUShort();
            }
        } else if (opcode == 93) {
            //drawMinimapDot
        } else if (opcode == 95) {
            this.combatLevel = stream.getUShort();
        } else if (opcode == 97) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 98) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 99) {
            this.visible = true;
        } else if (opcode == 100) {
            skipValue(opcode, stream.getByte());
        } else if (101 == opcode) {
            skipValue(opcode, stream.getByte());
        } else if (102 == opcode) {
            this.headIcon = stream.getUShort();
        } else if (103 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 106) {
            int script = stream.getUShort();
            int config = stream.getUShort();
            int count = stream.getUByte();
            childrenIds = new int[count + 1];
            for (int i = 0; i < childrenIds.length; i++) {
                childrenIds[i] = stream.getUShort();
                if (childrenIds[i] == 0xffff) {
                    childrenIds[i] = -1;
                }
            }
            scriptId = script == 0xFFFF ? -1 : script;
            configId = config == 0xFFFF ? -1 : config;
        } else if (107 == opcode) {
            this.clickable = false;
        } else if (109 == opcode) {
            //this.az = false;
        } else if (opcode == 111) {
            // this.as = true;
        } else if (opcode == 112) {
            skipValue(opcode, stream.getUByte());
        }
    }

    @Override
    public int scriptId() {
        return scriptId == -1 ? configId : scriptId;
    }

    @Override
    public int[] childrenIds() {
        return childrenIds;
    }
}
