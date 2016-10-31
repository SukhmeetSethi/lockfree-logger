package org.ss.logger.lockfree;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ss.logger.lockfree.LockFreeLog4jLogger;

public class LockFreeLoggerTest {
	
	private LockFreeLog4jLogger logger;
	File logFile;
	Scanner scanner;

	@Before
	public void initialize(){
		logger = LockFreeLog4jLogger.getLogger(this.getClass());
		logFile = new File("speedylog4j.log");
	}
	
	@Test
	public void testLoggingInfo(){
		UUID randomUUID = UUID.randomUUID();
		logger.info(randomUUID);
		assertTrue(isLoggingSuccessful(randomUUID.toString()));
	}
	
	
	@Test
	public void testLoggingDebug(){
		UUID randomUUID = UUID.randomUUID();
		logger.debug(randomUUID);
		assertTrue(isLoggingSuccessful(randomUUID.toString()));
	}
	

	private boolean isLoggingSuccessful(String message) {
		boolean isSuccessful = false;
		if(scanner == null){
			try {
				scanner = new Scanner(logFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		while(scanner.hasNextLine()){
			scanner.nextLine();
			if(scanner.findInLine(message) != null){
				isSuccessful = true;
			}
		}
		return isSuccessful;
	}
	
	@After
	public void destroy(){
			logFile.delete();
	}

}
