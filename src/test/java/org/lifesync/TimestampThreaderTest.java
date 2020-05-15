package org.lifesync;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("AppTest")
public class TimestampThreaderTest {
  @DisplayName("successfully returns true")
  @Test
  void testApp_Success() {
    assertTrue(TimestampThreader.isTrue());
  }
}
