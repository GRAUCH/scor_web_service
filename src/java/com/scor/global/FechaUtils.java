package com.scor.global;

import java.util.Date;
import java.text.SimpleDateFormat;


public class FechaUtils {

    private static final String innerDayFormat = "dd";
    private static final String innerMonthFormat = "MM";
    private static final String innerYearFormat = "yyyy";

    /**
     *
     * @param fecha
     * @return
     */
    public static int getYearAsInt (Date fecha) {
        return Integer.parseInt(getYearAsString(fecha));
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static String getDayAsString (Date fecha) {
        return dateToString(fecha, innerDayFormat);
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static String getYearAsString (Date fecha) {
        return dateToString(fecha, innerYearFormat);
    }
    /**
     *
     * @param valor
     * @param format
     * @return
     */
    public static String dateToString(Date valor, String format) {
        String result = "";

        if (valor != null && (format != null && !format.trim().equals(""))) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(valor);
        }

        return result;
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static int getMonthAsInt (Date fecha) {
        return Integer.parseInt(getMonthAsString(fecha));
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static String getMonthAsString (Date fecha) {
        return dateToString(fecha, innerMonthFormat);
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static int getDayAsInt (Date fecha) {
        return Integer.parseInt(getDayAsString(fecha));
    }
}
