package com.example.springarchive.aop.timed;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @apiNote Timed custom annotation implementation
 * @author  Michelnajeho
 */
@Slf4j
@Aspect
@Component
public class TimedAspect {

    @Around("@annotation(Timed)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        MethodSignature method = (MethodSignature)joinPoint.getSignature();
        Timed annotation = method.getMethod().getAnnotation(Timed.class);

        MDC.clear();

        long end = System.currentTimeMillis();
        log.info("Method name : {}", annotation.name());
        log.info("Execution Time : {} Second", ( end - start ) / 1000.0);

        return proceed;
    }
}
