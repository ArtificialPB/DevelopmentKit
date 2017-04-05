package com.artificial.cachereader.datastream;

import com.artificial.cachereader.compress.BZip2Decompressor;

import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.zip.Inflater;

public abstract class Stream {
    public static final int BYTE_SIZE = 8, SHORT_SIZE = 16, INT_SIZE = 32, LONG_SIZE = 64;
    public static final int NUM_LONG_BYTES = LONG_SIZE / BYTE_SIZE;
    public static final int NUM_INT_BYTES = INT_SIZE / BYTE_SIZE;
    public static final int NUM_SHORT_BYTES = SHORT_SIZE / BYTE_SIZE;
    public static final int[] MASKS = new int[INT_SIZE];
    public static final int BYTE_SIGN_BIT = 1 << (BYTE_SIZE - 1);
    public static final int BYTE_MASK;
    public static final int SKIP_SIGN_BYTE_MASK;
    public static final int SHORT_SIGN_BIT = 1 << (SHORT_SIZE - 1);
    public static final int SHORT_MASK;
    public static final int SKIP_SIGN_SHORT_MASK;
    public static final int INT_SIGN_BIT = 1 << (INT_SIZE - 1);
    public static final int SKIP_SIGN_INT_MASK;
    public static final long INT_MASK = 0xffffffffL;
    private static final int MAX_NOSIGN_SHORT = 0x7fff;
    private static final char charSubs[] = {'\u20AC', '\0', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020',
            '\u2021', '\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017D', '\0', '\0', '\u2018',
            '\u2019', '\u201C', '\u201D', '\u2022', '\u2013', '\u2014', '\u02DC', '\u2122', '\u0161', '\u203A',
            '\u0153', '\0', '\u017E', '\u0178'};

    static {
        for (int i = 1, currentMask = 0; i < MASKS.length; ++i) {
            currentMask = currentMask << 1 | 1;
            MASKS[i] = currentMask;
        }
        SKIP_SIGN_BYTE_MASK = MASKS[7];
        BYTE_MASK = MASKS[8];
        SKIP_SIGN_SHORT_MASK = MASKS[15];
        SHORT_MASK = MASKS[16];
        SKIP_SIGN_INT_MASK = MASKS[31];
    }

    public abstract int getLocation();

    public abstract int getLength();

    public abstract byte getByte();

    public abstract void seek(int loc);

    public final int getLeft() {
        return getLength() - getLocation();
    }

    protected final void assertLeft(int num) {
        if (!checkLeft(num))
            throw new BufferUnderflowException();
    }

    public final boolean checkLeft(int num) {
        int byteLeft = getLeft();
        return byteLeft >= num;
    }

    public final int getUByte() {
        return getByte() & BYTE_MASK;
    }

    public final int getUShort() {
        return getUByte() << BYTE_SIZE | getUByte();
    }

    public final int getUInt24() {
        return getUByte() << BYTE_SIZE * 2 | getUByte() << BYTE_SIZE | getUByte();
    }

    public final long getUInt() {
        return getInt() & INT_MASK;
    }

    public final short getShort() {
        return (short) getUShort();
    }

    public final int getInt() {
        return getUByte() << BYTE_SIZE * 3 | getUByte() << BYTE_SIZE * 2 | getUByte() << BYTE_SIZE | getUByte();
    }

    public final long getLong() {
        return getUInt() << INT_SIZE | getUInt();
    }

    public final int getReferenceTableSmart() {
        return getSmart(NUM_SHORT_BYTES, NUM_INT_BYTES, false);
    }

    public final int getBigSmart() {
        int first = getUByte(), joined = 0;
        if ((first & BYTE_SIGN_BIT) != BYTE_SIGN_BIT) {
            joined = joinSmart(first, NUM_SHORT_BYTES, false);
            if (joined == MAX_NOSIGN_SHORT)
                joined = -1;
        } else {
            joined = joinSmart(first, NUM_INT_BYTES, false);
        }
        return joined;
    }

    public final int getSmart() {
        return getSmart(1, NUM_SHORT_BYTES, false);
    }

    public final int getSignedSmart() {
        return getSmart(1, NUM_SHORT_BYTES, true);
    }

    public int getSmartMinusOne() {
        return getSmart() - 1;
    }

    public final int getSmarts() {
        int i = 0;
        for (int j = MAX_NOSIGN_SHORT; j == MAX_NOSIGN_SHORT; i += j) {
            j = getSmart();
        }
        return i;
    }

