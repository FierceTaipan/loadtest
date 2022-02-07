package utils

import com.google.gson.Gson

class GsonUtils {

}

object GsonUtils {
  def toJson(obj: Object): String = {
    val gson = new Gson
    gson.toJson(obj)
  }
}
