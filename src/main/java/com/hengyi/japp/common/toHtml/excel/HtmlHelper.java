package com.hengyi.japp.common.toHtml.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Formatter;

/**
 * This interface is used where code wants to be independent of the workbook
 * formats.  If you are writing such code, you can add a method to this
 * interface, and then implement it for both HSSF and XSSF workbooks, letting
 * the driving code stay independent of format.
 * Created by jzb on 16-11-7.
 */
public interface HtmlHelper {
    /**
     * Outputs the appropriate CSS style for the given cell style.
     *
     * @param style The cell style.
     * @param out   The place to write the output.
     */
    void colorStyles(CellStyle style, Formatter out);
}
