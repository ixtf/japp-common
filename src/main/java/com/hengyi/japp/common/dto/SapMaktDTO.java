package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapMaktDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String matnr;
    private String maktx;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapMaktDTO other = (SapMaktDTO) o;
        return Objects.equal(getMatnr(), other.getMatnr());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMatnr());
    }
}
