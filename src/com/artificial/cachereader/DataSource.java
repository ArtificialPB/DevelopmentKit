package com.artificial.cachereader;

import com.artificial.cachereader.datastream.ByteStream;
import com.artificial.cachereader.datastream.Stream;
import com.artificial.cachereader.meta.ArchiveRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {

    private static final String FILE_MODE = "r";

    public static final int MAX_INDEX_FILES = 255;
    public static final int META_INDEX_FILE_NUM = 255;

    private static final String FILE_BASE = "main_file_cache";
    private static final String CACHE_FILE = FILE_BASE + ".dat2";
    private static final String INDEX_FILE_BASE = FILE_BASE + ".idx";
    private static final String META_INDEX_FILE = INDEX_FILE_BASE + META_INDEX_FILE_NUM;

    private static final int SECTOR_SIZE = 520;

    private final File cacheDirectory;

    private final int indexFileCount;
    private final File cacheFile, indexFiles[], metaIndexFile;
    private FileChannel cacheChannel, indexChannels[];

    private GameType gameType = GameType.RT6;

    public DataSource(final GameType gameType) throws FileNotFoundException {
        this.gameType = gameType;
        this.cacheDirectory = getDefaultCacheDirectory(gameType);
        this.cacheFile = new File(this.cacheDirectory, CACHE_FILE);
        this.metaIndexFile = new File(this.cacheDirectory, META_INDEX_FILE);
        File[] indexFileSource = findIndexFiles();
        this.indexFileCount = indexFileSource.length;
        this.indexFiles = Arrays.copyOf(indexFileSource, META_INDEX_FILE_NUM + 1);
        if (!validateFiles()) {
            throw new FileNotFoundException("All the required cache files were not found in " + this.cacheDirectory);
        }
        this.indexFiles[META_INDEX_FILE_NUM] = metaIndexFile;
        this.indexChannels = new FileChannel[META_INDEX_FILE_NUM + 1];
    }

    public static File getDefaultCacheDirectory(final GameType gameType) {
        String rootDirectory = System.getProperty("user.home");
        if (rootDirectory != null) {
            rootDirectory = rootDirectory + "/";
        }
        if (rootDirectory == null) {
            if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                rootDirectory = System.getenv("USERPROFILE");
            } else {
                rootDirectory = System.getenv("HOME");
            }
        }
        if (rootDirectory != null) {
            rootDirectory = rootDirectory + "/";
        }
        if (rootDirectory == null) {
            rootDirectory = "~/";
        }
        return new File(rootDirectory + File.separatorChar + "jagexcache" + File.separatorChar + gameType.getFolderName()
                + File.separatorChar + "LIVE" + File.separatorChar);
    }

    public GameType getGameType() {
        return gameType;
    }

    public FileChannel getCacheChannel() {
        return cacheChannel = refreshBrokenChannel(cacheFile, cacheChannel);
    }

    public FileChannel getMetaIndexChannel() {
        return getIndexChannel(META_INDEX_FILE_NUM);
    }

    public FileChannel getIndexChannel(int type) {
        if (validateIndexChannel(type))
            return indexChannels[type] = refreshBrokenChannel(indexFiles[type], indexChannels[type]);
        else
            return null;
    }

    private boolean validateIndexChannel(int type) {
        return 0 <= type && type < indexFiles.length && indexFiles[type] != null;
    }

    private FileChannel refreshBrokenChannel(File location, FileChannel currentChannel) {
        synchronized (location) {
            if (currentChannel == null || !currentChannel.isOpen()) {
                currentChannel = openChannel(location);
            }
            return currentChannel;
        }
    }

    private FileChannel openChannel(File location) {
        try {
            return new RandomAccessFile(location, FILE_MODE).getChannel();
        } catch (FileNotFoundException impossible) {
            impossible.printStackTrace();
            throw new RuntimeException();
        }
    }

    public byte[] readArchive(ArchiveRequest query) {
        int currentChunk = 0;
        int nextSectorId = query.getStartSector();
        ByteBuffer outputBuffer = ByteBuffer.allocate(query.getFileSize());
        while (nextSectorId != 0) {
            Sector sector;
            try {
                sector = readSector(query.getArchiveId(), nextSectorId);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            if (currentChunk++ != sector.getFileChunk())
                throw new RuntimeException();
            if (!query.checkSector(sector))
                throw new RuntimeException();
            outputBuffer.put(sector.getData(), sector.getDataOffset(),
                    Math.min(sector.getData().length - sector.getDataOffset(), outputBuffer.remaining()));
            nextSectorId = sector.getNextSector();
        }
        if (outputBuffer.remaining() != 0)
            throw new RuntimeException();
        return outputBuffer.array();
    }

    public Sector readSector(int prevFileId, int sectorId) throws IOException {
        byte[] fullData = readSectorData(sectorId);
        Stream infoStream = new ByteStream(fullData);

        int fileId = prevFileId < Stream.SHORT_MASK ? infoStream.getUShort() : infoStream.getInt();
        int fileChunk = infoStream.getUShort();
        int nextSector = infoStream.getUInt24();
        int cacheType = infoStream.getUByte();

        return new Sector(sectorId, cacheType, fileId, fileChunk, nextSector, infoStream.getAllBytes(),
                infoStream.getLocation());
    }

    private byte[] readSectorData(int sector) throws IOException {
        FileChannel channel = getCacheChannel();
        if (!validateSector(channel, sector))
            throw new IndexOutOfBoundsException();

        long startPosition = (long) sector * SECTOR_SIZE;
        ByteBuffer dataBuffer = ByteBuffer.allocate(SECTOR_SIZE);
        channel.read(dataBuffer, startPosition);
        return dataBuffer.array();
    }

    private File[] findIndexFiles() {
        List<File> indexFiles = new ArrayList<File>(40);
        for (int i = 0; i < MAX_INDEX_FILES; ++i) {
            File currentIndex = new File(this.cacheDirectory, INDEX_FILE_BASE + i);
            if (currentIndex.exists())
                indexFiles.add(currentIndex);
            else
                break;
        }
        return indexFiles.toArray(new File[indexFiles.size()]);
    }

    private boolean validateFiles() {
        return cacheFile.exists() && metaIndexFile.exists() && indexFileCount > 0;
    }

    public boolean validateType(int type) {
        return 0 <= type && type < indexFileCount;
    }

    private boolean validateSector(FileChannel channel, int sector) throws IOException {
        if (sector < 0)
            return false;
        long endPosition = (sector + 1) * SECTOR_SIZE;
        return endPosition <= channel.size();
    }

    public int getIndexFileCount() {
        return indexFileCount;
    }
}
