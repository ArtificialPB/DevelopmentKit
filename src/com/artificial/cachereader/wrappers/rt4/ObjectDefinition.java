package com.artificial.cachereader.wrappers.rt4;

import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.Dynamic;
import com.artificial.cachereader.wrappers.rt4.loaders.ObjectDefinitionLoader;

import java.util.Map;

public class ObjectDefinition extends ProtocolWrapper implements Dynamic {
    public String name;
    public String[] actions = new String[5];
    public int type = -1;
    public int width = 1;
    public int height = 1;
    public boolean walkable = true;
    public boolean walkable2 = false;
    public int blockType = 2;
    public int scriptId = -1;
    public int configId = -1;
    public int[] childrenIds;
    public int[] modelTypes;
    public int[] modelIds;
    public int[] modifiedColors, originalColors;
    public int animationId;
    public int icon;
    public boolean rotated;
    public int face;
    public boolean castingShadow;
    public int modelSizeZ;
    public int modelSizeX;
    public int modelSizeY;
    public int approachableDirection;
    public Map<Integer, Object> params;

    public ObjectDefinition(final ObjectDefinitionLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (1 == opcode) {
            int count = stream.getUByte();
            if (modelIds == null) {
                modelIds = new int[count];
                modelTypes = new int[count];
                for (int i = 0; i < count; i++) {
                    modelIds[i] = stream.getUShort();
                    modelTypes[i] = stream.getUByte();
                }
            } else {
                stream.skip(count * 3);
            }
        } else if (2 == opcode) {
            this.name = stream.getString();
        } else if (5 == opcode) {
            int count = stream.getUByte();
            if (count > 0) {
                if (modelIds == null) {
                    modelIds = new int[count];
                    for (int i = 0; i < count; i++) {
                        modelIds[i] = stream.getUShort();
                    }
                    modelTypes = null;
                } else {
                    stream.skip(count * 2);
                }
            }
        } else if (14 == opcode) {
            this.width = stream.getUByte();
        } else if (opcode == 15) {
            this.height = stream.getUByte();
        } else if (17 == opcode) {
            this.walkable = false;
            blockType = 0;
        } else if (opcode == 18) {
            this.walkable = false;
        } else if (opcode == 19) {
            skipValue(opcode, stream.getUByte());
        } else if (21 == opcode) {
            this.type = 0;
        } else if (22 == opcode) {
            //this.d = true;
        } else if (23 == opcode) {
            // this.y = true;
        } else if (24 == opcode) {
            this.animationId = stream.getUShort();
        } else if (opcode == 27) {
            blockType = 1;
        } else if (opcode == 28) {
            skipValue(opcode, stream.getUByte());
        } else if (29 == opcode) {
            skipValue(opcode, stream.getByte());
        } else if (opcode == 39) {
            skipValue(opcode, stream.getByte());
        } else if (opcode >= 30 && opcode <= 34) {
            this.actions[opcode - 30] = stream.getString();
            if (this.actions[opcode - 30].equalsIgnoreCase("Hidden")) {
                this.actions[opcode - 30] = null;
            }
        } else if (opcode == 40) {
            int count = stream.getUByte();
            originalColors = new int[count];
            modifiedColors = new int[count];
            for (int i = 0; i < count; i++) {
                originalColors[i] = stream.getUShort();
                modifiedColors[i] = stream.getUShort();
            }
        } else if (opcode == 41) {
            final int s4 = stream.getUByte();
            short[] g = new short[s4];
            short[] s = new short[s4];
            for (int l = 0; l < s4; ++l) {
                g[l] = (short) stream.getUShort();
                s[l] = (short) stream.getUShort();
            }
        } else if (opcode == 60) {
            this.icon = stream.getUShort();
        } else if (62 == opcode) {
            this.rotated = true;
        } else if (opcode == 64) {
            this.castingShadow = false;
        } else if (65 == opcode) {
            this.modelSizeX = stream.getUShort();
        } else if (opcode == 66) {
            this.modelSizeY = stream.getUShort();
        } else if (opcode == 67) {
            this.modelSizeZ = stream.getUShort();
        } else if (opcode == 68) {
            skipValue(opcode, stream.getUShort());
        } else if (69 == opcode) {
            approachableDirection = stream.getUByte(); // direction from which the obstacle is accesable
        } else if (opcode == 70) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 71) {
            this.face = stream.getUShort();
        } else if (opcode == 72) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 73) {
            //this.at = true;
        } else if (74 == opcode) {
            this.walkable2 = true;
        } else if (opcode == 75) {
            skipValue(opcode, stream.getUByte());
        } else if (77 == opcode || opcode == 92) {
            int script = stream.getUShort();
            int config = stream.getUShort();
            int t = -1;
            if (opcode == 92) {
                t = stream.getUShort();
                if (t == 65535) {
                    t = -1;
                }
            }
            int count = stream.getUByte();
            childrenIds = new int[count + 2];
            for (int i = 0; i < count + 1; i++) {
                childrenIds[i] = stream.getUShort();
                if (childrenIds[i] == 0xffff) {
                    childrenIds[i] = -1;
                }
            }
            childrenIds[count + 1] = t;
            scriptId = script == 0xFFFF ? -1 : script;
            configId = config == 0xFFFF ? -1 : config;
        } else if (opcode == 78) {
            skipValue(opcode, stream.getUShort(), stream.getUByte());
        } else if (79 == opcode) {
            int ao = stream.getUShort();
            int an = stream.getUShort();
            int aj = stream.getUByte();
            final int s6 = stream.getUByte();
            int[] ad = new int[s6];
            for (int n4 = 0; n4 < s6; ++n4) {
                ad[n4] = stream.getUShort();
            }
        } else if (81 == opcode) {
            this.type = stream.getUByte();
        } else if (opcode == 82) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 249) {
            this.params = decodeParams(stream);
        }
    }

    @Override
    public boolean dynamic() {
        return scriptId != -1 || configId != -1;
    }

    @Override
    public int scriptId() {
        return scriptId;
    }

    @Override
    public int configId() {
        return configId;
    }

    @Override
    public int[] childrenIds() {
        return childrenIds;
    }
}
