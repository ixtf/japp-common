package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT023tDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String matkl;
    private String wgbez;

    public String getMatkl() {
        return matkl;
    }

    public void setMatkl(String matkl) {
        this.matkl = matkl;
    }

    public String getWgbez() {
        return wgbez;
    }

    public void setWgbez(String wgbez) {
        this.wgbez = wgbez;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapT023tDTO other = (SapT023tDTO) o;
        return Objects.equal(getMatkl(), other.getMatkl());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMatkl());
    }
}
