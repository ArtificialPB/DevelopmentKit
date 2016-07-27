package com.artificial.cachereader;

public enum GameType {
    RT6("runescape"),
    RT4("oldschool");

    private final String folderName;

    GameType(final String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
