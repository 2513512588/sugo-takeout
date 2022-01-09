package com.sugo.smart_city.common.exception;

import javax.security.auth.message.AuthException;

public class PermissionException extends AuthException {

    public PermissionException(String msg) {
        super(msg);
    }
}
