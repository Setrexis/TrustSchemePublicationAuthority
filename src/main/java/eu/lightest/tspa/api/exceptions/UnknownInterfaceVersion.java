package eu.lightest.tspa.api.exceptions;

public class UnknownInterfaceVersion extends Exception {

    public UnknownInterfaceVersion(String version){
        super(version);
    }
}
