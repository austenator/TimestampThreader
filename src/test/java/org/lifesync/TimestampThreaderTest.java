package org.lifesync;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AppTest")
public class TimestampThreaderTest {
  @DisplayName("successfully returns true")
  @Test
  void testApp_Success() {
    assertTrue(TimestampThreader.isTrue());
  }
}
