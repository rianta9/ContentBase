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
 * @date_created May 21, 2022
 * @version 1.0
 * @see
 */
public enum StatusCodeEnum {
  COMPLETED(
    "completed"
  ),
  IN_PROGRESS(
    "in_progress"
  ),
  DELETED(
    "deleted"
  );

  private String value;

  /**
   * @param value
   */
  private StatusCodeEnum(String value) {
    this.value = value;
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
   * Get Value
   * 
   * @return
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Get Enum from Value
   * 
   * @param value
   * @return
   */
  public static StatusCodeEnum getEnum(String value) {
    for (var item : values()) {
      if (item.value.equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(
      "Not exist StatusCodeEnum with value: " + value);
  }

}
