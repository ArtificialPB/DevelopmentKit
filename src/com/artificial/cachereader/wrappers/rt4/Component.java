/*
  Copyright (c) 2017, Adam <Adam@sigterm.info>
  All rights reserved.
  <p>
  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:
  <p>
  1. Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.
  2. Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.
  <p>
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.artificial.cachereader.wrappers.rt4;

import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt4.loaders.WrapperLoader;

public class Component extends StreamedWrapper {
    public int index = 0;
    public int parentId = -1;
    public boolean hasScript = true;
    public int componentType;
    public int specialType;
    public String text;
    public String name;
    public String[] actions;
    public String selectedAction;
    public String[] interfaceActions;
    public String textFormatting;
    public String spellName; //
    public String tooltip;
    public int originalX;
    public int originalY;
    public int originalWidth;
    public int originalHeight;
    public boolean isHidden;
    public int scrollWidth;
    public int scrollHeight;
    public int textureId;
    public int opacity;
    public int borderThickness;
    public int sprite2;
    public boolean flippedVertically;
    public boolean flippedHorizontally;
    public int modelType;
    public int modelId;
    public int rotationX;
    public int rotationY;
    public int rotationZ;
    public int modelZoom;
    public boolean textCentered;
    public int textColor;
    public boolean filled;
    public int fontId;
    public Object[] compTextureSpells;
    public int actionType;
    public int[][] dynamicValues;
    public int[] itemIds;
    public int[] itemQuantities;
    public int[] spritesX;
    public int[] spritesY;
    public int[] sprites;
    public int activeColor;
    public int mouseOverColor;
    public int mouseOverActiveColor;
    public int field2226;
    public int field2227;
    public int field2351;
    public int field2217;
    public boolean field2210;
    public int field2294;
    public boolean field2257;
    public int field2268;
    public int field2269;
    public int field2266;
    public boolean field2296;
    public int field2274;
    public int field2212;
    public int field2219;
    public int field2283;
    public int field2218;
    public boolean field2253;
    public int field2291;
    public int field2295;
    public int field2223;
    public boolean field2297;
    public Object[] field2300; //
    public Object[] field2248; //
    public Object[] field2311; //
    public Object[] field2350; //
    public Object[] field2312; //
    public Object[] field2314; //
    public Object[] field2316; //
    public Object[] field2318; //
    public Object[] field2319; //
    public Object[] field2306; //
    public Object[] settingsConfigAndOther;
    public Object[] field2287; //
    public Object[] field2303; //
    public Object[] field2304; //
    public Object[] field2308; //
    public Object[] field2310; //
    public Object[] field2262; //
    public int[] field2313;
    public int[] field2315;
    public int[] field2282;
    public boolean field2299;
    public int field2334;
    public int[] field2224;
    public int[] field2333;
    public int field2285;
    public int field2286;
    public int field2332;
    public int field2264;
    public int field2265;
    public int field2276;

    public Component(WrapperLoader<?> loader, int id) {
        super(loader, id);
    }

    @Override
    public void decode(Stream stream) {
        if (stream.getAllBytes()[0] == -1) {
            decodeActiveInterface(stream);
        } else {
            decodeInterface(stream);
        }
    }

    private void decodeInterface(Stream stream) {
        hasScript = false;
        componentType = stream.getUByte();
        actionType = stream.getUByte();
        specialType = stream.getUShort();
        originalX = stream.getShort();
        originalY = stream.getShort();
        originalWidth = stream.getUShort();
        originalHeight = stream.getUShort();
        opacity = stream.getUByte();
        parentId = stream.getUShort();
        if (parentId == '\uffff') {
            parentId = -1;
        } else {
            parentId += id() & -65536;
        }

        field2334 = stream.getUShort();
        if (field2334 == '\uffff') {
            field2334 = -1;
        }

        int var2 = stream.getUByte();
        int var3;
        if (var2 > 0) {
            field2224 = new int[var2];
            field2333 = new int[var2];

            for (var3 = 0; var3 < var2; ++var3) {
                field2224[var3] = stream.getUByte();
                field2333[var3] = stream.getUShort();
            }
        }

        var3 = stream.getUByte();
        int var4;
        int var5;
        int var6;
        if (var3 > 0) {
            dynamicValues = new int[var3][];

            for (var4 = 0; var4 < var3; ++var4) {
                var5 = stream.getUShort();
                dynamicValues[var4] = new int[var5];

                for (var6 = 0; var6 < var5; ++var6) {
                    dynamicValues[var4][var6] = stream.getUShort();
                    if (dynamicValues[var4][var6] == '\uffff') {
                        dynamicValues[var4][var6] = -1;
                    }
                }
            }
        }

        if (componentType == 0) {
            scrollHeight = stream.getUShort();
            isHidden = stream.getUByte() == 1;
        }

        if (componentType == 1) {
            stream.getUShort();
            stream.getUByte();
        }

        if (componentType == 2) {
            itemIds = new int[originalWidth * originalHeight];
            itemQuantities = new int[originalHeight * originalWidth];
            var4 = stream.getUByte();
            if (var4 == 1) {
                field2291 |= 268435456;
            }

            var5 = stream.getUByte();
            if (var5 == 1) {
                field2291 |= 1073741824;
            }

            var6 = stream.getUByte();
            if (var6 == 1) {
                field2291 |= Integer.MIN_VALUE;
            }

            int var7 = stream.getUByte();
            if (var7 == 1) {
                field2291 |= 536870912;
            }

            field2285 = stream.getUByte();
            field2286 = stream.getUByte();
            spritesX = new int[20];
            spritesY = new int[20];
            sprites = new int[20];

            int var8;
            for (var8 = 0; var8 < 20; ++var8) {
                int var9 = stream.getUByte();
                if (var9 == 1) {
                    spritesX[var8] = stream.getShort();
                    spritesY[var8] = stream.getShort();
                    sprites[var8] = stream.getInt();
                } else {
                    sprites[var8] = -1;
                }
            }

            interfaceActions = new String[5];

            for (var8 = 0; var8 < 5; ++var8) {
                String var11 = stream.getString();
                if (var11.length() > 0) {
                    interfaceActions[var8] = var11;
                    field2291 |= 1 << var8 + 23;
                }
            }
        }

        if (componentType == 3) {
            filled = stream.getUByte() == 1;
        }

        if (componentType == 4 || componentType == 1) {
            field2219 = stream.getUByte();
            field2283 = stream.getUByte();
            field2212 = stream.getUByte();
            fontId = stream.getUShort();
            if (fontId == '\uffff') {
                fontId = -1;
            }

            textCentered = stream.getUByte() == 1;
        }

        if (componentType == 4) {
            text = stream.getString();
            textFormatting = stream.getString();
        }

        if (componentType == 1 || componentType == 3 || componentType == 4) {
            textColor = stream.getInt();
        }

        if (componentType == 3 || componentType == 4) {
            activeColor = stream.getInt();
            mouseOverColor = stream.getInt();
            mouseOverActiveColor = stream.getInt();
        }

        if (componentType == 5) {
            textureId = stream.getInt();
            field2332 = stream.getInt();
        }

        if (componentType == 6) {
            modelType = 1;
            modelId = stream.getUShort();
            if (modelId == '\uffff') {
                modelId = -1;
            }

            field2264 = 1;
            field2265 = stream.getUShort();
            if (field2265 == '\uffff') {
                field2265 = -1;
            }

            field2266 = stream.getUShort();
            if (field2266 == '\uffff') {
                field2266 = -1;
            }

            field2276 = stream.getUShort();
            if (field2276 == '\uffff') {
                field2276 = -1;
            }

            modelZoom = stream.getUShort();
            rotationX = stream.getUShort();
            rotationZ = stream.getUShort();
        }

        if (componentType == 7) {
            itemIds = new int[originalWidth * originalHeight];
            itemQuantities = new int[originalWidth * originalHeight];
            field2219 = stream.getUByte();
            fontId = stream.getUShort();
            if (fontId == '\uffff') {
                fontId = -1;
            }

            textCentered = stream.getUByte() == 1;
            textColor = stream.getInt();
            field2285 = stream.getShort();
            field2286 = stream.getShort();
            var4 = stream.getUByte();
            if (var4 == 1) {
                field2291 |= 1073741824;
            }

            interfaceActions = new String[5];

            for (var5 = 0; var5 < 5; ++var5) {
                String var10 = stream.getString();
                if (var10.length() > 0) {
                    interfaceActions[var5] = var10;
                    field2291 |= 1 << var5 + 23;
                }
            }
        }

        if (componentType == 8) {
            text = stream.getString();
        }

        if (actionType == 2 || componentType == 2) {
            selectedAction = stream.getString();
            spellName = stream.getString();
            var4 = stream.getUShort() & 63;
            field2291 |= var4 << 11;
        }

        if (actionType == 1 || actionType == 4 || actionType == 5 || actionType == 6) {
            tooltip = stream.getString();
            if (tooltip.length() == 0) {
                if (actionType == 1) {
                    tooltip = "Ok";
                }

                if (actionType == 4) {
                    tooltip = "Select";
                }

                if (actionType == 5) {
                    tooltip = "Select";
                }

                if (actionType == 6) {
                    tooltip = "Continue";
                }
            }
        }

        if (actionType == 1 || actionType == 4 || actionType == 5) {
            field2291 |= 4194304;
        }

        if (actionType == 6) {
            field2291 |= 1;
        }

    }

    private void decodeActiveInterface(Stream stream) {
        stream.getUByte();
        hasScript = true;
        componentType = stream.getUByte();
        specialType = stream.getUShort();
        originalX = stream.getShort();
        originalY = stream.getShort();
        originalWidth = stream.getUShort();
        if (componentType == 9) {
            originalHeight = stream.getShort();
        } else {
            originalHeight = stream.getUShort();
        }

        field2226 = stream.getByte();
        field2227 = stream.getByte();
        field2351 = stream.getByte();
        field2217 = stream.getByte();
        parentId = stream.getUShort();
        if (parentId == '\uffff') {
            parentId = -1;
        } else {
            parentId += id & -65536;
        }

        isHidden = stream.getUByte() == 1;
        if (componentType == 0) {
            scrollWidth = stream.getUShort();
            scrollHeight = stream.getUShort();
            field2210 = stream.getUByte() == 1;
        }

        if (componentType == 5) {
            textureId = stream.getInt();
            field2294 = stream.getUShort();
            field2257 = stream.getUByte() == 1;
            opacity = stream.getUByte();
            borderThickness = stream.getUByte();
            sprite2 = stream.getInt();
            flippedVertically = stream.getUByte() == 1;
            flippedHorizontally = stream.getUByte() == 1;
        }

        if (componentType == 6) {
            modelType = 1;
            modelId = stream.getUShort();
            if (modelId == '\uffff') {
                modelId = -1;
            }

            field2268 = stream.getShort();
            field2269 = stream.getShort();
            rotationX = stream.getUShort();
            rotationZ = stream.getUShort();
            rotationY = stream.getUShort();
            modelZoom = stream.getUShort();
            field2266 = stream.getUShort();
            if (field2266 == '\uffff') {
                field2266 = -1;
            }

            field2296 = stream.getUByte() == 1;
            stream.getUShort();
            if (field2226 != 0) {
                field2274 = stream.getUShort();
            }

            if (field2227 != 0) {
                stream.getUShort();
            }
        }

        if (componentType == 4) {
            fontId = stream.getUShort();
            if (fontId == '\uffff') {
                fontId = -1;
            }

            text = stream.getString();
            field2212 = stream.getUByte();
            field2219 = stream.getUByte();
            field2283 = stream.getUByte();
            textCentered = stream.getUByte() == 1;
            textColor = stream.getInt();
        }

        if (componentType == 3) {
            textColor = stream.getInt();
            filled = stream.getUByte() == 1;
            opacity = stream.getUByte();
        }

        if (componentType == 9) {
            field2218 = stream.getUByte();
            textColor = stream.getInt();
            field2253 = stream.getUByte() == 1;
        }

        field2291 = stream.getUInt24();
        name = stream.getString();
        int var2 = stream.getUByte();
        if (var2 > 0) {
            actions = new String[var2];

            for (int var3 = 0; var3 < var2; ++var3) {
                actions[var3] = stream.getString();
            }
        }

        field2295 = stream.getUByte();
        field2223 = stream.getUByte();
        field2297 = stream.getUByte() == 1;
        selectedAction = stream.getString();
        compTextureSpells = this.readScriptParams(stream);
        field2300 = this.readScriptParams(stream);
        field2248 = this.readScriptParams(stream);
        field2311 = this.readScriptParams(stream);
        field2350 = this.readScriptParams(stream);
        field2312 = this.readScriptParams(stream);
        field2314 = this.readScriptParams(stream);
        field2316 = this.readScriptParams(stream);
        field2318 = this.readScriptParams(stream);
        field2319 = this.readScriptParams(stream);
        field2306 = this.readScriptParams(stream);
        settingsConfigAndOther = this.readScriptParams(stream);
        field2287 = this.readScriptParams(stream);
        field2303 = this.readScriptParams(stream);
        field2304 = this.readScriptParams(stream);
        field2308 = this.readScriptParams(stream);
        field2310 = this.readScriptParams(stream);
        field2262 = this.readScriptParams(stream);
        field2313 = this.readTriggers(stream);
        field2315 = this.readTriggers(stream);
        field2282 = this.readTriggers(stream);
    }

    private Object[] readScriptParams(Stream stream) {
        int var2 = stream.getUByte();
        if (var2 == 0) {
            return null;
        } else {
            Object[] var3 = new Object[var2];

            for (int var4 = 0; var4 < var2; ++var4) {
                int var5 = stream.getUByte();
                if (var5 == 0) {
                    var3[var4] = new Integer(stream.getInt());
                } else if (var5 == 1) {
                    var3[var4] = stream.getString();
                }
            }

            field2299 = true;
            return var3;
        }
    }

    private int[] readTriggers(Stream stream) {
        int var2 = stream.getUByte();
        if (var2 == 0) {
            return null;
        } else {
            int[] var3 = new int[var2];

            for (int var4 = 0; var4 < var2; ++var4) {
                var3[var4] = stream.getInt();
            }

            return var3;
        }
    }

    @Override
    public String toString() {
        return index + " (" + id() + ")";
    }
}