    protected final int getSmart(int smallSize, int bigSize, boolean signed) {
        int num = getUByte();
        int size = ((num & BYTE_SIGN_BIT) == BYTE_SIGN_BIT ? bigSize : smallSize);
        return joinSmart(num, size, signed);
    }

    protected final int joinSmart(int first, int size, boolean signed) {
        int num = first & ~BYTE_SIGN_BIT;
        for (int i = 1; i < size; ++i) {
            num = (num << BYTE_SIZE) | getUByte();
        }
        if (signed) {
            num -= (1 << size * BYTE_SIZE - 2);
        }
        return num;
    }

    protected final int joinSmart(int size, boolean signed) {
        return getSmart(getUByte(), size, signed);
    }

    public byte[] getAllBytes() {
        byte[] bytes = new byte[getLength()];
        for (int i = 0; i < bytes.length; ++i)
            bytes[i] = getByte();
        return bytes;
    }

    public final byte[] getBytes(final byte[] buffer) {
        return getBytes(buffer, 0, buffer.length);
    }

    public final byte[] getBytes(final byte[] buffer, int off, int len) {
        assertLeft(len);
        len += off;
        for (int k = off; k < len; k++) {
            buffer[k] = getByte();
        }
        return buffer;
    }

    public final void printBytes(int num) {
        assertLeft(num);
        for (int i = 0; i < num; ++i) {
            System.out.printf("%02x ", getUByte());
        }
    }

    public final String getString() {
        StringBuilder ret = new StringBuilder();
        int c;
        while ((c = getUByte()) != 0) {
            if (c >= 128 && c < 160) {
                char n = charSubs[c - 128];
                if (n == 0) {
                    n = '?';
                }
                c = n;
            }
            ret.append((char) c);
        }
        return ret.toString();
    }

    public final String getJagString() {
        if (getUByte() != 0)
            throw new RuntimeException();
        return getString();
    }

    public final void reset() {
        seek(0);
    }

    public final byte[] decompress() {
        reset();
        int compression = getUByte(), size = getInt();
        if (size < 0)
            throw new IndexOutOfBoundsException();
        if (compression == 0) {
            return getBytes(new byte[size]);
        } else {
            return decompress(compression);
        }
    }

    public final byte[] decompress(final int compression, final int size) {
        reset();
        if (size < 0)
            throw new IndexOutOfBoundsException();
        if (compression == 0) {
            return getBytes(new byte[size]);
        } else {
            return decompress(compression);
        }
    }

    private final byte[] decompress(int compression) {
        int decompressedSize = getInt();
        if (decompressedSize < 0)
            throw new IndexOutOfBoundsException();
        byte decompressed[] = new byte[decompressedSize];
        if (compression == 1) {
            decompressBzip2(decompressed);
        } else {
            decompressGzip(decompressed);
        }
        return decompressed;
    }

    private final void decompressBzip2(byte[] compressed) {
        byte[] payload = getAllBytes();
        BZip2Decompressor.decompress(compressed, compressed.length, payload, 0, 9);
    }

    private final void decompressGzip(byte[] compressed) {
        byte[] payload = getAllBytes();
        int location = getLocation();
        if (payload[location] != 31 || payload[location + 1] != -117) {
            throw new RuntimeException("Invalid GZIP header!");
        }
        Inflater inflater = new Inflater(true);
        try {
            inflater.setInput(payload, location + 10, getLength() - location - 18);
            inflater.inflate(compressed);
        } catch (Exception ex) {
            inflater.reset();
            throw new RuntimeException("Invalid GZIP compressed data!");
        }
        inflater.reset();
    }

    public void skip(int num) {
        assertLeft(num);
        seek(num + getLocation());
    }

    public static byte[] unwrapBuffer(Object o, boolean copy) {
        if (o instanceof byte[]) {
            byte[] ret = (byte[]) o;
            if (copy) {
                return Arrays.copyOf(ret, ret.length);
            } else {
                return ret;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] unwrapBuffer(Object o) {
        return unwrapBuffer(o, false);
    }

    public static Object wrapBuffer(byte[] b, boolean copy) {
        return copy ? Arrays.copyOf(b, b.length) : b;
    }

    public static Object wrapBuffer(byte[] b) {
        return wrapBuffer(b, false);
    }

}
