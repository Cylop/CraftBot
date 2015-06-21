package me.hfox.craftbot.entity;

public enum StatusEffect {

    SPEED(1),
    SLOWNESS(2),
    HASTE(3),
    FATIGUE(4),
    STRENGTH(5),
    INSTANT_HEALTH(6),
    INSTANT_DAMAGE(7),
    JUMP_BOOST(8),
    NAUSEA(9),
    REGENERATION(10),
    RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    BLINDNESS(15),
    NIGHT_VISION(16),
    HUNGER(17),
    WEAKNESS(18),
    POISON(19),
    WITHER(20),
    HEALTH_BOOST(21),
    ABSORPTION(22),
    SATURATION(23);

    private int id;

    StatusEffect(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StatusEffect getById(int id) {
        for (StatusEffect effect : values()) {
            if (effect.getId() == id) {
                return effect;
            }
        }

        throw new IllegalArgumentException("Unknown effect Id");
    }

}
