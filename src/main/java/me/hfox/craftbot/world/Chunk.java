package me.hfox.craftbot.world;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Chunk {

    public static final String EXTENSION = ".lxc";

    private File file;
    private int x;
    private int z;
    private ChunkSection[] sections;
    private byte[] biomeIndex;
    private Map<Integer, Map<Integer, Map<Integer, Block>>> blocks;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public ChunkSection[] getSections() {
        return sections;
    }

    public byte[] getBiomeIndex() {
        return biomeIndex;
    }

    public void save() throws IOException {
        boolean mkfile = file.createNewFile();
        if (!mkfile) {
            throw new IOException("Could not create data file for world");
        }

        FileOutputStream fos = new FileOutputStream(file);
        PacketOutputStream stream = new PacketOutputStream(fos);
        stream.writeVarInt(blocks.size());
        for (Entry<Integer, Map<Integer, Map<Integer, Block>>> x : blocks.entrySet()) {
            stream.writeVarInt(x.getKey());
            stream.writeVarInt(x.getValue().size());
            for (Entry<Integer, Map<Integer, Block>> z : x.getValue().entrySet()) {
                stream.writeVarInt(z.getKey());
                stream.writeVarInt(z.getValue().size());
                for (Entry<Integer, Block> y : z.getValue().entrySet()) {
                    stream.writeVarInt(y.getKey());
                    stream.writeVarInt(y.getValue().getType());
                    stream.writeShort(y.getValue().getData());
                }
            }
        }

        fos.close();
    }

    public void create() throws IOException {
        Block block = new Block(1, (short) 0);
        Map<Integer, Block> yBlocks = new HashMap<>();
        yBlocks.put(60, block);

        Map<Integer, Map<Integer, Block>> zBlocks = new HashMap<>();
        zBlocks.put(0, yBlocks);

        blocks.put(0, zBlocks);
        save();
    }

    public void parse() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        PacketInputStream stream = new PacketInputStream(fis);

        int xSize = stream.readVarInt();
        for (int i = 0; i < xSize; i++) {
            int x = stream.readVarInt();
            Map<Integer, Map<Integer, Block>> xBlocks = new HashMap<>();
            blocks.put(x, xBlocks);

            int zSize = stream.readVarInt();
            for (int j = 0; j < zSize; j++) {
                int z = stream.readVarInt();
                Map<Integer, Block> zBlocks = new HashMap<>();
                xBlocks.put(z, zBlocks);

                int ySize = stream.readVarInt();
                for (int k = 0; k < ySize; k++) {
                    int y = stream.readVarInt();
                    int type = stream.readVarInt();
                    short data = stream.readShort();
                    Block block = new Block(type, data);
                    zBlocks.put(y, block);
                }
            }
        }

        fis.close();
    }

}
