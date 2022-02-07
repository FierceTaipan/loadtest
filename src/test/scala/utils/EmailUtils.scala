package utils

object EmailUtils {
  def generate(): String = {
    StringUtils.randomStringFromCharList(length = 10, chars = 'a' to 'z')
  }
}