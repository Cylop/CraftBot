package me.hfox.craftbot.network.packet.play.server;

import com.google.common.collect.Lists;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketPlayOutEntityProperties extends PacketPlayOutEntity {

    private EntityProperty[] properties;

    public PacketPlayOutEntityProperties(int entityId, EntityProperty[] properties) {
        super(entityId);
        this.properties = properties;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.properties = new EntityProperty[stream.readInt()];
        for(int i = 0; i < properties.length; i++) {
            String name = stream.readString();
            double value = stream.readDouble();
            EntityProperty property = new EntityProperty(name, value);
            properties[i] = property;

            int modifiers = stream.readVarInt();
            for(int j = 0; j < modifiers; j++) {
                UUID uuid = stream.readUUID();
                double amount = stream.readDouble();
                int operation = stream.readByte();
                property.addModifier(uuid, amount, operation);
            }
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeInt(properties.length);
        for (EntityProperty property : properties) {
            stream.writeString(property.getName());
            stream.writeDouble(property.getValue());

            stream.writeVarInt(property.getModifiers().size());
            for (Modifier modifier : property.getModifiers()) {
                stream.writeUUID(modifier.getUUID());
                stream.writeDouble(modifier.getAmount());
                stream.writeByte(modifier.getOperation());
            }
        }
    }

    public static final class EntityProperty {

        private final String name;
        private final double value;
        private final List<Modifier> modifiers;

        private EntityProperty(String name, double value) {
            this.name = name;
            this.value = value;
            this.modifiers = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public double getValue() {
            return value;
        }

        public List<Modifier> getModifiers() {
            return Lists.newArrayList(modifiers);
        }

        private void addModifier(UUID uuid, double amount, int operation) {
            modifiers.add(new Modifier(uuid, amount, operation));
        }

    }

    public static class Modifier {

        private final UUID uuid;
        private final double amount;
        private final int operation;

        private Modifier(UUID uuid, double amount, int operation) {
            this.uuid = uuid;
            this.amount = amount;
            this.operation = operation;
        }

        public UUID getUUID() {
            return uuid;
        }

        public double getAmount() {
            return amount;
        }

        public int getOperation() {
            return operation;
        }

    }

}
