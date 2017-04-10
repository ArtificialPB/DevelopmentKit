package com.artificial.cachereader.wrappers.rt4.loaders;

import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT4CacheSystem;
import com.artificial.cachereader.wrappers.rt4.ItemDefinition;

public class ItemDefinitionLoader extends ProtocolWrapperLoader<ItemDefinition> {
    public ItemDefinitionLoader(final RT4CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(2).getArchive(10));
    }

    @Override
    public ItemDefinition load(final int id) {
        final FileData data = getValidFile(id);
        ItemDefinition ret = new ItemDefinition(this, id);
        ret.decode(data.getDataAsStream());
        fixItem(ret);
        return ret;
    }

    private void fixItem(final ItemDefinition item) {
        if (item.noteTemplateId != -1) {
            fixNotedItem(item);
        }
        //we set shift action here in case the actions don't get loaded before the shift index is
        if (item.shiftActionIndex != -1 && item.shiftActionIndex < item.actions.length) {
            item.shiftAction = item.actions[item.shiftActionIndex];
        }
    }

    private void fixNotedItem(final ItemDefinition item) {
        final ItemDefinition note = this.load(item.noteId);
        item.value = note.value;
        item.name = note.name;
        item.stackable = true;
        item.members = note.members;
        item.noted = true;
    }

}
