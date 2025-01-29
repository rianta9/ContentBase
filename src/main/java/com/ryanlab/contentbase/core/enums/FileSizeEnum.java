//////////////////////////////////////////////////////
//
// * Code For Fun *_^
//
//////////////////////////////////////////////////////
package com.ryanlab.contentbase.core.enums;

/**
 * This class is used for ...
 * 
 * @project N9
 * @author rianta9
 * @date_created Jul 30, 2022
 * @version 1.0
 * @see
 */
public enum FileSizeEnum {
  BYTES(
    "Bytes"
  ),
  KILOBYTES(
    "KB"
  ),
  MEGABYTES(
    "MB"
  ),
  GIGABYTES(
    "GB"
  ),
  TERABYTES(
    "TB"
  );

  private String value;

  /**
   * Constant of FileSizeEnum
   * 
   * @param value
   */
  FileSizeEnum(String value) {
    this.value = value;
  }

  /**
   * Get Value
   * 
   * @return
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Get Name
   * 
   * @return
   */
  public String getName() {
    return this.name();
  }

  /**
   * Get Enum
   * 
   * @param value
   * @return
   */
  public FileSizeEnum getEnum(String value) {
    for (var item : values()) {
      if (item.value.equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(
      "Not exist FileSizeEnum with value: " + value);
  }
}
