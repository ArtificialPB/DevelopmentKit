package com.artificial.cachereader.wrappers.rt6.loaders;


import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.fs.FileData;
import com.artificial.cachereader.fs.RT6CacheSystem;
import com.artificial.cachereader.wrappers.rt6.ItemDefinition;

import java.util.Arrays;

public class ItemDefinitionLoader extends ProtocolWrapperLoader<ItemDefinition> {

    private static final String[] NOT_EDIBLE = {"Burnt", "Rotten", "Poison", "Fish-like thing", "Dwarven rock cake"};

    public ItemDefinitionLoader(final RT6CacheSystem cacheSystem) {
        super(cacheSystem, cacheSystem.getCacheSource().getCacheType(19));
    }

    @Override
    public ItemDefinition load(final int id) {
        FileData data = getValidFile(id);
        ItemDefinition ret = new ItemDefinition(this, id);
        final Stream stream = data.getDataAsStream();
        ret.decode(stream);
        fixItem(ret);
        return ret;
    }

    private void fixItem(final ItemDefinition item) {
        if (item.lentTemplateId != -1 && item.lentId != -1) {
            fixLentItem(item);
        }
        if (item.noteTemplateId != -1) {
            fixNotedItem(item);
        }
        if (item.cosmeticTemplateId != -1) {
            fixCosmeticItem(item);
        }
        if (item.specialAttackTeplateId != -1) {
            fixSpecialAttack(item, item.specialAttackTeplateId);
        }

        item.edible = isEdible(item);
    }

    private void fixCosmeticItem(final ItemDefinition item) {
        fixItem(item, item.cosmeticId);
        item.cosmetic = true;
    }

    private void fixLentItem(final ItemDefinition item) {
        final ItemDefinition source = fixItem(item, item.lentId);
        item.lent = true;
        item.meleeWeapon = source.meleeWeapon;
        item.rangeWeapon = source.rangeWeapon;
        item.magicWeapon = source.magicWeapon;
        item.slot = source.slot;
        item.secondSlot = source.secondSlot;
        item.twoHand = source.twoHand;
        item.adrenaline = source.adrenaline;
        item.equipable = source.equipable;
        item.specialAttack = source.specialAttack;
    }

    private ItemDefinition fixItem(final ItemDefinition item, final int sourceId) {
        final ItemDefinition source = this.load(sourceId);
        item.groundActions = source.groundActions;
        item.actions = source.actions;
        item.name = source.name;
        item.members = source.members;
        item.value = 0;
        item.team = source.team;
        item.actions[4] = "Discard";
        return source;
    }

    private void fixNotedItem(final ItemDefinition item) {
        final ItemDefinition note = this.load(item.noteId);
        item.value = note.value;
        item.name = note.name;
        item.stackable = true;
        item.members = note.members;
        item.noted = true;
    }

    private void fixSpecialAttack(final ItemDefinition item, final int sourceId) {
        final ItemDefinition source = this.load(sourceId);
        item.specialAttack = true;
        item.adrenaline = source.adrenaline;
    }

    private boolean isEdible(final ItemDefinition item) {
        if (!Arrays.asList(item.actions).contains("Eat") || item.name == null) {
            return false;
        }
        for (final String s : NOT_EDIBLE) {
            if (item.name.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
