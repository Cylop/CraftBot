package me.hfox.craftbot.metadata;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.hfox.craftbot.metadata.types.MetadataByte;
import me.hfox.craftbot.metadata.types.MetadataFloat;
import me.hfox.craftbot.metadata.types.MetadataInt;
import me.hfox.craftbot.metadata.types.MetadataLocation;
import me.hfox.craftbot.metadata.types.MetadataRotation;
import me.hfox.craftbot.metadata.types.MetadataShort;
import me.hfox.craftbot.metadata.types.MetadataSlot;
import me.hfox.craftbot.metadata.types.MetadataString;
import me.hfox.craftbot.network.stream.PacketInputStream;

import java.io.IOException;

public enum MetadataType {

    BYTE {
        @Override
        public MetadataByte getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataByte(id, type, stream);
        }
    },
    SHORT {
        @Override
        public MetadataShort getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataShort(id, type, stream);
        }
    },
    INT {
        @Override
        public MetadataInt getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataInt(id, type, stream);
        }
    },
    FLOAT {
        @Override
        public MetadataFloat getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataFloat(id, type, stream);
        }
    },
    STRING {
        @Override
        public MetadataString getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataString(id, type, stream);
        }
    },
    SLOT {
        @Override
        public MetadataSlot getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataSlot(id, type, stream);
        }
    },
    LOCATION {
        @Override
        public MetadataLocation getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataLocation(id, type, stream);
        }
    },
    ROTATION {
        @Override
        public MetadataRotation getObject(int id, int type, PacketInputStream stream) throws IOException {
            return new MetadataRotation(id, type, stream);
        }
    };

    private static int count = 0;
    private static BiMap<Integer, MetadataType> types = HashBiMap.create();

    static {
        for (MetadataType type : values()) {
            types.put(count++, type);
        }
    }

    public abstract MetadataObject getObject(int id, int type, PacketInputStream stream) throws IOException;

    public static MetadataType getType(int id) {
        return types.get(id);
    }

}
