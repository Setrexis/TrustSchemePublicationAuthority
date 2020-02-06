package eu.lightest.tspa.api.v1.factory.publication;

import eu.lightest.tspa.interfaces.publication.IPublicationManager;
import eu.lightest.tspa.api.v1.publication.*; //FIXME: IntelliJ does not want me to put PublicationManager instead of the *

public class v1PublicationManagerFactory {
    public static IPublicationManager newPublicationManager() {
        return new PublicationManager();
    }
}
