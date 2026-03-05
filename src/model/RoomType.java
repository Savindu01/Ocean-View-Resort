package model;

/**
 * Enum representing room types with their prices per night in LKR.
 */
public enum RoomType {
    SINGLE("Single", 5000),
    DOUBLE("Double", 8000),
    FAMILY("Family", 12000),
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

    @Override
    public String toString() {
        return displayName + " - LKR " + String.format("%,d", pricePerNight) + "/night";
    }

    public static RoomType fromString(String text) {
        for (RoomType type : RoomType.values()) {
            if (type.name().equalsIgnoreCase(text) || type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown room type: " + text);
    }
}
