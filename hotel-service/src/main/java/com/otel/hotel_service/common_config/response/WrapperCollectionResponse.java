package com.otel.hotel_service.common_config.response;

import java.util.Collection;
import java.util.Collections;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class WrapperCollectionResponse<T> extends AbstractResponse {

  private static final WrapperCollectionResponse<?> EMPTY = new WrapperCollectionResponse<>();
  private Collection<T> data;
  private long count;
  private long page;
  private long limit;
  private long totalCount;
  private long totalPage;


  private WrapperCollectionResponse() {
    this.data = Collections.emptyList();
  }

  public WrapperCollectionResponse(Page<T> value) {
    this.status = HttpStatus.OK.value();
    this.data = CollectionUtils.isEmpty(value.getContent()) ? Collections.emptyList()
        : value.getContent();
    this.count = value.getNumberOfElements();
    this.totalCount = value.getTotalElements();
    this.totalPage = value.getTotalPages();
    this.page = value.getNumber();
    this.limit = value.getSize();
  }


  private WrapperCollectionResponse(Collection<T> value) {
    this.data = CollectionUtils.isEmpty(value) ? Collections.emptyList() : value;
    this.count = CollectionUtils.isEmpty(value) ? 0L : value.size();
    this.status = HttpStatus.OK.value();
  }

  public static <T> WrapperCollectionResponse<T> empty() {
    @SuppressWarnings("unchecked")
    WrapperCollectionResponse<T> t = (WrapperCollectionResponse<T>) EMPTY;
    return t;
  }

  public static <T> WrapperCollectionResponse<T> of(Collection<T> value) {
    return new WrapperCollectionResponse<>(value);
  }

  public static <T> WrapperCollectionResponse<T> of(Page<T> value) {
    return new WrapperCollectionResponse<>(value);
  }

  public static <T> WrapperCollectionResponse<T> ofNullable(Collection<T> value) {
    return value == null ? empty() : of(value);
  }

  public Collection<T> get() {
    if (CollectionUtils.isEmpty(data)) {
      return Collections.emptyList();
    }
    return data;
  }


}
