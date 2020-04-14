package com.sreedwish.cybazeapp.rest;



import java.io.IOException;

public class NoConnectivityException extends IOException {

    public static final String NO_INTERNET_MSG = "ഇന്റർനെറ്റ് കണക്ഷൻ ലഭ്യമല്ല. കണക്ഷൻ ലഭ്യമായതിനു ശേഷം വീണ്ടും ശ്രമിക്കുക .";

    @Override
    public String getMessage() {
        return NO_INTERNET_MSG;
    }
}
