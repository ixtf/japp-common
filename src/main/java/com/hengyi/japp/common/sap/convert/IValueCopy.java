/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.common.sap.convert;

import com.sap.conn.jco.JCoRecord;

/**
 * @param <T>
 * @author jzb
 */
public interface IValueCopy<T> {
    void copy(T dest, JCoRecord source);

    void copy(JCoRecord dest, T source);
}
