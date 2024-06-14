package com.prac.music.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "Request 정보")
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.prac.music.domain..controller..*.*(..))")
    public void allController() {}

    @Before("allController()")
    public void logController(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String method = request.getMethod();
            String url = request.getRequestURL().toString();

            log.info("Request URL: {}", url);
            log.info("HTTP Method: {}", method);
        }
    }
}
