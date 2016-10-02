package edu.sjsu.mithai.data

/**
  * Created by kaustubh on 9/29/16.
  */
trait SerializationHelper[D] {

  @throws(classOf[Exception])
  def serialize(data: D): String

  @throws(classOf[Exception])
  def deserialize(stream: String): D

}
