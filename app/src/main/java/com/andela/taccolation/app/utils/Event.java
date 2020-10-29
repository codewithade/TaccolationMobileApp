package com.andela.taccolation.app.utils;

// used as a wrapper for data that is exposed via a LiveData that represents an event
// i.e for SingleLiveEvent case such as SnackBar, Navigation etc.
public class Event<T> {
    private final T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    // returns the content and prevent it's use again
    public T getContentIfNotHandled() {
        if (hasBeenHandled)
            return null;
        else {
            hasBeenHandled = true;
            return content;
        }
    }

    // returns the content, even if it's already been handled
    public T peekContent() {
        return content;
    }
}
