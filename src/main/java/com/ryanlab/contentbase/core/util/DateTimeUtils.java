package com.ryanlab.contentbase.core.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateTimeUtils {
  /**
   * Trả về một chuỗi ngày định dạng dd/MM/yyyy
   * 
   * @param date
   * @return
   */
  public static String toStringType(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String result = formatter.format(date);
    return result;
  }

  public static String toStringType(LocalDateTime date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String result = formatter.format(date);
    return result;
  }

  public static String toStringType(LocalDate date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String result = formatter.format(date);
    return result;
  }

  public static String toStringType(LocalTime time) {
    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    String result = formatter.format(time);
    return result;
  }

  /**
   * Trả về một chuỗi ngày định dạng dd/MM/yyyy
   * 
   * @param date
   * @return
   */
  public static String toStringType(java.util.Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String result = formatter.format(date);
    return result;
  }

  /**
   * Trả về một chuỗi ngày định dạng HH:mm dd/MM/yyyy
   * 
   * @param date
   * @return
   */
  public static String toStringDateTimeType(java.util.Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    String result = formatter.format(date);
    return result;
  }

  /**
   * Định dạng một kiểu Date util bất kỳ thành Date util dd/MM/yyyy
   * 
   * @param date
   * @return
   */
  public static java.util.Date formatDateUtil(java.util.Date date) {
    String dateFormated = toStringType(date);
    try {
      return new SimpleDateFormat("dd/MM/yyyy").parse(dateFormated);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Trả về một chuỗi giờ định dạng HH:mm
   * 
   * @param time
   * @return
   */
  public static String toStringTimeTypeHHmm(java.util.Date time) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    String result = formatter.format(time);
    return result;
  }

  /**
   * Trả về một chuỗi giờ định dạng HH:mm
   * 
   * @param time
   * @return
   */
  public static String toStringTimeTypeHHmm(Date time) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    String result = formatter.format(time);
    return result;
  }

  /**
   * Chuyển định dạng ngày bất kỳ thành định dạng HH:mm
   * 
   * @param time
   * @return
   */
  public static java.util.Date formatTimeUtil(String time) {
    try {
      return new SimpleDateFormat("HH:mm").parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Chuyển định dạng ngày bất kỳ thành định dạng HH:mm
   * 
   * @param time
   * @return
   */
  public static java.util.Date formatTimeUtil(java.util.Date time) {
    String timeFormated = toStringTimeTypeHHmm(time);
    try {
      return new SimpleDateFormat("HH:mm").parse(timeFormated);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Trả về Date(sql) từ một chuỗi có định dạng dd/MM/yyyy
   * 
   * @param date
   * @return
   */
  public static Date toDateSQL(String date) {
    java.util.Date dateUtil;
    try {
      dateUtil = new SimpleDateFormat("dd/MM/yyyy").parse(date);
    } catch (ParseException e) {
      return null;
    }
    Long result = dateUtil.getTime();
    return new Date(result);
  }

  public static java.util.Date toTimeUtil(String time) {
    java.util.Date result;
    try {
      result = new SimpleDateFormat("HH:mm").parse(time);
    } catch (ParseException e) {
      return null;
    }
    return result;
  }

  /**
   * Chuyển từ một date util sang date sql
   * 
   * @param date
   * @return
   */
  public static Date dateUtilToDateSQL(java.util.Date date) {
    Long milis = date.getTime();
    return new Date(milis);
  }

  /**
   * Chuyển từ một date sql sang date util
   * 
   * @param date
   * @return
   */
  public static java.util.Date dateSQLToDateUtil(Date date) {
    Long milis = date.getTime();
    return new java.util.Date(milis);
  }

  /**
   * Kiểm tra thời điểm đặt bàn có hợp lệ không(Phải lớn hơn thời điểm hiện tại
   * ba mươi phút)
   * 
   * @param dateIn
   * @param timeIn
   * @return
   */
  public static boolean checkValidDateTimeForBooking(java.util.Date dateIn,
    String timeIn) {
    int compareDate =
      formatDateUtil(dateIn).compareTo(formatDateUtil(new java.util.Date()));
    if (compareDate < 0)
      return false;

    else if (compareDate == 0) {
      long timeInMs = formatTimeUtil(timeIn).getTime();
      long currentTimeMs = formatTimeUtil(new java.util.Date()).getTime();
      if (timeInMs - currentTimeMs < 30 * 60000)
        return false;
    }
    return true;
  }
}

