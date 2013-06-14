package ccm.nucleum.utils.lib;

public enum Arrows {
    NORTH(8679),
    NORHEAST(8663),
    EAST(8680),
    SOUTHEAST(8665),
    SOUTH(8681),
    SOUTHWEST(8664),
    WEST(8678),
    NOTHWEST(8662);
    
    private final int dec;
    
    private Arrows(final int dec) {
        this.dec = dec;
    }
    
    @Override
    public String toString() {
        return new String(Character.toChars(dec));
    }
    
    public static Arrows getArrowFromAngle(double angle) {
        angle %= 360;
        if (angle <= 22.5) {
            return Arrows.NORTH;
        } else if (angle <= 67.5) {
            return Arrows.NORHEAST;
        } else if (angle <= 112.5) {
            return Arrows.EAST;
        } else if (angle <= 157.5) {
            return Arrows.SOUTHEAST;
        } else if (angle <= 202.5) {
            return Arrows.SOUTH;
        } else if (angle <= 247.5) {
            return Arrows.SOUTHWEST;
        } else if (angle <= 292.5) {
            return Arrows.WEST;
        } else if (angle <= 337.5) {
            return Arrows.NOTHWEST;
        } else if (angle <= 360) {
            return Arrows.NORTH;
        } else {
            return null;
        }
    }
}
