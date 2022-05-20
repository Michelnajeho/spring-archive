package com.spring.utils.archive.aop.timed;

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

    /**
     * Apply @Timed(name = "{Method Name}") to the method you want to use.
     * Outputs the run time in the form of the view below.
     *
     * Method name : {Method Name}
     * Execution Time : 0.0 Second
     */
    @Around("@annotation(com.spring.utils.archive.aop.timed.Timed)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        MethodSignature method = (MethodSignature)joinPoint.getSignature();
        Timed annotation = method.getMethod().getAnnotation(Timed.class);

        MDC.clear();

        long end = System.currentTimeMillis();
        log.debug("Method name : {}", annotation.name());
        log.debug("Execution Time : {} Second", ( end - start ) / 1000.0);

        return proceed;
    }
}
