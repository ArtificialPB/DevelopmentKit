package com.artificial.cachereader.fs;

import com.artificial.cachereader.GameType;
import com.artificial.cachereader.wrappers.rt6.loaders.*;

public class RT6CacheSystem extends CacheSystem<RT6CacheSystem> {

    public final ItemDefinitionLoader itemLoader;
    public final ObjectDefinitionLoader objectLoader;
    public final ScriptLoader scriptLoader;
    public final QuestDefinitionLoader questLoader;
    public final NpcDefinitionLoader npcLoader;

    public RT6CacheSystem() {
        super(GameType.RT6);
        addLoader(itemLoader = new ItemDefinitionLoader(this));
        addLoader(objectLoader = new ObjectDefinitionLoader(this));
        addLoader(scriptLoader = new ScriptLoader(this));
        addLoader(questLoader = new QuestDefinitionLoader(this));
        addLoader(npcLoader = new NpcDefinitionLoader(this));
    }

}
