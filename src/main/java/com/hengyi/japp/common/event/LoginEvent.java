package com.hengyi.japp.common.event;


import com.hengyi.japp.common.data.UserType;

public class LoginEvent {

    private final UserType userType;
    private final Object bindId;

    public LoginEvent(UserType userType, Object bindId) {
        super();
        this.userType = userType;
        this.bindId = bindId;
    }

    public UserType getUserType() {
        return userType;
    }

    public Object getBindId() {
        return bindId;
    }
}
