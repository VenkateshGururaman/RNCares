package com.renault.rncares.core.cross.event;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class BaseEvent {

    /***********************************************************
     * Contants / Status
     **********************************************************/
    public final static String STATUS_SUCCESS = "SUCCESS";
    public final static String STATUS_ERROR = "ERROR";

    /***********************************************************
     * Error messages.
     **********************************************************/
    public static final String EXCEPTED_DATA_NOT_FOUND = "Successful event was expecting data.";
    private static final String ILLEGAL_ARGUMENTS_MESSAGE = "Successful event should not contain an error and unsuccessful event should contain an error.";

    /***********************************************************
     * Attributes
     **********************************************************/
    protected String status = null;
    protected Error error = null;

    /***********************************************************
     * Constructors
     **********************************************************/
    public BaseEvent(String status, Error error) throws IllegalArgumentException {
        this.status = status;
        this.error = error;

        if ((status.equals(STATUS_SUCCESS) && error != null) || (status.equals(STATUS_ERROR) && error == null)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENTS_MESSAGE);
        }
    }

    /***********************************************************
     * Methods
     **********************************************************/
    public String getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEvent baseEvent = (BaseEvent) o;

        if (status != null ? !status.equals(baseEvent.status) : baseEvent.status != null)
            return false;
        return error != null ? error.equals(baseEvent.error) : baseEvent.error == null;

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "status='" + status + '\'' +
                ", error=" + error +
                '}';
    }

}
