package me.hfox.craftbot.inventory;

import me.hfox.craftbot.file.nbt.NBTTagCompound;

public class ItemStack {

    private int id;
    private short data;
    private int amount;
    private NBTTagCompound nbt;

    public ItemStack(int id, short data, int amount) {
        this.id = id;
        this.data = data;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public short getData() {
        return data;
    }

    public int getAmount() {
        return amount;
    }

    public NBTTagCompound getNBT() {
        return nbt;
    }

    public void setNBT(NBTTagCompound tag) {
        this.nbt = tag;
    }

}
