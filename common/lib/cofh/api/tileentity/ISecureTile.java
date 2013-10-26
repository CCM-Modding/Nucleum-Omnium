package lib.cofh.api.tileentity;

public interface ISecureTile
{

    public static enum AccessMode
    {
        PUBLIC,
        RESTRICTED,
        PRIVATE;

        public boolean isPublic()
        {

            return this == PUBLIC;
        }

        public boolean isRestricted()
        {

            return this == RESTRICTED;
        }

        public boolean isPrivate()
        {

            return this == PRIVATE;
        }
    }

    public boolean setAccess(AccessMode access);

    public AccessMode getAccess();

    public boolean setOwnerName(String name);

    public String getOwnerName();

    public boolean canPlayerAccess(String name);
}