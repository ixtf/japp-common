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
public class SapVbakDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String vbeln;
    private BigDecimal netwr;
    private String waerk;
    private String kunnr;
    private String bstnk;
    private Date audat;

    public SapVbakDTO(String vbeln) {
        super();
        this.vbeln = vbeln;
    }

    public SapVbakDTO() {
        super();
    }

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getBstnk() {
        return bstnk;
    }

    public void setBstnk(String bstnk) {
        this.bstnk = bstnk;
    }

    public Date getAudat() {
        return audat;
    }

    public void setAudat(Date audat) {
        this.audat = audat;
    }

    public BigDecimal getNetwr() {
        return netwr;
    }

    public void setNetwr(BigDecimal netwr) {
        this.netwr = netwr;
    }

    public String getWaerk() {
        return waerk;
    }

    public void setWaerk(String waerk) {
        this.waerk = waerk;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapVbakDTO other = (SapVbakDTO) o;
        return Objects.equal(getVbeln(), other.getVbeln());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVbeln());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(getVbeln()).toString();
    }
}
