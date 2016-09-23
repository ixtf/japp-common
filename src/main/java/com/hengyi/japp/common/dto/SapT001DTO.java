package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT001DTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String bukrs;
    private String butxt;

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getButxt() {
        return butxt;
    }

    public void setButxt(String butxt) {
        this.butxt = butxt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapT001DTO other = (SapT001DTO) o;
        return Objects.equal(getBukrs(), other.getBukrs());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBukrs());
    }
}
