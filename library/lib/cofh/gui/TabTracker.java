package lib.cofh.gui;

import lib.cofh.gui.element.TabBase;

/**
 * Keeps track of which tabs should be open by default when a player opens a
 * GUI.
 * 
 * @author King Lemming
 */
public class TabTracker {

    private static Class<? extends TabBase> openedLeftTab;

    private static Class<? extends TabBase> openedRightTab;

    public static Class<? extends TabBase> getOpenedLeftTab() {

        return TabTracker.openedLeftTab;
    }

    public static Class<? extends TabBase> getOpenedRightTab() {

        return TabTracker.openedRightTab;
    }

    public static void setOpenedLeftTab(final Class<? extends TabBase> tabClass) {

        TabTracker.openedLeftTab = tabClass;
    }

    public static void setOpenedRightTab(final Class<? extends TabBase> tabClass) {

        TabTracker.openedRightTab = tabClass;
    }

}
