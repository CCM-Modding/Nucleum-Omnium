package ccm.nucleum_omnium.helper;

import java.util.Random;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.utils.exeptions.InstantiationExeption;

public class BaseHelper
{

    protected BaseHelper()
    {
        throw new InstantiationExeption(NucleumOmnium.instance);
    }

    protected static Random rand = new Random();
}