package com.artificial.cachereader.wrappers.rt6;

import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.wrappers.rt6.loaders.WrapperLoader;

import java.util.Map;

public class Component extends StreamedWrapper {
    public int index;
    public int parentId;
    public int type;
    public int contentType = 0;
    public String text;
    public int basePositionX;
    public int basePositionY;
    public int baseWidth;
    public int baseHeight;
    public int scrollWidth;
    public int scrollHeight;
    public boolean hidden;
    public boolean disableHover;
    public int textColor;
    public int spritePitch;
    public int spriteYaw;
    public int animationId;
    public int spriteRoll;
    public int spriteScale;
    public String aString2368;
    public String[] actions;
    public String aString2394;
    public String aString2473;
    public String aString2480;
    public Object[] anObjectArray2357;
    public byte aByte2358;
    public Object[] anObjectArray2359;
    Map<Object, Object> aClass667_2373;
    public int anInt2374;
    public int[] anIntArray2380;
    public byte aByte2381;
    public byte aByte2383;
    public int anInt2391;
    public byte aByte2400 = 0;
    public boolean aBool2405;
    public int anInt2406;
    public int anInt2407;
    public int anInt2409;
    public boolean aBool2414;
    public boolean aBool2415;
    public boolean aBool2416;
    public boolean aBool2417;
    public boolean aBool2423;
    public int anInt2425;
    public int anInt2427;
    public int anInt2428;
    public int anInt2429;
    public int anInt2431;
    public int anInt2432;
    public boolean aBool2434;
    public Object[] anObjectArray2440;
    public int anInt2446;
    public Object[] anObjectArray2452;
    public int anInt2453;
    public int anInt2456;
    public int anInt2458;
    public int anInt2463;
    public int anInt2465;
    public int[] anIntArray2466;
    public byte[][] aByteArrayArray2467;
    public boolean aBool2475;
    public int[] anIntArray2476;
    public boolean aBool2479;
    public int anInt2481;
    public boolean aBool2483;
    public int anInt2484;
    public int anInt2486;
    public Object[] anObjectArray2487;
    public Object[] anObjectArray2489;
    public Object[] anObjectArray2490;
    public Object[] anObjectArray2491;
    public Object[] anObjectArray2492;
    public Object[] anObjectArray2493;
    public Object[] anObjectArray2494;
    public Object[] anObjectArray2495;
    public Object[] anObjectArray2496;
    public int[] anIntArray2497;
    public Object[] anObjectArray2498;
    public int[] anIntArray2499;
    public Object[] anObjectArray2500;
    public int[] anIntArray2501;
    public Object[] anObjectArray2502;
    public int[] anIntArray2503;
    public Object[] anObjectArray2504;
    public int[] anIntArray2505;
    public Object[] anObjectArray2508;
    public Object[] anObjectArray2510;
    public boolean aBool2511;
    public boolean aBool2520;
    public boolean aBool2536;
    public boolean aBool2539;
    public int anInt2546;
    public byte[][] aByteArrayArray2549;
    public int anInt2551;
    public Object[] anObjectArray2553;
    public int anInt2554;
    public Object[] anObjectArray2557;

    public Component(WrapperLoader<?> loader, int id) {
        super(loader, id);
    }

