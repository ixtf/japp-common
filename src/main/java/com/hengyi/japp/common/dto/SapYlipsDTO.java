package com.hengyi.japp.common.dto;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapYlipsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pound_no;
    private String vbeln;
    private String posnr;
    private String pack_type;
    private String trans_type;
    private String car_no;
    private String batch_no;
    private BigDecimal lfimg1;
    private String meins1;
    private BigDecimal lfimg2;
    private String meins2;
    private BigDecimal lfimg;
    private String meins;
    private String note;

    public String getPound_no() {
        return pound_no;
    }

    public void setPound_no(String pound_no) {
        this.pound_no = pound_no;
    }

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

    public String getPack_type() {
        return pack_type;
    }

    public void setPack_type(String pack_type) {
        this.pack_type = pack_type;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public BigDecimal getLfimg1() {
        return lfimg1;
    }

    public void setLfimg1(BigDecimal lfimg1) {
        this.lfimg1 = lfimg1;
    }

    public String getMeins1() {
        return meins1;
    }

    public void setMeins1(String meins1) {
        this.meins1 = meins1;
    }

    public BigDecimal getLfimg2() {
        return lfimg2;
    }

    public void setLfimg2(BigDecimal lfimg2) {
        this.lfimg2 = lfimg2;
    }

    public String getMeins2() {
        return meins2;
    }

    public void setMeins2(String meins2) {
        this.meins2 = meins2;
    }

    public BigDecimal getLfimg() {
        return lfimg;
    }

    public void setLfimg(BigDecimal lfimg) {
        this.lfimg = lfimg;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapYlipsDTO other = (SapYlipsDTO) o;
        return Objects.equal(getPound_no(), other.getPound_no())
                && Objects.equal(getVbeln(), other.getVbeln())
                && Objects.equal(getPosnr(), other.getPosnr());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPound_no(), getVbeln(), getPosnr());
    }
}
