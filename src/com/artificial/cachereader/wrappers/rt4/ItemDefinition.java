package com.artificial.cachereader.wrappers.rt4;

import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt4.loaders.ItemDefinitionLoader;

public class ItemDefinition extends ProtocolWrapper {
    public String name;
    public String[] actions = {null, null, null, null, "Drop"};
    public String[] groundActions = {null, null, "Take", null, null};
    public boolean stackable;
    public boolean members;
    public boolean noted;
    public boolean tradeable;
    public int noteId = -1;
    public int value = 0;
    public int noteTemplateId = -1;
    public int cosmeticId = -1;
    public int cosmeticTemplateId = -1;
    public int modelOffset;
    public short[] originalModelColors;
    public int modelSine;
    public int modelZoom;
    public int modelRotation1;
    public int modelId;
    public short[] modifiedModelColors;
    public int modelRotation2;

    public ItemDefinition(final ItemDefinitionLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (opcode == 1) {
            this.modelId = stream.getUShort();
        } else if (2 == opcode) {
            this.name = stream.getString();
        } else if (opcode == 4) {
            this.modelZoom = stream.getUShort();
        } else if (opcode == 5) {
            this.modelRotation1 = stream.getUShort();
        } else if (6 == opcode) {
            this.modelRotation2 = stream.getUShort();
        } else if (7 == opcode) {
            this.modelOffset = stream.getUShort();
        } else if (8 == opcode) {
            this.modelSine = stream.getUShort();
        } else if (11 == opcode) {
            stackable = true;
        } else if (opcode == 12) {
            value = stream.getInt();
        } else if (opcode == 16) {
            this.members = true;
        } else if (23 == opcode) {
            skipValue(opcode, stream.getUShort(), stream.getUByte());
        } else if (24 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (25 == opcode) {
            skipValue(opcode, stream.getUShort(), stream.getUByte());
        } else if (26 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode >= 30 && opcode <= 34) {
            groundActions[opcode - 30] = stream.getString();
            if (groundActions[opcode - 30].equalsIgnoreCase("Hidden")) {
                groundActions[opcode - 30] = null;
            }
        } else if (opcode >= 35 && opcode <= 39) {
            actions[opcode - 35] = stream.getString();
        } else if (40 == opcode) {
            int arraySize = stream.getUByte();
            this.modifiedModelColors = new short[arraySize];
            for (int i = 0; i < arraySize; ++i) {
                this.modifiedModelColors[i] = (short) stream.getUShort();
                stream.getUShort();
            }
        } else if (opcode == 41) {
            int arraySize = stream.getUByte();
            this.originalModelColors = new short[arraySize];
            for (int i = 0; i < arraySize; ++i) {
                stream.getUShort();
                this.originalModelColors[i] = (short) stream.getUShort();
            }
        } else if (opcode == 42) {
            skipValue(opcode, stream.getByte());
        } else if (opcode == 65) {
            tradeable = true;
        } else if (78 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 79) {
            skipValue(opcode, stream.getUShort());
        } else if (90 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (91 == opcode) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 92) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 93) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 95) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 97) {
            this.noteId = stream.getUShort();
        } else if (opcode == 98) {
            this.noteTemplateId = stream.getUShort();
        } else if (opcode >= 100 && opcode <= 109) {
            skipValue(opcode, stream.getUShort(), stream.getUShort());
        } else if (opcode == 110) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 111) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 112) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 113) {
            skipValue(opcode, stream.getByte());
        } else if (opcode == 114) {
            skipValue(opcode, stream.getByte());
        } else if (opcode == 115) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 139) {
            cosmeticId = stream.getUShort();
        } else if (opcode == 140) {
            cosmeticTemplateId = stream.getUShort();
        } else if (opcode == 148) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 149) {
            skipValue(opcode, stream.getUShort());
        }
    }

}


