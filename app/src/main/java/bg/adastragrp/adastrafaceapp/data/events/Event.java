package bg.adastragrp.adastrafaceapp.data.events;

public class Event<T> {

    private T content;
    private boolean isHandled;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if (isHandled) {
            return null;
        }

        isHandled = true;
        return content;
    }

    public boolean isHandled() {
        return isHandled;
    }
}
