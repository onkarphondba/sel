package com.cglean.cloudnative.demo.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IntroduceLatency {

	private Log log = LogFactory.getLog(IntroduceLatency.class);

	@Value("${client.latency}")
	int latency;

	@Before("execution(* com.cglean.cloudnative.demo.client..*(..))")
	public void addLatencyByEnvironment(JoinPoint joinPoint) {
		if (latency > 0) {
			try {
				log.warn(String.format("Introducing latency of %s seconds", latency));
				Thread.sleep(1000 * latency);
			} catch (InterruptedException e) {
				log.error("Exception thrown while introducing latency", e);
			}
		}
	}

}
