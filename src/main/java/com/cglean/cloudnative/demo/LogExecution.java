package com.cglean.cloudnative.demo;

import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecution {
	@AfterReturning("execution(* com.cglean..*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		LogFactory.getLog(joinPoint.getTarget().getClass().getName()).debug("Execution completed for :" + joinPoint);
	}

}
