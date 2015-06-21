package me.hfox.craftbot.world;

public class Block {

    private int type;
    private short data;

    public Block(int type, short data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public short getData() {
        return data;
    }

}
