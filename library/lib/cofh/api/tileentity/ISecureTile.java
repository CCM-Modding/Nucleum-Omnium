package lib.cofh.api.tileentity;

public interface ISecureTile {

	public static final int	PUBLIC		= 0;
	public static final int	RESTRICTED	= 1;
	public static final int	PRIVATE		= 2;

	public boolean setAccess(int access);

	public int getAccess();

	public boolean setOwnerName(String name);

	public String getOwnerName();

	public boolean canPlayerAccess(String name);

	public boolean isPublic();

	public boolean isRestricted();

	public boolean isPrivate();

}
