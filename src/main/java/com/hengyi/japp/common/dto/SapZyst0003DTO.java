package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapZyst0003DTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String vbeln;
    private Integer zprno;
    private String name2;
    private String zvbeln;
    private String extension1;
    private String zbeiz;
    private Date begda;
    private Date endda;
    private String px1;
    private String px2;
    private Date build_date;
    private String car_no;

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public Integer getZprno() {
        return zprno;
    }

    public void setZprno(Integer zprno) {
        this.zprno = zprno;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getZvbeln() {
        return zvbeln;
    }

    public void setZvbeln(String zvbeln) {
        this.zvbeln = zvbeln;
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    public String getZbeiz() {
        return zbeiz;
    }

    public void setZbeiz(String zbeiz) {
        this.zbeiz = zbeiz;
    }

    public Date getBegda() {
        return begda;
    }

    public void setBegda(Date begda) {
        this.begda = begda;
    }

    public Date getEndda() {
        return endda;
    }

    public void setEndda(Date endda) {
        this.endda = endda;
    }

    public String getPx1() {
        return px1;
    }

    public void setPx1(String px1) {
        this.px1 = px1;
    }

    public String getPx2() {
        return px2;
    }

    public void setPx2(String px2) {
        this.px2 = px2;
    }

    public Date getBuild_date() {
        return build_date;
    }

    public void setBuild_date(Date build_date) {
        this.build_date = build_date;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapZyst0003DTO other = (SapZyst0003DTO) o;
        return Objects.equal(getVbeln(), other.getVbeln());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVbeln());
    }
}
