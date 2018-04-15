package report.layout.controllers.allProperties;


import padeg.lib.Padeg;

public class PadegUtils {
    private  static final int ROD_PADEG = 2;

    public static String changePadeg(String name){
        return Padeg.getFIOPadegFS(name, true, ROD_PADEG);
    }
}
