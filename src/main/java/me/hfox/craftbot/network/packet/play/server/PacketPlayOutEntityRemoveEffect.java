package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.entity.StatusEffect;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityRemoveEffect extends PacketPlayOutEntity {

    private StatusEffect effect;

    public PacketPlayOutEntityRemoveEffect(int entityId, StatusEffect effect) {
        super(entityId);
        this.effect = effect;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);
        this.effect = StatusEffect.getById(stream.readByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);
        stream.writeByte(effect.getId());
    }

}
