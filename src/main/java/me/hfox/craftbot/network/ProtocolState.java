package me.hfox.craftbot.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.hfox.craftbot.network.listeners.HandshakeListener;
import me.hfox.craftbot.network.listeners.LoginListener;
import me.hfox.craftbot.network.listeners.PacketListener;
import me.hfox.craftbot.network.listeners.PlayListener;
import me.hfox.craftbot.network.listeners.StatusListener;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.handshake.client.PacketHandshakeInHandshake;
import me.hfox.craftbot.network.packet.login.client.PacketLoginInEncryptionResponse;
import me.hfox.craftbot.network.packet.login.client.PacketLoginInStart;
import me.hfox.craftbot.network.packet.login.server.PacketLoginOutDisconnect;
import me.hfox.craftbot.network.packet.login.server.PacketLoginOutEncryptionRequest;
import me.hfox.craftbot.network.packet.login.server.PacketLoginOutSuccess;
import me.hfox.craftbot.network.packet.play.both.PacketPlayPlayerAbilities;
import me.hfox.craftbot.network.packet.play.client.*;
import me.hfox.craftbot.network.packet.play.server.*;
import me.hfox.craftbot.network.packet.status.client.PacketStatusInPing;
import me.hfox.craftbot.network.packet.status.client.PacketStatusInRequest;
import me.hfox.craftbot.network.packet.status.server.PacketStatusOutPong;
import me.hfox.craftbot.network.packet.status.server.PacketStatusOutResponse;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public enum ProtocolState {

    HANDSHAKE(-1) {
        @Override
        public HandshakeListener create(NetworkClient network) {
            return new HandshakeListener(network);
        }

        @Override
        public void register() {
            register(ProtocolDirection.SERVERBOUND, PacketHandshakeInHandshake.class); // -> 00
        }
    },
    PLAY(0) {
        @Override
        public PlayListener create(NetworkClient network) {
            return new PlayListener(network);
        }

        @Override
        public void register() {
            register(ProtocolDirection.SERVERBOUND, PacketPlayInKeepAlive.class); // -> 00
            register(ProtocolDirection.SERVERBOUND, PacketPlayInChat.class); // -> 01
            register(ProtocolDirection.SERVERBOUND, PacketPlayInUseEntity.class); // -> 02
            register(ProtocolDirection.SERVERBOUND, PacketPlayInPlayer.class); // -> 03
            register(ProtocolDirection.SERVERBOUND, PacketPlayInPlayerPosition.class); // -> 04
            register(ProtocolDirection.SERVERBOUND, PacketPlayInPlayerLook.class); // -> 05
            register(ProtocolDirection.SERVERBOUND, PacketPlayInPlayerPositionLook.class); // -> 06
            register(ProtocolDirection.SERVERBOUND, PacketPlayInBlockDig.class); // -> 07
            register(ProtocolDirection.SERVERBOUND, PacketPlayInBlockPlace.class); // -> 08
            register(ProtocolDirection.SERVERBOUND, PacketPlayInSlotChange.class); // -> 09
            register(ProtocolDirection.SERVERBOUND, PacketPlayInAnimation.class); // -> 10 (0A)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInEntityAction.class); // -> 11 (0B)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInSteerVehicle.class); // -> 12 (0C)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInCloseWindow.class); // -> 13 (0D)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInClickWindow.class); // -> 14 (0E)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInConfirmTransaction.class); // -> 15 (0F)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInInventoryAction.class); // -> 16 (10)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInEnchantItem.class); // -> 17 (11)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInUpdateSign.class); // -> 18 (12)
            register(ProtocolDirection.SERVERBOUND, PacketPlayPlayerAbilities.class); // -> 19 (13)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInTabComplete.class); // -> 20 (14)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInClientSettings.class); // -> 21 (15)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInClientStatus.class); // -> 22 (16)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInPluginMessage.class); // -> 23 (17)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInSpectate.class); // -> 24 (18)
            register(ProtocolDirection.SERVERBOUND, PacketPlayInResourcePackStatus.class); // -> 25 (19)

            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutKeepAlive.class); // <- 00
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutJoinGame.class); // <- 01
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutChatMessage.class); // <- 02
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutTimeUpdate.class); // <- 03
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityEquipment.class); // <- 04
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnPosition.class); // <- 05
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutUpdateHealth.class); // <- 06
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutRespawn.class); // <- 07
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutPlayerPositionLook.class); // <- 08
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSlotChange.class); // <- 09
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutUseBed.class); // <- 10 (0A)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutAnimation.class); // <- 11 (0B)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnPlayer.class); // <- 12 (0C)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutCollectItem.class); // <- 13 (0D)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnObject.class); // <- 14 (0E)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnMob.class); // <- 15 (0F)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnPainting.class); // <- 16 (10)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSpawnExperienceOrb.class); // <- 17 (11)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityVelocity.class); // <- 18 (12)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutDestroyEntities.class); // <- 19 (13)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntity.class); // <- 20 (14)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityRelativeMove.class); // <- 21 (15)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityLook.class); // <- 22 (16)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityLookRelativeMove.class); // <- 23 (17)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityTeleport.class); // <- 24 (18)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityHeadLook.class); // <- 25 (19)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityStatus.class); // <- 26 (1A)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityAttach.class); // <- 27 (1B)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityMetadata.class); // <- 28 (1C)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityEffect.class); // <- 29 (1D)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityRemoveEffect.class); // <- 30 (1E)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSetExperience.class); // <- 31 (1F)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEntityProperties.class); // <- 32 (20)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutChunkData.class); // <- 33 (21)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutMultiBlockChange.class); // <- 34 (22)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutBlockChange.class); // <- 35 (23)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutBlockAction.class); // <- 36 (24)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutBlockBreakAnimation.class); // <- 37 (25)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutChunkBulk.class); // <- 38 (26)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutExplosion.class); // <- 39 (27)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutEffect.class); // <- 40 (28)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayOutSoundEffect.class); // <- 41 (29)
            increment(ProtocolDirection.CLIENTBOUND); // Particle <- 42 (2A)
            increment(ProtocolDirection.CLIENTBOUND); // Change Game State <- 43 (2B)
            increment(ProtocolDirection.CLIENTBOUND); // Spawn Global Entity <- 44 (2C)
            increment(ProtocolDirection.CLIENTBOUND); // Open Window <- 45 (2D)
            increment(ProtocolDirection.CLIENTBOUND); // Close Window <- 46 (2E)
            increment(ProtocolDirection.CLIENTBOUND); // Set Slot <- 47 (2F)
            increment(ProtocolDirection.CLIENTBOUND); // Window Items <- 48 (30)
            increment(ProtocolDirection.CLIENTBOUND); // Window Property <- 49 (31)
            increment(ProtocolDirection.CLIENTBOUND); // Confirm Transaction <- 50 (32)
            increment(ProtocolDirection.CLIENTBOUND); // Update Sign <- 51 (33)
            increment(ProtocolDirection.CLIENTBOUND); // Maps <- 52 (34)
            increment(ProtocolDirection.CLIENTBOUND); // Update Block Entity <- 53 (35)
            increment(ProtocolDirection.CLIENTBOUND); // Sign Editor Open <- 54 (36)
            increment(ProtocolDirection.CLIENTBOUND); // Statistics <- 55 (37)
            increment(ProtocolDirection.CLIENTBOUND); // Player List Item <- 56 (38)
            register(ProtocolDirection.CLIENTBOUND, PacketPlayPlayerAbilities.class); // <- 57 (39)
            increment(ProtocolDirection.CLIENTBOUND); // Tab Complete <- 58 (3A)
            increment(ProtocolDirection.CLIENTBOUND); // Scoreboard Objective <- 59 (3B)
            increment(ProtocolDirection.CLIENTBOUND); // Update Score <- 60 (3C)
            increment(ProtocolDirection.CLIENTBOUND); // Display Scoreboard <- 61 (3D)
            increment(ProtocolDirection.CLIENTBOUND); // Teams <- 62 (3E)
            increment(ProtocolDirection.CLIENTBOUND); // Plugin Message <- 63 (3F)
            increment(ProtocolDirection.CLIENTBOUND); // Server Difficulty <- 64 (40)
            increment(ProtocolDirection.CLIENTBOUND); // Disconnect <- 65 (41)
            increment(ProtocolDirection.CLIENTBOUND); // Combat Event <- 66 (42)
            increment(ProtocolDirection.CLIENTBOUND); // Camera <- 67 (43)
            increment(ProtocolDirection.CLIENTBOUND); // World Border <- 68 (44)
            increment(ProtocolDirection.CLIENTBOUND); // Title <- 69 (45)
            increment(ProtocolDirection.CLIENTBOUND); // Set Compression <- 70 (46)
            increment(ProtocolDirection.CLIENTBOUND); // Player List Header/Footer <- 71 (47)
            increment(ProtocolDirection.CLIENTBOUND); // Resource Packet Send <- 72 (48)
            increment(ProtocolDirection.CLIENTBOUND); // Update Entity NBT <- 73 (49)
        }
    },
    STATUS(1) {
        @Override
        public StatusListener create(NetworkClient network) {
            return new StatusListener(network);
        }

        @Override
        public void register() {
            register(ProtocolDirection.SERVERBOUND, PacketStatusInRequest.class); // -> 00
            register(ProtocolDirection.SERVERBOUND, PacketStatusInPing.class); // -> 01

            register(ProtocolDirection.CLIENTBOUND, PacketStatusOutResponse.class); // <- 00
            register(ProtocolDirection.CLIENTBOUND, PacketStatusOutPong.class); // <- 01
        }
    },
    LOGIN(2) {
        @Override
        public LoginListener create(NetworkClient network) {
            return new LoginListener(network);
        }

        @Override
        public void register() {
            register(ProtocolDirection.SERVERBOUND, PacketLoginInStart.class); // -> 00
            register(ProtocolDirection.SERVERBOUND, PacketLoginInEncryptionResponse.class); // -> 01

            register(ProtocolDirection.CLIENTBOUND, PacketLoginOutDisconnect.class); // <- 00
            register(ProtocolDirection.CLIENTBOUND, PacketLoginOutEncryptionRequest.class); // <- 01
            register(ProtocolDirection.CLIENTBOUND, PacketLoginOutSuccess.class); // <- 02
        }
    };

    private int id;
    private Map<ProtocolDirection, Integer> counts;
    private Map<ProtocolDirection, BiMap<Integer, Class<? extends Packet>>> packets;

    ProtocolState(int id) {
        this.counts = new HashMap<>();
        this.packets = new HashMap<>();
        for (ProtocolDirection direction : ProtocolDirection.values()) {
            counts.put(direction, 0);
            packets.put(direction, HashBiMap.create());
        }

        this.id = id;
        register();
    }

    public int getId() {
        return id;
    }

    public Map<ProtocolDirection, BiMap<Integer, Class<? extends Packet>>> getPackets() {
        return packets;
    }

    public abstract PacketListener create(NetworkClient network);

    public abstract void register();

    synchronized void register(ProtocolDirection direction, Class<? extends Packet> packet) {
        if (packets.get(direction) == null) {
            packets.put(direction, HashBiMap.create());
        }

        int count = counts.get(direction);
        packets.get(direction).put(count, packet);
        increment(direction);
    }

    void increment(ProtocolDirection direction) {
        int count = counts.get(direction);
        counts.put(direction, count + 1);
    }

    public Integer getPacket(Packet packet) {
        return getPacket(ProtocolDirection.CLIENTBOUND, packet);
    }

    public Integer getPacket(Class<? extends Packet> packet) {
        return getPacket(ProtocolDirection.CLIENTBOUND, packet);
    }

    public Integer getPacket(ProtocolDirection direction, Packet packet) {
        return getPacket(direction, packet.getClass());
    }

    public Integer getPacket(ProtocolDirection direction, Class<? extends Packet> packet) {
        return packets.get(direction).inverse().get(packet);
    }

    public Class<? extends Packet> getPacket(int id) {
        return getPacket(ProtocolDirection.SERVERBOUND, id);
    }

    public Class<? extends Packet> getPacket(ProtocolDirection direction, int id) {
        return packets.get(direction).get(id);
    }

    public static Packet build(Class<? extends Packet> cls) {
        final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();
        final Constructor constructor;
        try {
            constructor = reflection.newConstructorForSerialization(cls, Object.class.getDeclaredConstructor());
            return (Packet) constructor.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ProtocolState findById(int id) {
        for (ProtocolState state : values()) {
            if (state.id == id) {
                return state;
            }
        }

        throw new RuntimeException("Could not find a state matching the id #" + id);
    }

}
