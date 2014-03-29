// =================== DO NOT EDIT THIS FILE ====================
// Generated by Modello 1.8.1,
// any modifications will be overwritten.
// ==============================================================

package ccm.libs.org.apache.maven.artifact.repository.metadata;

/**
 * Versioning information for a sub-artifact of the current
 * snapshot artifact.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class SnapshotVersion
    implements java.io.Serializable, java.lang.Cloneable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The classifier of the sub-artifact.
     */
    private String classifier = "";

    /**
     * The file extension of thesub-artifact.
     */
    private String extension;

    /**
     * The resolved snapshot version of the sub-artifact.
     */
    private String version;

    /**
     * The timestamp when this version information was last
     * updated. The timestamp is expressed using UTC in the format
     * yyyyMMddHHmmss.
     */
    private String updated;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method clone.
     * 
     * @return SnapshotVersion
     */
    public SnapshotVersion clone()
    {
        try
        {
            SnapshotVersion copy = (SnapshotVersion) super.clone();

            return copy;
        }
        catch ( java.lang.Exception ex )
        {
            throw (java.lang.RuntimeException) new java.lang.UnsupportedOperationException( getClass().getName()
                + " does not support clone()" ).initCause( ex );
        }
    } //-- SnapshotVersion clone()

    /**
     * Get the classifier of the sub-artifact.
     * 
     * @return String
     */
    public String getClassifier()
    {
        return this.classifier;
    } //-- String getClassifier()

    /**
     * Get the file extension of thesub-artifact.
     * 
     * @return String
     */
    public String getExtension()
    {
        return this.extension;
    } //-- String getExtension()

    /**
     * Get the timestamp when this version information was last
     * updated. The timestamp is expressed using UTC in the format
     * yyyyMMddHHmmss.
     * 
     * @return String
     */
    public String getUpdated()
    {
        return this.updated;
    } //-- String getUpdated()

    /**
     * Get the resolved snapshot version of the sub-artifact.
     * 
     * @return String
     */
    public String getVersion()
    {
        return this.version;
    } //-- String getVersion()

    /**
     * Set the classifier of the sub-artifact.
     * 
     * @param classifier
     */
    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    } //-- void setClassifier( String )

    /**
     * Set the file extension of thesub-artifact.
     * 
     * @param extension
     */
    public void setExtension( String extension )
    {
        this.extension = extension;
    } //-- void setExtension( String )

    /**
     * Set the timestamp when this version information was last
     * updated. The timestamp is expressed using UTC in the format
     * yyyyMMddHHmmss.
     * 
     * @param updated
     */
    public void setUpdated( String updated )
    {
        this.updated = updated;
    } //-- void setUpdated( String )

    /**
     * Set the resolved snapshot version of the sub-artifact.
     * 
     * @param version
     */
    public void setVersion( String version )
    {
        this.version = version;
    } //-- void setVersion( String )

}
