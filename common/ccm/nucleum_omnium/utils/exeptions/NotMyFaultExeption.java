package ccm.nucleum_omnium.utils.exeptions;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import ccm.nucleum_omnium.IMod;

public class NotMyFaultExeption extends RuntimeException
{

    /**
     * This Exception, and All its sub-Classes are NOT my Fault
     */
    private static final long serialVersionUID = -4392997358096412890L;

    private final IMod        mod;

    protected StringBuilder   errorSB          = new StringBuilder();

    public NotMyFaultExeption(final IMod mod)
    {
        this.mod = mod;
        this.addString();
    }

    private void addString()
    {
        this.errorSB.append("The mod '");
        this.errorSB.append(this.mod.getName());
        this.errorSB.append("' has a Problem.\nIT'S NOT MY FAULT! ");
        this.errorSB.append("Below is how to fix it.\n \n");
        this.errorSB.append("|");
        this.errorSB.append("\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!\n");
    }

    protected void crashMC()
    {
        Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(this, this.toString()));
    }

    @Override
    public String toString()
    {
        return this.errorSB.toString();
    }
}