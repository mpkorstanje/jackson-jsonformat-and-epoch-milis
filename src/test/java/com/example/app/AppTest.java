package com.example.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

  final ObjectMapper mapper = new ObjectMapper()
      .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
      .setTimeZone(TimeZone.getTimeZone("UTC"))
      .findAndRegisterModules();
  final Instant instant = Instant.ofEpochMilli(123456);
  final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));

  @Test
  public void test() throws JsonProcessingException {
    String json = "{\"time\":" + instant.toEpochMilli() + "}";
    assertEquals(zonedDateTime, mapper.readValue(json, DateHolder.class).time);
  }

  @Test
  public void test2() throws JsonProcessingException {
    String json = "{\"time\":\"" + instant.toEpochMilli() + "\"}";
    assertEquals(zonedDateTime, mapper.readValue(json, DateHolder.class).time);
  }

  @Test
  public void test3() throws JsonProcessingException {
    String json = "{\"time\":" + instant.toEpochMilli() + "}";
    assertEquals(zonedDateTime, mapper.readValue(json, DateHolderWithFormat.class).time);
  }

  @Test
  public void test4() throws JsonProcessingException {
    String json = "{\"time\":\"" + instant.toEpochMilli() + "\"}";
    assertEquals(zonedDateTime, mapper.readValue(json, DateHolderWithFormat.class).time);
  }

  static class DateHolder {

    private ZonedDateTime time;


    public ZonedDateTime getTime() {
      return time;
    }

    public void setTime(ZonedDateTime time) {
      this.time = time;
    }
  }

  static class DateHolderWithFormat {

    private ZonedDateTime time;


    public ZonedDateTime getTime() {
      return time;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public void setTime(ZonedDateTime time) {
      this.time = time;
    }
  }
}
