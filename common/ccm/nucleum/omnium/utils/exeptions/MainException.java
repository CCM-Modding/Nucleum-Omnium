package ccm.nucleum.omnium.utils.exeptions;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

public class MainException extends RuntimeException
{
    /**
     * long, serialVersionUID
     */
    private static final long serialVersionUID = -5710853836298784734L;

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     * 
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public MainException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message, and formats it with the specified arguments. The cause is not
     * initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     * 
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     * @param args
     *            the arguments to be added to the detailed message
     */
    public MainException(String message, Object... args)
    {
        super(String.format(message, args));
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this runtime exception's
     * detail message.
     * 
     * @param message
     *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     *            indicates that the cause is nonexistent
     *            or unknown.)
     * @since 1.4
     */
    public MainException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and
     * detail message of <tt>cause</tt>). This constructor is useful for runtime exceptions that are little more than wrappers for other
     * throwables.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     *            indicates that the cause is nonexistent
     *            or unknown.)
     * @since 1.4
     */
    public MainException(Throwable cause)
    {
        super(cause);
    }

    protected void crashMC()
    {
        Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(this, toString()));
    }
}
