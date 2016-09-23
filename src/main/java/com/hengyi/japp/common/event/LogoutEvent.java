package com.hengyi.japp.common.event;


import com.hengyi.japp.common.data.UserType;

public class LogoutEvent {

    private final UserType userType;
    private final Object bindId;

    public LogoutEvent(UserType userType, Object bindId) {
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
