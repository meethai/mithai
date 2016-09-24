package edu.sjsu.mithai.data


import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

/**
  * Created by kaustubh on 9/18/16.
  */
abstract class AbstractData extends Streamable {

  def getJsonBytes(): Array[Byte] = getFrame().getBytes

  def getFrame() = this.getClass.getName+"-!-"+getJson()

  def getJson():String = gson.toJson(this)

  def gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

}
