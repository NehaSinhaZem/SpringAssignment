package com.example.shopping.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Order(1)
public class ServiceLoggingAspect {
    private static Logger logger = Logger.getLogger(ServiceLoggingAspect.class.getName());
    @Pointcut("execution(* com.example.shopping.service.*.get*(..))")
    private void getFromDB(){}

    @Pointcut("execution(* com.example.shopping.service.*.save*(..))")
    private void addToDB(){}

    @Pointcut("execution(* com.example.shopping.service.*.delete*(..))")
    public void deleteFromDB(){}

    @Pointcut("getFromDB() || addToDB() || deleteFromDB()")
    public void forServices(){}

    @Before("forServices()")
    public void beforeCallingService(JoinPoint joinPoint){
        logger.info("Executing advice @Before calling service to do something...");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info(signature.toString());
    }
    @AfterThrowing(pointcut = "execution(* com.example.shopping.service.*.find*(..))", throwing = "ex")
    public void afterThrowingFindEntityService(JoinPoint joinPoint,Throwable ex){
        String method=joinPoint.getSignature().toShortString();
        logger.info("After returning from method "+ method);
        logger.info("Exception: "+ex);
    }
}
