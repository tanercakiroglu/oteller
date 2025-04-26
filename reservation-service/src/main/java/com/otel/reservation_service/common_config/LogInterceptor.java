package com.otel.reservation_service.common_config;

import java.lang.reflect.Method;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LogInterceptor {


  @Around(value = "@annotation(loggable)", argNames = "joinPoint,loggable")
  public Object performanceLogMethod(ProceedingJoinPoint joinPoint, Loggable loggable)
      throws Throwable {
    return performanceLogging(joinPoint, loggable);
  }

  @Around(value = "@within(loggable)", argNames = "joinPoint,loggable")
  public Object performanceLogClass(ProceedingJoinPoint joinPoint, Loggable loggable)
      throws Throwable {
    return performanceLogging(joinPoint, loggable);
  }

  private Object performanceLogging(ProceedingJoinPoint joinPoint, Loggable loggable)
      throws Throwable {
    final var stopWatch = new StopWatch();
    var methodReturn = new Object();

    try {
      stopWatch.start();
      methodReturn = joinPoint.proceed();
    } finally {
      stopWatch.stop();
      final var logMessage = new StringBuilder();
      if (loggable == null) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        loggable = method.getAnnotation(Loggable.class);
      }
      logMessage.append(loggable.value());
      logMessage.append(" ");
      logMessage.append(joinPoint.getSignature().getDeclaringType());
      logMessage.append("-->");
      logMessage.append(joinPoint.getSignature().getName());
      logMessage.append("(");
      // append args
      Object[] args = joinPoint.getArgs();
      for (Object arg : args) {
        logMessage.append(arg).append(",");
      }
      if (args.length > 0) {
        logMessage.deleteCharAt(logMessage.length() - 1);
      }

      logMessage.append(")");
      logMessage.append(" execution time: ");
      logMessage.append(stopWatch.getTotalTimeMillis());
      logMessage.append(" ms");
      if (log.isInfoEnabled()) {
        log.info(logMessage.toString());
      }
    }
    return methodReturn;
  }
}
