package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapVbkdDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String vbeln;
    private String posnr;
    private String inco1;
    private String inco2;
    private String zterm;
    private String bstkd;
    private Date bstdk;

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getInco1() {
        return inco1;
    }

    public void setInco1(String inco1) {
        this.inco1 = inco1;
    }

    public String getZterm() {
        return zterm;
    }

    public void setZterm(String zterm) {
        this.zterm = zterm;
    }

    public String getInco2() {
        return inco2;
    }

    public void setInco2(String inco2) {
        this.inco2 = inco2;
    }

    public String getBstkd() {
        return bstkd;
    }

    public void setBstkd(String bstkd) {
        this.bstkd = bstkd;
    }

    public Date getBstdk() {
        return bstdk;
    }

    public void setBstdk(Date bstdk) {
        this.bstdk = bstdk;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapVbkdDTO other = (SapVbkdDTO) o;
        return Objects.equal(getVbeln(), other.getVbeln())
                && Objects.equal(getPosnr(), other.getPosnr());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVbeln(), getPosnr());
    }
}
