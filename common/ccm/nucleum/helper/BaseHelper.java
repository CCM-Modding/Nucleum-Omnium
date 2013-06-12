package ccm.nucleum.helper;

import java.util.Random;

import ccm.nucleum.NucleumOmnium;
import ccm.nucleum.utils.exeptions.InstantiationExeption;


public class BaseHelper
{

    protected BaseHelper()
    {
        throw new InstantiationExeption(NucleumOmnium.instance);
    }

    protected static Random rand = new Random();
}