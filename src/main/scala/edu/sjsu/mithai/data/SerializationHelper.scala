package edu.sjsu.mithai.data

/**
  * Created by kaustubh on 9/29/16.
  */
trait SerializationHelper {

  def serialize(data : Any) : String

  def deserialize (string: String): Any

}
