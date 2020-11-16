package com.andela.taccolation.app.utils;

public enum AuthenticationState {
    PENDING,
    AUTHENTICATED,
    EMAIL_UNCONFIRMED,
    UNAUTHENTICATED,
    FAILED,
    NETWORK_ERROR,
    EMAIL_ALREADY_IN_USE,
    PASSWORD_TOO_SHORT,
    NO_USER_RECORD,
    INVALID_CREDENTIALS
}
