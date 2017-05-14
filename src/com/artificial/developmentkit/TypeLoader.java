package com.artificial.developmentkit;

import com.artificial.cachereader.wrappers.Wrapper;

public interface TypeLoader {

    Class<? extends Wrapper> getWrapperClass();

    Class<? extends Wrapper> getScriptClass();

    enum RT6 implements TypeLoader {
        ITEMS(com.artificial.cachereader.wrappers.rt6.ItemDefinition.class),
        NPCS(com.artificial.cachereader.wrappers.rt6.NpcDefinition.class),
        OBJECTS(com.artificial.cachereader.wrappers.rt6.ObjectDefinition.class),
        QUESTS(com.artificial.cachereader.wrappers.rt6.QuestDefinition.class);
        private final Class<? extends Wrapper> wrapperClass;

        RT6(final Class<? extends Wrapper> wrapperClass) {
            this.wrapperClass = wrapperClass;
        }

        @Override
        public Class<? extends Wrapper> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public Class<? extends Wrapper> getScriptClass() {
            return com.artificial.cachereader.wrappers.rt6.Script.class;
        }
    }

    enum RT4 implements TypeLoader {
        ITEMS(com.artificial.cachereader.wrappers.rt4.ItemDefinition.class),
        NPCS(com.artificial.cachereader.wrappers.rt4.NpcDefinition.class),
        OBJECTS(com.artificial.cachereader.wrappers.rt4.ObjectDefinition.class);
        private final Class<? extends Wrapper> wrapperClass;

        RT4(final Class<? extends Wrapper> wrapperClass) {
            this.wrapperClass = wrapperClass;
        }

        @Override
        public Class<? extends Wrapper> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public Class<? extends Wrapper> getScriptClass() {
            return com.artificial.cachereader.wrappers.rt4.Script.class;
        }
    }
}
