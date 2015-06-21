package me.hfox.craftbot.world;

public class ChunkSection {

    private static final int SECTION_SIZE = 16;
    private static final int SECTION_INDEXES = SECTION_SIZE * SECTION_SIZE * SECTION_SIZE;

    private int y;
    private char[] blockIds;
    private Block[] blocks;
    private ChunkLight emittedLight;
    private ChunkLight skyLight;

    private int nonEmptyBlocks;
    private int tickingBlocks;

    public ChunkSection(int y) {
        this.y = y;
        this.blockIds = new char[SECTION_INDEXES];
        this.blocks = new Block[SECTION_INDEXES];
        this.emittedLight = new ChunkLight();
        this.skyLight = new ChunkLight();
        checkEmpty();
    }

    public Block getBlock(int x, int y, int z) {
        int pos = x << 8 | y << 4 | z;
        return blocks[pos];
    }

    public int getY() {
        return y;
    }

    public char[] getBlockIds() {
        return blockIds;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public ChunkLight getEmittedLight() {
        return emittedLight;
    }

    public ChunkLight getSkyLight() {
        return skyLight;
    }

    public void checkEmpty() {
        nonEmptyBlocks = 0;
        tickingBlocks = 0;
        for (int x = 0; x < SECTION_SIZE; ++x) {
            for (int y = 0; y < SECTION_SIZE; ++y) {
                for (int z = 0; z < SECTION_SIZE; ++z) {
                    Block block = getBlock(x, y, z);

                    if (block != null && block.getType() != 0) {
                        nonEmptyBlocks++;
                        // if (block.isTicking()) {
                        //     tickingBlocks++;
                        // }
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return nonEmptyBlocks == 0;
    }

}
