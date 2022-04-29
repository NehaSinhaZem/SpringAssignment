package com.example.shopping.aspect;

import com.example.shopping.entity.Product;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class RepoLoggingAspect {
    private static Logger logger = Logger.getLogger(RepoLoggingAspect.class.getName());
    @Around("execution(* com.example.shopping.repository.*.*(..))")
    public Object aroundGetFortune(ProceedingJoinPoint joinPoint) throws Throwable{
        String method=joinPoint.getSignature().toShortString();
        logger.info("Around method "+ method);
        long begin = System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("Duration: "+(end-begin)/1000);
        return res;
    }
    @AfterReturning(pointcut = "execution(* com.example.shopping.repository.ProductRepo.*(..))", returning = "res")
    public void afterFindingProduct(JoinPoint joinPoint, List<Product> res){
        String method=joinPoint.getSignature().toShortString();
        logger.info("After returning from method "+ method);
        logger.info("Result is: "+res);
        logger.info("Now Result is: "+res);
    }

}
