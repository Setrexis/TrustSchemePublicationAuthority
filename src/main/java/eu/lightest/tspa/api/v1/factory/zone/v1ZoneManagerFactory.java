package eu.lightest.tspa.api.v1.factory.zone;

import eu.lightest.tspa.interfaces.zone.IZoneManager;
import eu.lightest.tspa.api.v1.zone.ZoneManager;

public class v1ZoneManagerFactory {
    public static IZoneManager newZoneManager() {
        return new ZoneManager();
    }
}
