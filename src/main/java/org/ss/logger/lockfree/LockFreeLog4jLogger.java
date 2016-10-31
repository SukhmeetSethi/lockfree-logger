package org.ss.logger.lockfree;

import org.apache.log4j.Logger;
import org.ss.logger.lockfree.domain.LockFreeMessage;
import org.ss.logger.lockfree.vertx.VertxEnvironment;

public class LockFreeLog4jLogger extends Logger {

	Logger logger;

	private LockFreeLog4jLogger(Class<?> clazz) {
		super(clazz.getName());
		logger = Logger.getLogger(clazz);
		VertxEnvironment.initialize();
	}
	
	static public LockFreeLog4jLogger getLogger(@SuppressWarnings("rawtypes") Class clazz){
		return new LockFreeLog4jLogger(clazz);
	}

	public void info(Object message) {
		LockFreeMessage lockFreeMessage = new LockFreeMessage(logger,message);
		VertxEnvironment.send("log4j-messages-info", lockFreeMessage);
	}
	
	public void info(Object message, Throwable throwable) {
		LockFreeMessage lockFreeMessage = new LockFreeMessage(logger,message);
		VertxEnvironment.send("log4j-messages-info-throwable", lockFreeMessage);
	}

	public void debug(Object message) {
		LockFreeMessage lockFreeMessage = new LockFreeMessage(logger,message);
		VertxEnvironment.send("log4j-messages-debug", lockFreeMessage);
	}
	
	public void debug(Object message, Throwable throwable) {
		LockFreeMessage lockFreeMessage = new LockFreeMessage(logger,message);
		VertxEnvironment.send("log4j-messages-debug-throwable", lockFreeMessage);
	}
	
	

}
