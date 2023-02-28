package com.hanssem.remodeling.content.admin.service;

import com.amazonaws.util.StringUtils;
import org.apache.poi.ss.usermodel.Row;

public abstract class ExcelImportAbstract {

    protected String readString(Row row, int index) {
        try {
            return row.getCell(index).getStringCellValue();
        } catch (NullPointerException ne) {
            return "";
        }
    }

    protected String readNumeric(Row row, int index) {
        try {
            double numericCellValue = row.getCell(index).getNumericCellValue();
            return Double.toString(numericCellValue);
        } catch (NullPointerException ne) {
            return "";
        }
    }

    protected int readInteger(Row row, int index) {
        try {
            return (int) row.getCell(index).getNumericCellValue();
        } catch (NullPointerException ne) {
            return 0;
        }
    }

    protected long readLong(Row row, int index) {
        try {
            return (long) row.getCell(index).getNumericCellValue();
        } catch (NullPointerException ne) {
            return 0L;
        }
    }

}
