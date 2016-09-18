package edu.sjsu.mithai.data

import com.google.gson.Gson
import com.google.gson.annotations.Expose

/**
  * Created by kaustubh on 9/17/16.
  */


trait SerialisableData {
  def getJsonBytes(): Array[Byte]
}

abstract class AbstractData extends SerialisableData {
  def getJsonBytes(): Array[Byte] = gson.toJson(this).getBytes

  @Expose(deserialize = false)
  def gson = new Gson()

}

