package org.ss.logger.lockfree;

import org.apache.log4j.Logger;
import org.ss.logger.lockfree.domain.LockFreeMessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class Log4jConsumer extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {

		vertx.eventBus().consumer("log4j-messages-info", handler -> {
			LockFreeMessage speedyMessage =  (LockFreeMessage) handler.body();
			Logger logger = speedyMessage.getLogger();
			logger.info(speedyMessage.getMessage());
			
		});

		super.start(startFuture);
	}

}
