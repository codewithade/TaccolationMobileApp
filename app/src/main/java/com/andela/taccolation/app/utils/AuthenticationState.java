package com.andela.taccolation.app.utils;

public enum AuthenticationState {
    PENDING,
    AUTHENTICATED,
    EMAIL_UNCONFIRMED,
    UNAUTHENTICATED,
    FAILED,
    NETWORK_ERROR,
    NO_USER_RECORD,
    INVALID_CREDENTIALS
}
