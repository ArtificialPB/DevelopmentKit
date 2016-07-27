package com.artificial.cachereader.fs;


import com.artificial.cachereader.GameType;
import com.artificial.cachereader.wrappers.rt4.loaders.ItemDefinitionLoader;
import com.artificial.cachereader.wrappers.rt4.loaders.NpcDefinitionLoader;
import com.artificial.cachereader.wrappers.rt4.loaders.ObjectDefinitionLoader;
import com.artificial.cachereader.wrappers.rt4.loaders.ScriptLoader;

public class RT4CacheSystem extends CacheSystem<RT4CacheSystem> {

    public final ItemDefinitionLoader itemLoader;
    public final ObjectDefinitionLoader objectLoader;
    public final NpcDefinitionLoader npcLoader;
    public final ScriptLoader scriptLoader;

    public RT4CacheSystem() {
        super(GameType.RT4);
        addLoader(scriptLoader = new ScriptLoader(this));
        addLoader(itemLoader = new ItemDefinitionLoader(this));
        addLoader(objectLoader = new ObjectDefinitionLoader(this));
        addLoader(npcLoader = new NpcDefinitionLoader(this));
    }
}
