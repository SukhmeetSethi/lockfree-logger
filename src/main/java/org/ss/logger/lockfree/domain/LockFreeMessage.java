package org.ss.logger.lockfree.domain;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class LockFreeMessage implements Serializable {

	private static final long serialVersionUID = 6860288093773336899L;

	private Logger logger;
	private Object message;

	public LockFreeMessage(Logger logger, Object message) {
		super();
		this.logger = logger;
		this.message = message;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
