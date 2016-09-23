package com.hengyi.japp.common.weixin.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by jzb on 16-5-4.
 */
public class QyUserDTO_simple implements Serializable {
    private String userid;
    private String name;
    private List<Integer> department;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QyUserDTO_simple that = (QyUserDTO_simple) o;
        return Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid);
    }
}
