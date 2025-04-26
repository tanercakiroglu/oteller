package com.otel.reservation_service.common_config.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapperUtils {

  public static Timestamp map(LocalDateTime value) {
    if (value == null) {
      return null;
    }
    return Timestamp.valueOf(value);

  }

  public static LocalDateTime map(Timestamp value) {
    if (value == null) {
      return null;
    }
    return value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
