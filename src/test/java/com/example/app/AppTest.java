package com.example.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

  final ObjectMapper mapper = new ObjectMapper()
      .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
      .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
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
  @Test
  public void test2() throws JsonProcessingException {
    assertEquals("\"1970-01-01T00:02:03.456Z\"", mapper.writeValueAsString(instant));
  }

  static class TimeHolder {
    private Instant time;
    public void setTime(Instant time) {
      this.time = time;
    }
    public Instant getTime() {
      return time;
    }
  }

  static class TimeHolderWithFormat {
    private Instant time;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setTime(Instant time) {
      this.time = time;
    }
    public Instant getTime() {
      return time;
    }
  }
}
