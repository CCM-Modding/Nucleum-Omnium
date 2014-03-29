// =================== DO NOT EDIT THIS FILE ====================
// Generated by Modello 1.8.1,
// any modifications will be overwritten.
// ==============================================================

package ccm.libs.org.apache.maven.artifact.repository.metadata;

/**
 * Snapshot data for the current artifact version.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Snapshot
    implements java.io.Serializable, java.lang.Cloneable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The time it was deployed.
     */
    private String timestamp;

    /**
     * The incremental build number.
     */
    private int buildNumber = 0;

    /**
     * Whether to use a local copy instead (with filename that
     * includes the base version).
     */
    private boolean localCopy = false;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method clone.
     * 
     * @return Snapshot
     */
    public Snapshot clone()
    {
        try
        {
            Snapshot copy = (Snapshot) super.clone();

            return copy;
        }
        catch ( java.lang.Exception ex )
        {
            throw (java.lang.RuntimeException) new java.lang.UnsupportedOperationException( getClass().getName()
                + " does not support clone()" ).initCause( ex );
        }
    } //-- Snapshot clone()

    /**
     * Get the incremental build number.
     * 
     * @return int
     */
    public int getBuildNumber()
    {
        return this.buildNumber;
    } //-- int getBuildNumber()

    /**
     * Get the time it was deployed.
     * 
     * @return String
     */
    public String getTimestamp()
    {
        return this.timestamp;
    } //-- String getTimestamp()

    /**
     * Get whether to use a local copy instead (with filename that
     * includes the base version).
     * 
     * @return boolean
     */
    public boolean isLocalCopy()
    {
        return this.localCopy;
    } //-- boolean isLocalCopy()

    /**
     * Set the incremental build number.
     * 
     * @param buildNumber
     */
    public void setBuildNumber( int buildNumber )
    {
        this.buildNumber = buildNumber;
    } //-- void setBuildNumber( int )

    /**
     * Set whether to use a local copy instead (with filename that
     * includes the base version).
     * 
     * @param localCopy
     */
    public void setLocalCopy( boolean localCopy )
    {
        this.localCopy = localCopy;
    } //-- void setLocalCopy( boolean )

    /**
     * Set the time it was deployed.
     * 
     * @param timestamp
     */
    public void setTimestamp( String timestamp )
    {
        this.timestamp = timestamp;
    } //-- void setTimestamp( String )

}
