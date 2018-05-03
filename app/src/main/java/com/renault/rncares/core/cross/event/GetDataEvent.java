package com.renault.rncares.core.cross.event;


/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class GetDataEvent<T> extends BaseEvent {

    private final String TAG;

    private T data;

    public GetDataEvent(String status, Error error, T data, String tag) throws IllegalArgumentException {
        super(status, error);
        this.data = data;
        this.TAG = tag;
        if (status.equals(STATUS_SUCCESS) && data == null) {
            throw new IllegalArgumentException(EXCEPTED_DATA_NOT_FOUND);
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTAG() {
        return TAG;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetDataEvent)) return false;
        if (!super.equals(o)) return false;

        GetDataEvent<?> that = (GetDataEvent<?>) o;

        if (!TAG.equals(that.TAG)) return false;
        return getData().equals(that.getData());

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GetDataEvent{");
        sb.append("TAG='").append(TAG).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

}
