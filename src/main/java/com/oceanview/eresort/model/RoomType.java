package com.oceanview.eresort.model;

/**
 * Enum representing different room types with pricing.
 * Used in JPA entities with @Enumerated annotation.
 */
public enum RoomType {
    SINGLE("Single Room", 5000),
    DOUBLE("Double Room", 8000),
    FAMILY("Family Room", 12000),
    SUITE("Suite", 20000);

    private final String displayName;
    private final int pricePerNight;

    RoomType(String displayName, int pricePerNight) {
        this.displayName = displayName;
        this.pricePerNight = pricePerNight;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Convert string to RoomType enum (case-insensitive).
     */
    public static RoomType fromString(String type) {
        try {
            return RoomType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid room type: " + type);
        }
    }

    @Override
    public String toString() {
        return displayName + " (LKR " + pricePerNight + "/night)";
    }
}
