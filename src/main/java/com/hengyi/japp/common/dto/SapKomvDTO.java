package com.hengyi.japp.common.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapKomvDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String knumv;
    private String kposn;
    private String stunr;
    private String zaehk;
    private String kappl;
    private String kschl;
    private Date kdatu;
    private String krech;
    private BigDecimal kawrt;
    private BigDecimal kbetr;
    private String waers;
    private BigDecimal kkurs;
    private BigDecimal kpein;
    private String kmein;

    public String getKnumv() {
        return knumv;
    }

    public void setKnumv(String knumv) {
        this.knumv = knumv;
    }

    public String getKposn() {
        return kposn;
    }

    public void setKposn(String kposn) {
        this.kposn = kposn;
    }

    public String getStunr() {
        return stunr;
    }

    public void setStunr(String stunr) {
        this.stunr = stunr;
    }

    public String getZaehk() {
        return zaehk;
    }

    public void setZaehk(String zaehk) {
        this.zaehk = zaehk;
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

    public Date getKdatu() {
        return kdatu;
    }

    public void setKdatu(Date kdatu) {
        this.kdatu = kdatu;
    }

    public String getKrech() {
        return krech;
    }

    public void setKrech(String krech) {
        this.krech = krech;
    }

    public BigDecimal getKawrt() {
        return kawrt;
    }

    public void setKawrt(BigDecimal kawrt) {
        this.kawrt = kawrt;
    }

    public BigDecimal getKkurs() {
        return kkurs;
    }

    public void setKkurs(BigDecimal kkurs) {
        this.kkurs = kkurs;
    }

    public BigDecimal getKpein() {
        return kpein;
    }

    public void setKpein(BigDecimal kpein) {
        this.kpein = kpein;
    }

    public String getKmein() {
        return kmein;
    }

    public void setKmein(String kmein) {
        this.kmein = kmein;
    }

    public String getWaers() {
        return waers;
    }

    public void setWaers(String waers) {
        this.waers = waers;
    }

    public BigDecimal getKbetr() {
        return kbetr;
    }

    public void setKbetr(BigDecimal kbetr) {
        this.kbetr = kbetr;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapKomvDTO other = (SapKomvDTO) o;
        return Objects.equal(getKnumv(), other.getKnumv());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKnumv());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(getKnumv()).toString();
    }
}
