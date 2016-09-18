package edu.sjsu.mithai.data

import java.util

/**
  * Created by kaustubh on 9/17/16.
  */
class TemperatureData(temp: String = "-273", scale: String = "kelvin") extends AbstractData {
  var hm = new util.HashMap[String, String]()
  hm.put("temp", temp)
  hm.put("scale", scale)

  def updateTemp(temp: String) = {
    hm.put("temp", temp)
  }
}

//object TemperatureData{
//  def main(args: Array[String]) {
//    val td = new TemperatureData()
////    td.updateTemp("10.3")
//    println(new String(td.getJsonBytes(),"utf-8"))
//  }
//}