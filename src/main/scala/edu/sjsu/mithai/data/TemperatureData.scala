package edu.sjsu.mithai.data

import java.util

import com.google.gson.annotations.Expose

/**
  * Created by kaustubh on 9/17/16.
  */
class TemperatureData(id:String = "id",temp: String = "-273", scale: String = "kelvin") extends AbstractData {

  val ID = "id"

  val TEMP = "temp"

  val SCALE = "scale"

  @Expose
  var tempValues = new util.HashMap[String, String]()

  tempValues.put(TEMP, temp)
  tempValues.put(SCALE, scale)
  tempValues.put(ID,id)

  def updateTemp(temp: String) = tempValues.put(TEMP, temp)

  def setId(id:String) = tempValues.put(ID,id)

  def setScale(scale:String) = tempValues.put(SCALE,scale)
}