package com.ryanlab.contentbase.core.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ResponseMessageId {
  E_NO_CATEGORY_MESSAGE,
  E_NO_USER_INFO,
  E_INTERNAL_SERVER_ERROR,
  E_PERMISSION_DENIED,
  E_INVALID_INPUT_FIELD,
  E_RESOURCE_NOT_FOUND;

  public static ResponseMessageId toEnum(String key) {
    for (ResponseMessageId item: values()) {
      if (item.name() == key)
        return item;
    }
    log.error("Not found ResponseMessageId with name: " + key);
    return ResponseMessageId.E_NO_CATEGORY_MESSAGE;
  }
}
