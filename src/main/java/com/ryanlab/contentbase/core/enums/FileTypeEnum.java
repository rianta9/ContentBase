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
 * @date_created Jul 13, 2022
 * @version 1.0
 * @see
 */
public enum FileTypeEnum {
  IMAGE(
    "image"
  ),
  ATTACHED_FILE(
    "attached_file"
  );

  private String value;

  FileTypeEnum(String value) {
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
  public String getvalue() {
    return this.value;
  }

  /**
   * Get Enum From Value
   * 
   * @param value
   * @return
   */
  public static FileTypeEnum getEnum(String value) {
    for (var item : values()) {
      if (item.value.equals(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(
      "FileType does not exist enum with value equals " + value);
  }

}
