package com.ericsson.li.binary2;


public enum BPBitOrder {
  /**
   * Most Significant Bit First means that the most significant bit will arrive first, the 7th bit will be read as the first one.
   */
  MSB,
  /**
   * Least Significant Bit First means that the least significant bit will arrive first, the 0th bit will be read as the first one.
   * It is default order for Java.
   */
  LSB
}
