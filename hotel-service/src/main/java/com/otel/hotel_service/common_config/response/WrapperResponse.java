package com.otel.hotel_service.common_config.response;

import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class WrapperResponse<T> extends AbstractResponse {

  private static final WrapperResponse<?> EMPTY = new WrapperResponse<>();
  private T data;


  public WrapperResponse() {
    this.data = null;
    this.status = HttpStatus.OK.value();
  }


  private WrapperResponse(T value) {
    this.data = Objects.requireNonNull(value);
    this.status = HttpStatus.OK.value();
  }

  public static <T> WrapperResponse<T> empty() {
    @SuppressWarnings("unchecked")
    WrapperResponse<T> t = (WrapperResponse<T>) EMPTY;
    return t;
  }

  public static <T> WrapperResponse<T> of(T value) {
    return new WrapperResponse<>(value);
  }

  public static <T> WrapperResponse<T> ofNullable(T value) {
    return value == null ? empty() : of(value);
  }

  public T get() {
    if (data == null) {
      throw new NoSuchElementException("No value present");
    }
    return data;
  }

}
