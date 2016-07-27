package com.artificial.cachereader.wrappers.rt6;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.Dynamic;
import com.artificial.cachereader.wrappers.rt6.loaders.NpcDefinitionLoader;

import java.util.HashMap;
import java.util.Map;

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
    public int[][] modelOffsets;

    public Map<Integer, Object> parameters = new HashMap<Integer, Object>();

    public NpcDefinition(final NpcDefinitionLoader loader, final int id) {
        super(loader, id);
    }

    @Override
    protected void decodeOpcode(final Stream stream, final int opcode) {
        if (opcode == 1) {
            int A = stream.getUByte();
            modelIds = new int[A];
            for (int i = 0; i < A; i++) {
                modelIds[i] = stream.getBigSmart();
            }
        } else if (opcode == 2) {
            name = stream.getString();
        } else if (opcode == 12) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode >= 30 && opcode < 35) {
            actions[opcode - 30] = stream.getString();
        } else if (opcode == 39) {
            skipValue(opcode, stream.getByte() * 5);
        } else if (opcode == 40) {
            int B = stream.getUByte();
            int[] originalColors = new int[B];
            int[] modifiedColors = new int[B];
            for (int E = 0; E < B; E++) {
                originalColors[E] = stream.getUShort();
                modifiedColors[E] = stream.getUShort();
            }
            skipValue(opcode, B, originalColors, modifiedColors);
        } else if (opcode == 41) {
            int M = stream.getUByte();
            int[] arr1 = new int[M];
            int[] arr2 = new int[M];
            for (int v = 0; v < M; v++) {
                arr1[v] = stream.getShort();
                arr2[v] = stream.getShort();
            }
            skipValue(opcode, M, arr1, arr2);
        } else if (opcode == 42) {
            int g = stream.getUByte();
            int[] arr = new int[g];
            for (int F = 0; F < g; F++) {
                arr[F] = stream.getByte();
            }
            skipValue(opcode, g, arr);
        } else if (opcode == 44) {
            int L = stream.getUShort();
            skipValue(opcode, L);
// int j = 0;
// for (int C = L; C > 0; C = C >> 1) {
// j++;
// }
// _kud = new Array(j);
// int e = 0;
// for (int C = 0; C < j; C++) {
// if ((L & (1 << C)) > 0) {
// _kud[C] = e++;
// } else {
// _kud[C] = -1;
// }
// }
        } else if (opcode == 45) {
            int J = stream.getUShort();
            skipValue(opcode, J);
// int K = 0;
// for (int C = J; C > 0; C = C >> 1) {
// K++;
// }
// _kui = new Array(K);
// int e = 0;
// for (int C = 0; C < K; C++) {
// if ((J & (1 << C)) > 0) {
// _kui[C] = e++;
// } else {
// _kui[C] = -1;
// }
// }
        } else if (opcode == 60) {
            int size = stream.getUByte();
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = stream.getBigSmart();
            }
            skipValue(opcode, size, arr);
        } else if (opcode == 93) {
            visible = false;
        } else if (opcode == 95) {
            combatLevel = stream.getUShort();
        } else if (opcode == 97 || opcode == 98) {
            float[] resize = null;
            if (resize == null)
                resize = new float[]{128, 128, 128};
            if (opcode == 97) {
                resize[0] = resize[2] = stream.getUShort();
            } else {
                resize[1] = stream.getUShort();
            }
            skipValue(opcode, resize);
        } else if (opcode == 99) {
            skipValue(opcode, true);
        } else if (opcode == 100) {
            skipValue(opcode, stream.getByte() + 64);
        } else if (opcode == 101) {
            skipValue(opcode, stream.getByte() * 5);
        } else if (opcode == 102) {
            int G = stream.getUByte();
            int x = 0;
            int C = G;
            while (C != 0) {
                x++;
                C >>= 1;
            }
            int[] arr1 = new int[x];
            int[] arr2 = new int[x];
            for (int k = 0; k < x; k++) {
                if ((G & (1 << k)) == 0) {
                    arr1[k] = -1;
                    arr2[k] = -1;
                } else {
                    arr1[k] = stream.getBigSmart();
                    arr2[k] = stream.getSmartMinusOne();
                }
            }
            skipValue(opcode, G, arr1, arr2);
        } else if (opcode == 103) {
            skipValue(stream.getUShort());
        } else if (opcode == 106 || opcode == 118) {
            scriptId = stream.getUShort();
            if (scriptId == 65535) {
                scriptId = -1;
            }
            configId = stream.getUShort();
            if (configId == 65535) {
                configId = -1;
            }
            int defaultChildId = -1;
            if (opcode == 118) {
                defaultChildId = stream.getUShort();
                if (defaultChildId == 65535) {
                    defaultChildId = -1;
                }
            }
            int childCount = stream.getUByte();
            childrenIds = new int[childCount + 2];
            for (int o = 0; o <= childCount; o++) {
                childrenIds[o] = stream.getUShort();
                if (childrenIds[o] == 65535) {
                    childrenIds[o] = -1;
                }
            }
            childrenIds[childCount + 1] = defaultChildId;
        } else if (opcode == 107) {
            clickable = false;
        } else if (opcode == 109) {
            skipValue(opcode, false);
        } else if (opcode == 111) {
            skipValue(opcode, false);
        } else if (opcode == 113) {
            skipValue(opcode, stream.getUShort(), stream.getUShort());
        } else if (opcode == 114) {
            skipValue(opcode, stream.getByte(), stream.getByte());
        } else if (opcode == 119) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 121) {
            modelOffsets = new int[modelIds.length][3];
            int t = stream.getUByte();
            for (int r = 0; r < t; r++) {
                int u = stream.getUByte();
                modelOffsets[u][0] = stream.getByte();
                modelOffsets[u][1] = stream.getByte();
                modelOffsets[u][2] = stream.getByte();
            }
        } else if (opcode == 123) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 125) {
            skipValue(opcode, stream.getByte());
        } else if (opcode == 127) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 128) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 134) {
            int _txc = stream.getUShort();
            if (_txc == 65535) {
                _txc = -1;
            }
            int _txe = stream.getUShort();
            if (_txe == 65535) {
                _txe = -1;
            }
            int _txf = stream.getUShort();
            if (_txf == 65535) {
                _txf = -1;
            }
            int _txh = stream.getUShort();
            if (_txh == 65535) {
                _txh = -1;
            }
            int _txi = stream.getUByte();
            skipValue(opcode, _txc, _txe, _txf, _txh, _txi);
        } else if (opcode == 137) {
// arr len 6 default -1 index 1
            skipValue(opcode, 1, stream.getUShort());
        } else if (opcode == 138) {
            headIcon = stream.getBigSmart();
        } else if (opcode == 139) {
            skipValue(opcode, stream.getBigSmart());
        } else if (opcode == 140) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 141) {
            skipValue(opcode, true);
        } else if (opcode == 142) {
            skipValue(opcode, stream.getUShort());
        } else if (opcode == 143) {
            skipValue(opcode, true);
        } else if (opcode >= 150 && opcode < 155) {
            actions[opcode - 150] = stream.getString();
        } else if (opcode == 155) {
            skipValue(opcode, stream.getByte(), stream.getByte(), stream.getByte(), stream.getByte());
        } else if (opcode == 158) {
            skipValue(opcode, 1);
        } else if (opcode == 159) {
            skipValue(opcode, 0);
        } else if (opcode == 160) {
            int I = stream.getUByte();
            int[] arr = new int[I];
            for (int D = 0; D < I; D++) {
                arr[D] = stream.getUShort();
            }
            skipValue(opcode, I, arr);
        } else if (opcode == 162) {
            skipValue(opcode, true);
        } else if (opcode == 163) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 164) {
            skipValue(opcode, stream.getUShort(), stream.getUShort());
        } else if (opcode == 165) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 168) {
            skipValue(opcode, stream.getUByte());
        } else if (opcode == 169) {
            skipValue(opcode, false);
        } else if (opcode >= 170 && opcode < 176) {
// array len 6 default -1 index opcode-170
            skipValue(opcode, opcode - 170, stream.getUShort());
        } else if (opcode == 178) {
            skipValue(opcode, false);
        } else if (opcode == 179) {
            skipValue(opcode, stream.getSmart(), stream.getSmart(), stream.getSmart(), stream.getSmart(), stream.getSmart(), stream.getSmart());
        } else if (opcode == 180) {
            skipValue(opcode, stream.getUByte() * 1000 / 50);
        } else if (opcode == 181) {
            skipValue(opcode, stream.getUShort(), stream.getUByte());
        } else if (opcode == 182) {
            skipValue(opcode, true);
        } else if (opcode == 249) {
            parameters = decodeParams(stream);
            skipValue(opcode, parameters);
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
