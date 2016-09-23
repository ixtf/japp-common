package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapKna1DTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String kunnr;
    private String land1;
    private String name1;

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapKna1DTO other = (SapKna1DTO) o;
        return Objects.equal(getKunnr(), other.getKunnr());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKunnr());
    }
}
