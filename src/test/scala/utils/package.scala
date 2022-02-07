import ch.qos.logback.classic.{Level, Logger}
import org.slf4j.LoggerFactory

package object utils {

  def tuneLogging(clazz: String, level: Level): Unit = {
    LoggerFactory.getLogger(clazz)
      .asInstanceOf[Logger]
      .setLevel(level)
  }
}
