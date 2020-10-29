package com.andela.taccolation.app.utils;

public enum AuthenticationState {
    PENDING,
    AUTHENTICATED,
    EMAIL_UNCONFIRMED,
    EMAIL_CONFIRMED,
    UNAUTHENTICATED,
    FAILED,
    NETWORK_ERROR,
    INVALID_CREDENTIALS
}
