package com.ryanlab.contentbase.core.api;

import java.text.MessageFormat;

import com.ryanlab.contentbase.core.entity.ICoreEntityObject;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

@Data
@Value
@Builder(toBuilder = true)
@Embeddable
@ToString
public class MessagePattern implements ICoreEntityObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private final String value;

  public MessagePattern() {
    this.value = "";
  }

  public MessagePattern(String value) {
    this.value = value;
  }

  /**
   * To Message
   * 
   * @param args
   * @return
   */
  public String toMessage(Object[] args) {
    if (value == null) {
      return null;
    }
    return MessageFormat.format(value, args);
  }

}
