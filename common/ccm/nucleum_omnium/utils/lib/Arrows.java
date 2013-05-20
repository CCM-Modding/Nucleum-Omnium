package ccm.nucleum_omnium.utils.lib;

public enum Arrows
{
    NORTH(8679),
    NORHEAST(8663),
    EAST(8680),
    SOUTHEAST(8664),
    SOUTH(8681),
    SOUTHWEST(8665),
    WEST(8678),
    NOTHWEST(8662);
    
    private final int dec;
    
    private Arrows(int dec)
    {
        this.dec = dec;
    }
    
    @Override
    public String toString()
    {
        return new String(Character.toChars(this.dec));
    }
}
