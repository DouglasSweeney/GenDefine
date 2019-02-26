package main.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The log4j2 instance.
 *
 * @version release-1.0.0 Initial version
 * @author D.K.Sweeney 1/21/2016
 *
 */
public class ApplicationLogger {
  
  /**
   * This variable writes to the log.
   *
   * @var log             The current log instance.
   */
  private static Logger log;

  /**
   * The constructor.
   */
  public ApplicationLogger() {
    super();
    trace("Entered logger constructor");
    
    log = LogManager.getLogger(this);
    
    trace("Leaving logger constructor");
  }
  
  /**
   * Write a message to the trace log file.
   * 
   * @param message The message to write 
   */
  public void trace(final String message) {
    if (log != null) { 
      log.trace(message);
    }
  }

  /**
   * Write a message to the error log file.
   * 
   * @param message The message to write 
   */
  public void error(final String message) {
    if (log != null) {
      log.error(message);
    }
  }
}
