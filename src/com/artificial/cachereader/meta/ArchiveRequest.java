package com.artificial.cachereader.meta;


import com.artificial.cachereader.Sector;

public class ArchiveRequest {

    private final int cacheType;
    private final int archiveId;
    private final int startSector;
    private final int fileSize;

    public ArchiveRequest(int cacheType, int archiveId, int archiveSize, int startSector) {
        this.cacheType = cacheType;
        this.archiveId = archiveId;
        this.fileSize = archiveSize;
        this.startSector = startSector;
    }

    public int getCacheType() {
        return cacheType;
    }

    public int getArchiveId() {
        return archiveId;
    }

    public int getStartSector() {
        return startSector;
    }

    public int getFileSize() {
        return fileSize;
    }

    public boolean checkSector(Sector sector) {
        return sector.getFileId() == this.getArchiveId() && sector.getCacheType() == this.getCacheType();
    }

}
