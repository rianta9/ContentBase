package com.ryanlab.contentbase.core.util;

import java.util.UUID;

/**
 * Class for creating a random UUID
 * 
 * @project ContentBase
 * @author rianta9
 * @date_created Jan 4, 2025
 * @version 1.0
 * @see
 */
public class RandomUUID {
  /**
   * Get Random ID
   * 
   * @return
   */
  public static String getRandomID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
