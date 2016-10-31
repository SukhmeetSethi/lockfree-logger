package org.ss.logger.lockfree;

import java.util.stream.LongStream;

import org.apache.log4j.Logger;
import org.ss.logger.lockfree.LockFreeLog4jLogger;
import org.ss.logger.lockfree.domain.LockFreeMessage;
import org.ss.logger.lockfree.vertx.VertxEnvironment;

public class SpeedyPerformanceComparison {

	static final Logger logger = Logger.getLogger(LockFreeMessage.class);
	static final LockFreeLog4jLogger speedyLogger = LockFreeLog4jLogger.getLogger(SpeedyPerformanceComparison.class);
	static long numberOfLogs = 5000000;
	
	public static void main(String[] args) {
		System.out.println("starting performance comparison....");
		System.out.println("We will first log "+numberOfLogs+" messages using Log4j Logger :");
		long timeStartForLog4j = System.currentTimeMillis();
		logger.info("Log4j start :" + timeStartForLog4j );
		LongStream.range(1, numberOfLogs).forEach(count -> {
			logger.info(System.currentTimeMillis() + "-" +count);
		});
		long timeEndForLog4j = System.currentTimeMillis();
		logger.info("Log4j end :" + timeEndForLog4j );
		
		System.out.println("Log4j logger completed in :" + (timeEndForLog4j - timeStartForLog4j)/1000 + " seconds");
		
		System.out.println("Now we will log "+numberOfLogs+" messages using Speedy Logger :");
		long timeStartForSpeedy = System.currentTimeMillis();
		logger.info("speedy start :" + timeStartForSpeedy );
		LongStream.range(1, numberOfLogs).forEach(count -> {
			speedyLogger.info(System.currentTimeMillis() + "-" +count);
		});
		long timeEndForSpeedy = System.currentTimeMillis();
		System.out.println("Speedy logger completed in :" + (timeEndForSpeedy - timeStartForSpeedy)/1000 + " seconds");
		VertxEnvironment.destroy();
		
	}
	
}
