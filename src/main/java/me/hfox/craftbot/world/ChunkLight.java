package me.hfox.craftbot.world;

public class ChunkLight {

    private final byte[] data;

    public ChunkLight() {
        data = new byte[2048];
    }

    public int getLight(int x, int y, int z) {
        return getLight(getPosition(x, y, z));
    }

    public void setLight(int x, int y, int z, int l) {
        setLight(getPosition(x, y, z), l);
    }

    private int getPosition(int x, int y, int z) {
        return x << 8 | y << 4 | z;
    }

    public int getLight(int position) {
        int index = shift(position);
        return isMostSignificantNibble(position) ? data[index] & 15 : data[index] >> 4 & 15;
    }

    public void setLight(int position, int light) {
        int index = shift(position);
        if (isMostSignificantNibble(position)) {
            data[index] = (byte) (data[index] & 240 | light & 15);
        } else {
            data[index] = (byte) (data[index] & 15 | (light & 15) << 4);
        }

    }

    private boolean isMostSignificantNibble(int index) {
        return (index & 1) == 0;
    }

    private int shift(int i) {
        return i >> 1;
    }

    public byte[] getData() {
        return data;
    }
    
}
