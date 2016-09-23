package com.hengyi.japp.common.weixin.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by jzb on 16-5-4.
 */
public class QyDepartmentDTO implements Serializable {
    private int id;
    private String name;
    private int parentid;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QyDepartmentDTO that = (QyDepartmentDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
