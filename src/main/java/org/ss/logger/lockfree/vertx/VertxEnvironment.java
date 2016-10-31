package org.ss.logger.lockfree.vertx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.stream.IntStream;

import org.ss.logger.lockfree.Log4jConsumer;
import org.ss.logger.lockfree.domain.LockFreeMessage;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class VertxEnvironment {

	static boolean isInitialized = false;
	static Vertx vertx;

	public static void initialize() {
		if (!isInitialized) {
			vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
			vertx.eventBus().registerDefaultCodec(LockFreeMessage.class, getSpeedyMessageCodec());
			/*IntStream.range(0, 3).forEach(count -> {
			});*/
			vertx.deployVerticle(new Log4jConsumer());
			isInitialized = true;
		}
	}

	public static void destroy() {
		if (isInitialized) {
			vertx.close();
		}
	}

	private static MessageCodec<LockFreeMessage, LockFreeMessage> getSpeedyMessageCodec() {
		return new MessageCodec<LockFreeMessage, LockFreeMessage>() {

			@Override
			public void encodeToWire(Buffer buffer, LockFreeMessage rm) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream os;
				try {
					os = new ObjectOutputStream(out);
					os.writeObject(rm);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				byte[] byteArray = out.toByteArray();
				buffer.appendBytes(byteArray);
			}

			@Override
			public LockFreeMessage decodeFromWire(int pos, Buffer buffer) {

				ByteArrayInputStream in = new ByteArrayInputStream(buffer.getBytes());
				ObjectInputStream is;
				LockFreeMessage rm = null;
				try {
					is = new ObjectInputStream(in);
					rm = (LockFreeMessage) is.readObject();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return rm;
			}

			@Override
			public LockFreeMessage transform(LockFreeMessage s) {
				return s;
			}

			@Override
			public String name() {
				return "LockFreeMessage";
			}

			@Override
			public byte systemCodecID() {
				return -1;
			}
		};
	}

	public static void send(String channelName, LockFreeMessage lockFreeMessage) {
		vertx.eventBus().send(channelName, lockFreeMessage);
	}
}
