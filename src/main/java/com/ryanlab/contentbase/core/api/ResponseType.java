package com.ryanlab.contentbase.core.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ResponseType {
  SUCCESS(
    "success"
  ),
  INFO(
    "info"
  ),
  WARNING(
    "warning"
  ),
  ERROR(
    "error"
  ),
  UNKNOW(
    "unknow"
  );

  private String key;

  ResponseType(String key) {
    this.key = key;
  }

  public static ResponseType toEnum(String name) {
    for (ResponseType item : values()) {
      if (item.name() == name)
        return item;
    }
    log.error("Not found ResponseType with name: " + name);
    return ResponseType.UNKNOW;
  }

  public String getKey() {
    return key;
  }
}
