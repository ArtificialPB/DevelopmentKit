package com.artificial.cachereader.fs;

import com.artificial.cachereader.DataSource;
import com.artificial.cachereader.GameType;
import com.artificial.cachereader.wrappers.Wrapper;
import com.artificial.cachereader.wrappers.WrapperLoader;

import java.io.FileNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class CacheSystem<C extends CacheSystem> {
    private final CacheSource cache;
    protected final Map<Type, WrapperLoader<?, C>> loaderMap = new HashMap<Type, WrapperLoader<?, C>>();

    public CacheSystem(final GameType gameType) {
        try {
            this.cache = new CacheSource(new DataSource(gameType));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find cache directory!");
        }
    }

    public CacheSource getCacheSource() {
        return cache;
    }

    @SuppressWarnings("unchecked")
    public <T extends Wrapper> WrapperLoader<T, C> getLoader(Class<T> wrapperClass) {
        return (WrapperLoader<T, C>) loaderMap.get(wrapperClass);
    }

    public Collection<WrapperLoader<?, C>> getLoaders() {
        return loaderMap.values();
    }

    public <T extends Wrapper> void addLoader(WrapperLoader<T, C> loader) {
        ParameterizedType type = (ParameterizedType) loader.getClass().getGenericSuperclass();
        loaderMap.put(type.getActualTypeArguments()[0], loader);
    }
}
