package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.entity.StatusEffect;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityEffect extends PacketPlayOutEntity {

    private StatusEffect effect;
    private byte amplifier;
    private int duration;
    private boolean particles;

    public PacketPlayOutEntityEffect(int entityId, StatusEffect effect, byte amplifier, int duration, boolean particles) {
        super(entityId);
        this.effect = effect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.particles = particles;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isParticles() {
        return particles;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.effect = StatusEffect.getById(stream.readByte());
        this.amplifier = stream.readByte();
        this.duration = stream.readVarInt();
        this.particles = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeByte(effect.getId());
        stream.writeByte(amplifier);
        stream.writeVarInt(duration);
        stream.writeBoolean(particles);
    }

}
