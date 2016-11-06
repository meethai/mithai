package edu.sjsu.mithai.mqtt

import com.google.gson.Gson
import edu.sjsu.mithai.data._
import edu.sjsu.mithai.export.ExportMessage
import edu.sjsu.mithai.graphX.GraphProcessor
import edu.sjsu.mithai.spark.{SparkStreamingObject, Store}
import org.apache.log4j.{Level, Logger}

import scala.reflect.ClassTag

class MQTTDataReceiver[D: ClassTag](val brokerUrl: String, val topic: String) {

  private val logger: Logger = Logger.getLogger(MQTTDataReceiver.this.getClass)
  Logger.getLogger("org").setLevel(Level.ERROR)
  Logger.getLogger("akka").setLevel(Level.ERROR)

  private var _serializationHelper: SerializationHelper[D] = _

  private var data: List[D] = _

  private val streamingObject = SparkStreamingObject

  private val stream = streamingObject.getStream(brokerUrl, topic)

  stream.foreachRDD(r => {
    r.collect().toList.foreach(x => println(x + "<--recieved raw"))
    data = r.collect().toList.map(_serializationHelper.deserialize(_))
    data.foreach(x => {
      //Assign Data to Graph Vertices.

      if (Store.graph != null) {
        val data: SensorData = x.asInstanceOf[SensorData]
        val vertexId: Integer = data.getId().hashCode()
        Store.graph = Store.graph.mapVertices((id, attr) => {

          if (id == vertexId) {
            (data.getId, data.getValue)
          } else {
            attr
          }
        })
      }
    })

    logger.debug("==========Graph==========")
    if (Store.graph != null) {
      Store.graph.vertices.collect().foreach(logger.debug)
      Store.graph.edges.collect().foreach(logger.debug)
    }
    logger.debug("=========================")

    if (Store.graph != null) {

      val min = GraphProcessor.min(Store.graph)
      val max = GraphProcessor.max(Store.graph)
      logger.debug("Min: " + min)
      logger.debug("Max: " + max)

      var gson:Gson = new Gson()
      var message = gson.toJson(new DataTuple(min._2._1, min._2._2))

      Store.messageStore.addMessage(new ExportMessage(message))
    }
  })

  @throws(classOf[Exception])
  def start(): Unit = {
    if (_serializationHelper == null) {
      throw new Exception("SerializationHelper cannot be null in MQTTReciever")
    }
  }

  def stop(stopSparkContext: Boolean = true): Unit = {
    stream.stop();
  }

  def awaitTermination(): Unit = streamingObject.streamingContext.awaitTermination()

  def setSerializationHelper(serializationHelper: SerializationHelper[D]): Unit = {
    _serializationHelper = serializationHelper
  }
}