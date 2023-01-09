package com.example.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

  final ObjectMapper mapper = new ObjectMapper()
      .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
      .setTimeZone(TimeZone.getTimeZone("UTC"))
      .findAndRegisterModules();
  final Instant instant = Instant.ofEpochMilli(123456);

  @Test
  public void test() throws JsonProcessingException {
    assertEquals(instant, mapper.readValue("{\"time\":123456}", TimeHolder.class).time);
    assertEquals(instant, mapper.readValue("{\"time\":\"123456\"}", TimeHolder.class).time);
    assertEquals(instant, mapper.readValue("{\"time\":123456}", TimeHolderWithFormat.class).time);
    assertEquals(instant, mapper.readValue("{\"time\":\"123456\"}", TimeHolderWithFormat.class).time);
  }

  static class TimeHolder {

    private Instant time;

    public Instant getTime() {
      return time;
    }

    public void setTime(Instant time) {
      this.time = time;
    }
  }

  static class TimeHolderWithFormat {

    private Instant time;

    public Instant getTime() {
      return time;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public void setTime(Instant time) {
      this.time = time;
    }
  }
}
