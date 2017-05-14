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
package com.artificial.cachereader.wrappers.rt4.loaders;

import com.artificial.cachereader.fs.Archive;
import com.artificial.cachereader.fs.CacheType;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.meta.ArchiveMeta;
import com.artificial.cachereader.meta.FileMeta;
import com.artificial.cachereader.wrappers.rt4.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//https://github.com/runelite/runelite/tree/master/cache/src/main/java/net/runelite/cache
public class ComponentLoader extends WrapperLoader<Component> {
    private CacheType cache;

    public ComponentLoader(RT4CacheSystem cacheSystem) {
        super(cacheSystem);
        this.cache = cacheSystem.getCacheSource().getCacheType(3);
    }

    @Override
    public Component load(final int id) {
        final FileData data = getValidFile(id);
        final Component ret = new Component(this, id);
        ret.decode(data.getDataAsStream());
        return ret;
    }

    protected FileData getValidFile(final int id) {
        FileData ret = getFile(id);
        if (ret == null)
            throw new IllegalArgumentException("Bad id");
        return ret;
    }

    private FileData getFile(final int id) {
        Archive archive = cache.getArchive(id >> 16);
        if (archive == null)
            return null;
        return archive.getFile(id & 0xffff);
    }

    public List<Component> loadAll() {
        final List<Component> r = new LinkedList<>();
        for (Map.Entry<Integer, ArchiveMeta> a : cache.getTable().getEntries().entrySet()) {
            final ArchiveMeta meta = a.getValue();
            final int archiveId = meta.getId();
            final Archive archive = cache.getArchive(archiveId);
            for (Map.Entry<Integer, FileMeta> b : meta.getChildren().entrySet()) {
                final int fileId = b.getValue().getId();
                final FileData data = archive.getFile(fileId);
                final int componentId = (archiveId << 16) + fileId;
                final Component ret = new Component(this, componentId);
                ret.decode(data.getDataAsStream());
                r.add(ret);
            }
        }
        return r;
    }

    @Override
    public boolean canLoad(final int id) {
        return getFile(id) != null;
    }
}
