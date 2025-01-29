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
 * @date_created Jun 23, 2022
 * @version 1.0
 * @see
 */
public enum DisplayStatusEnum {
  DISABLE(
    "disable"
  ),
  VISIBLE(
    "visible"
  );

  private String value;

  /**
   * @param value
   */
  private DisplayStatusEnum(String value) {
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

  public String getValue() {
    return this.value;
  }

  public static DisplayStatusEnum getEnum(String value) {
    for (var item : values()) {
      if (item.value.equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(
      "StatusEnum does not exist enum with value equals " + value);
  }
}
