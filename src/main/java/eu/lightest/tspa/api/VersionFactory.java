package eu.lightest.tspa.api;

import eu.lightest.tspa.api.exceptions.UnknownInterfaceVersion;
import eu.lightest.tspa.api.v1.factory.publication.v1PublicationManagerFactory;
import eu.lightest.tspa.api.v1.factory.zone.v1ZoneManagerFactory;
import eu.lightest.tspa.interfaces.publication.IPublicationManager;
import eu.lightest.tspa.interfaces.zone.IZoneManager;

public class VersionFactory {

    public static IZoneManager makeZoneManagerFactory(String vType) throws UnknownInterfaceVersion {
        if(vType.compareTo("v1") ==0 ) {
            return new v1ZoneManagerFactory().newZoneManager();
        }else{
            throw new UnknownInterfaceVersion(vType);
        }
    }

    public static IPublicationManager makePublicationManagerFactory(String vType) throws UnknownInterfaceVersion {
        if(vType.compareTo("v1") == 0) {
            return new v1PublicationManagerFactory().newPublicationManager();
        } else {
            throw new UnknownInterfaceVersion(vType);
        }
    }

}