    @Override
    public void decode(Stream stream) {
        int i_43_ = stream.getUByte();
        if (255 == i_43_) {
            i_43_ = -1;
        }
        type = stream.getUByte();
        if ((type & 0x80) != 0) {
            type = (type & 0x7f);
            aString2394 = stream.getString();
        }
        contentType = stream.getUShort();
        basePositionX = stream.getShort();
        basePositionY = stream.getShort();
        baseWidth = stream.getUShort();
        baseHeight = stream.getUShort();
        aByte2358 = stream.getByte();
        aByte2383 = stream.getByte();
        aByte2400 = stream.getByte();
        aByte2381 = stream.getByte();
        parentId = stream.getUShort();
        if (65535 == parentId) {
            parentId = 107738123;
        } else {
            parentId = ((id() & ~0xffff) + parentId);
        }
        int i_44_ = stream.getUByte();
        hidden = 0 != (i_44_ & 0x1);
        if (i_43_ >= 0) {
            disableHover = (i_44_ & 0x2) != 0;
        }
        if (type == 0) {
            scrollWidth = stream.getUShort();
            scrollHeight = stream.getUShort();
            if (i_43_ < 0) {
                disableHover = stream.getUByte() == 1;
            }
        }
        if (5 == type) {
            anInt2409 = stream.getInt();
            anInt2391 = stream.getUShort();
            int i_45_ = stream.getUByte();
            aBool2475 = (i_45_ & 0x1) != 0;
            aBool2416 = (i_45_ & 0x2) != 0;
            anInt2406 = stream.getUByte();
            anInt2432 = stream.getUByte();
            anInt2425 = stream.getInt();
            aBool2479 = stream.getUByte() == 1;
            aBool2415 = stream.getUByte() == 1;
            textColor = stream.getInt();
            if (i_43_ >= 3) {
                aBool2417 = stream.getUByte() == 1;
            }
        }
        if (type == 6) {
            anInt2486 = stream.getBigSmart();
            int i_46_ = stream.getUByte();
            boolean bool = (i_46_ & 0x1) == 1;
            aBool2511 = (i_46_ & 0x2) == 2;
            aBool2423 = 4 == (i_46_ & 0x4);
            aBool2434 = 8 == (i_46_ & 0x8);
            if (bool) {
                anInt2554 = stream.getShort();
                anInt2428 = stream.getShort();
                spritePitch = stream.getUShort();
                spriteRoll = stream.getUShort();
                spriteYaw = stream.getUShort();
                spriteScale = stream.getUShort();
            } else if (aBool2511) {
                anInt2554 = stream.getShort();
                anInt2428 = stream.getShort();
                anInt2429 = stream.getShort();
                spritePitch = stream.getUShort();
                spriteRoll = stream.getUShort();
                spriteYaw = stream.getUShort();
                spriteScale = stream.getShort();
            }
            animationId = stream.getBigSmart();
            if (0 != aByte2358) {
                anInt2431 = stream.getUShort();
            }
            if (aByte2383 != 0) {
                anInt2551 = stream.getUShort();
            }
        }
        if (4 == type) {
            anInt2453 = stream.getBigSmart();
            if (i_43_ >= 2) {
                aBool2536 = stream.getUByte() == 1;
            }
            text = stream.getString();
            anInt2456 = stream.getUByte();
            anInt2546 = stream.getUByte();
            anInt2458 = stream.getUByte();
            aBool2520 = stream.getUByte() == 1;
            textColor = stream.getInt();
            anInt2406 = stream.getUByte();
            if (i_43_ >= 0) {
                anInt2374 = stream.getUByte();
            }
        }
        if (type == 3) {
            textColor = stream.getInt();
            aBool2405 = stream.getUByte() == 1;
            anInt2406 = stream.getUByte();
        }
        if (9 == type) {
            anInt2407 = stream.getUByte();
            textColor = stream.getInt();
            aBool2539 = stream.getUByte() == 1;
        }
        int i_47_ = stream.getUInt24();
        int i_48_ = stream.getUByte();
        if (i_48_ != 0) {
            aByteArrayArray2549 = new byte[11][];
            aByteArrayArray2467 = new byte[11][];
            anIntArray2466 = new int[11];
            anIntArray2380 = new int[11];
            for (; i_48_ != 0; i_48_ = stream.getUByte()) {
                int i_49_ = (i_48_ >> 4) - 1;
                i_48_ = i_48_ << 8 | stream.getUByte();
                i_48_ &= 0xfff;
                if (i_48_ == 4095) {
                    i_48_ = -1;
                }
                byte i_50_ = stream.getByte();
                if (i_50_ != 0) {
                    aBool2414 = true;
                }
                byte i_51_ = stream.getByte();
                anIntArray2466[i_49_] = i_48_;
                aByteArrayArray2549[i_49_] = new byte[]{i_50_};
                aByteArrayArray2467[i_49_] = new byte[]{i_51_};
            }
        }
        aString2473 = stream.getString();
        int i_52_ = stream.getUByte();
        int i_53_ = i_52_ & 0xf;
        int i_54_ = i_52_ >> 4;
        if (i_53_ > 0) {
            actions = new String[i_53_];
            for (int i_55_ = 0; i_55_ < i_53_; i_55_++) {
                actions[i_55_] = stream.getString();
            }
        }
        if (i_54_ > 0) {
            int i_56_ = stream.getUByte();
            anIntArray2476 = new int[1 + i_56_];
            for (int i_57_ = 0; i_57_ < anIntArray2476.length; i_57_++) {
                anIntArray2476[i_57_] = -1;
            }
            anIntArray2476[i_56_] = stream.getUShort();
        }
        if (i_54_ > 1) {
            int i_58_ = stream.getUByte();
            anIntArray2476[i_58_] = stream.getUShort();
        }
        aString2368 = stream.getString();
        if (aString2368.equals("")) {
            aString2368 = null;
        }
        anInt2446 = stream.getUByte();
        anInt2465 = stream.getUByte();
        anInt2481 = stream.getUByte();
        aString2480 = stream.getString();
        int i_59_ = -1;
        if (method4432(i_47_) != 0) {
            i_59_ = stream.getUShort();
            if (i_59_ == 65535) {
                i_59_ = -1;
            }
            anInt2484 = stream.getUShort();
            if (anInt2484 == 65535) {
                anInt2484 = 1887779447;
            }
            anInt2463 = stream.getUShort();
            if (65535 == anInt2463) {
                anInt2463 = 1132968959;
            }
        }
        if (i_43_ >= 0) {
            anInt2427 = stream.getUShort();
            if (anInt2427 == 65535) {
                anInt2427 = 592771235;
            }
        }
        //aClass480_Sub16_2462 = new Class480_Sub16(i_47_, i_59_);
        if (i_43_ >= 0) {
            int i_60_ = stream.getUByte();
            for (int i_61_ = 0; i_61_ < i_60_; i_61_++) {
                int i_62_ = stream.getUInt24();
                int i_63_ = stream.getInt();
                aClass667_2373.put(i_62_, (i_63_));
            }
            int i_64_ = stream.getUByte();
            for (int i_65_ = 0; i_65_ < i_64_; i_65_++) {
                int i_66_ = stream.getUInt24();
                String string = stream.getJagString();
                aClass667_2373.put(i_66_, (string));
            }
        }
        anObjectArray2440 = method4273(stream);
        anObjectArray2489 = method4273(stream);
        anObjectArray2491 = method4273(stream);
        anObjectArray2495 = method4273(stream);
        anObjectArray2494 = method4273(stream);
        anObjectArray2496 = method4273(stream);
        anObjectArray2498 = method4273(stream);
        anObjectArray2500 = method4273(stream);
        anObjectArray2508 = method4273(stream);
        anObjectArray2553 = method4273(stream);
        if (i_43_ >= 0) {
            anObjectArray2510 = method4273(stream);
        }
        anObjectArray2490 = method4273(stream);
        anObjectArray2359 = method4273(stream);
        anObjectArray2357 = method4273(stream);
        anObjectArray2487 = method4273(stream);
        anObjectArray2557 = method4273(stream);
        anObjectArray2492 = method4273(stream);
        anObjectArray2493 = method4273(stream);
        anObjectArray2452 = method4273(stream);
        anObjectArray2502 = method4273(stream);
        anObjectArray2504 = method4273(stream);
        anIntArray2497 = method4250(stream);
        anIntArray2499 = method4250(stream);
        anIntArray2501 = method4250(stream);
        anIntArray2503 = method4250(stream);
        anIntArray2505 = method4250(stream);
    }

    Object[] method4273(Stream stream) {
        int i_32_ = stream.getUByte();
        if (0 == i_32_) {
            return null;
        }
        Object[] objects = new Object[i_32_];
        for (int i_33_ = 0; i_33_ < i_32_; i_33_++) {
            int i_34_ = stream.getUByte();
            if (i_34_ == 0) {
                objects[i_33_] = stream.getInt();
            } else if (i_34_ == 1) {
                objects[i_33_] = stream.getString();
            }
        }
        aBool2483 = true;
        return objects;
    }

    private int[] method4250(Stream stream) {
        int i_30_ = stream.getUByte();
        if (i_30_ == 0) {
            return null;
        }
        int[] is = new int[i_30_];
        for (int i_31_ = 0; i_31_ < i_30_; i_31_++) {
            is[i_31_] = stream.getInt();
        }
        return is;
    }

    private int method4432(int i) {
        return i >> 11 & 0x7f;
    }

    @Override
    public String toString() {
        return index + " (" + id() + ")";
    }
}
