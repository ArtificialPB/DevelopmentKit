package com.artificial.cachereader.wrappers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Wrapper<W extends WrapperLoader<?, ?>> {
    public static boolean DEBUG = false;

    protected final W loader;
    protected final int id;

    public Wrapper(final W loader, final int id) {
        this.loader = loader;
        this.id = id;
    }

    public int id() {
        return id;
    }

    public W getLoader() {
        return loader;
    }

    public Map<String, Object> getDeclaredFields() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>();
        ret.put("id", id());
        for (Field f : getClass().getDeclaredFields()) {
            Object o;
            try {
                o = f.get(this);
            } catch (Exception e) {
                o = null;
            }
            if (Modifier.isStatic(f.getModifiers())) continue;

            if (f.getType().isArray()) {
                StringBuilder builder = new StringBuilder();
                appendObject(builder, o);
                ret.put(f.getName(), builder.toString());
            } else {
                ret.put(f.getName(), o == null ? "null" : o);
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(getClass().getSimpleName());
        output.append(" ");
        output.append(id());
        output.append(" {");
        for (Field f : getClass().getDeclaredFields()) {
            if ((f.getModifiers() & (Modifier.STATIC | Modifier.FINAL)) != 0)
                continue;
            Object o;
            try {
                o = f.get(this);
            } catch (Exception e) {
                continue;
            }
            if (o == null)
                continue;
            output.append(f.getName());
            output.append(": ");
            appendObject(output, o);
            output.append(", ");
        }
        output.delete(output.length() - 2, output.length());
        output.append("}");
        return output.toString();
    }

    protected void skipValue(final int opcode, final Object... os) {
        StringBuilder builder = new StringBuilder();
        builder.append(opcode).append(", ");
        appendObject(builder, os != null && os.length == 1 ? os[0] : os);
        if (DEBUG) {
            System.out.println("[" + this.getClass().getSimpleName() + "] Skip -> " + builder.toString());
        }
    }

    private void appendObject(final StringBuilder output, final Object o) {
        if (o == null) {
            output.append("null");
        } else if (o instanceof Long) {
            output.append(((Long) o).longValue());
        } else if (o instanceof Double) {
            output.append(o);
        } else if (o instanceof Float) {
            output.append(o);
        } else if (o instanceof Number) {
            output.append(((Number) o).intValue());
        } else if (o instanceof Boolean) {
            output.append((Boolean) o ? "T" : "F");
        } else if (o.getClass().isArray()) {
            output.append("[");
            for (int i = 0, len = Array.getLength(o); i < len; ++i) {
                if (i != 0)
                    output.append(", ");
                appendObject(output, Array.get(o, i));
            }
            output.append("]");
        } else if (o instanceof Collection<?>) {
            output.append("[");
            boolean first = true;
            for (Object child : (Collection<?>) o) {
                if (first & !(first = false))
                    output.append(", ");
                appendObject(output, child);
            }
            output.append("]");
        } else {
            output.append(o);
        }
    }
}
