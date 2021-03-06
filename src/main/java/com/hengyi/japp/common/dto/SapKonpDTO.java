package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapKonpDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String knumh;
    private String kopos;
    private String kappl;
    private String kschl;
    private BigDecimal kbetr;
    private String konwa;
    private String mwsk1;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapKonpDTO other = (SapKonpDTO) o;
        return Objects.equal(getKnumh(), other.getKnumh())
                && Objects.equal(getKopos(), other.getKopos());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKnumh(), getKopos());
    }

    public String getKappl() {
        return kappl;
    }

    public void setKappl(String kappl) {
        this.kappl = kappl;
    }

    public String getKschl() {
        return kschl;
    }

    public void setKschl(String kschl) {
        this.kschl = kschl;
    }

    public BigDecimal getKbetr() {
        return kbetr;
    }

    public void setKbetr(BigDecimal kbetr) {
        this.kbetr = kbetr;
    }

    public String getKonwa() {
        return konwa;
    }

    public void setKonwa(String konwa) {
        this.konwa = konwa;
    }

    public String getMwsk1() {
        return mwsk1;
    }

    public void setMwsk1(String mwsk1) {
        this.mwsk1 = mwsk1;
    }

    public String getKnumh() {
        return knumh;
    }

    public void setKnumh(String knumh) {
        this.knumh = knumh;
    }

    public String getKopos() {
        return kopos;
    }

    public void setKopos(String kopos) {
        this.kopos = kopos;
    }
}
