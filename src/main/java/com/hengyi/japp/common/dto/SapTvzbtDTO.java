package com.hengyi.japp.common.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTvzbtDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String spras;
    private String zterm;
    private String vtext;

    public SapTvzbtDTO() {
        super();
    }

    public String getSpras() {
        return spras;
    }

    public void setSpras(String spras) {
        this.spras = spras;
    }

    public String getZterm() {
        return zterm;
    }

    public void setZterm(String zterm) {
        this.zterm = zterm;
    }

    public String getVtext() {
        return vtext;
    }

    public void setVtext(String vtext) {
        this.vtext = vtext;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapTvzbtDTO other = (SapTvzbtDTO) o;
        return Objects.equal(getZterm(), other.getZterm());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getZterm());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(getZterm()).toString();
    }
}
