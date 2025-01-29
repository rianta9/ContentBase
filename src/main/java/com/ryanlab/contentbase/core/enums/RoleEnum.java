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
public enum RoleEnum {
  ADMIN,
  USER;

  /**
   * Get Name
   * 
   * @return
   */
  public String getName() {
    return this.name();
  }

  /**
   * Get Enum From Value
   * 
   * @param value
   * @return
   */
  public static RoleEnum getEnum(String value) {
    for (var item : values()) {
      if (item.name().equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(
      "RoleEnum does not exist enum with value equals " + value);
  }
}
