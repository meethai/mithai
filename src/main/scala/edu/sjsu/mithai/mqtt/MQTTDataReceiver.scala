package edu.sjsu.mithai.mqtt

import java.io.Serializable
import edu.sjsu.mithai.config.MithaiProperties
import edu.sjsu.mithai.data._
import edu.sjsu.mithai.graphX.GraphProcessor
import edu.sjsu.mithai.main.Mithai
import edu.sjsu.mithai.spark.{SparkStreamingObject, Store}
import org.apache.log4j.{Level, Logger}

import scala.reflect.ClassTag

class MQTTDataReceiver[D <: Serializable : ClassTag](val brokerUrl: String, val topic: String) {

  private val logger: Logger = Logger.getLogger(MQTTDataReceiver.this.getClass)
  Logger.getLogger("org").setLevel(Level.ERROR)
  Logger.getLogger("akka").setLevel(Level.ERROR)
  private val streamingObject = SparkStreamingObject
  private val stream = streamingObject.getStream(brokerUrl, topic)
  private var _serializationHelper: SerializationHelper[D] = _
  private var data: List[D] = _

  stream.foreachRDD(r => {
    data = r.collect().toList.map(_serializationHelper.deserialize(_))
    data.foreach(x => {
      //Assign Data to Graph Vertices.
      if (Store.graph != null) {
        val d: SensorData = x.asInstanceOf[SensorData]
        val vId: Integer = d.getId().hashCode()
        //        Store.graph.vertices.filter { case (id, attr) => id == vId }.mapValues((s, v) => {
        //          (s, d.getValue)
        //        })
        Store.graph = Store.graph.mapVertices((id, attr) => {

          if (id == vId) {
            (d.getId, d.getValue)
          } else {
            attr
          }
        })
      }
    })

    //    logger.debug("==========Graph==========")
    //    if (Store.graph != null) {
    //      Store.graph.vertices.collect().foreach(logger.debug)
    //      Store.graph.edges.collect().foreach(logger.debug)
    //    }
    //    logger.debug("=========================")

    if (Store.graph != null) {
      val function = Mithai.getConfiguration.getProperty(MithaiProperties.FUNCTION_LIST)
      function.split(",").foreach(x =>
        if (x.trim.equals("min")) {
          val min = GraphProcessor.min(Store.graph)
        }
        else if (x.trim.equals("max")) {
          val max = GraphProcessor.max(Store.graph)
        }
        else if (x.trim.equals("ShortestPath")) {
          val shortestPath = GraphProcessor.shortestPath(Store.graph, Mithai.getConfiguration.getProperty("ENTRY"))
        }
        else if (x.trim.equals("average")) {
          val average = GraphProcessor.average(Store.graph)
        })
      //      logger.debug("Min: " + min)
      //      logger.debug("Max: " + max)
      //      logger.debug("Average: "+GraphProcessor.average(Store.graph))
      //      logger.debug("Shortest Path: " + GraphProcessor.shortestPath(Store.graph, "entry0"))

      //      var gson:Gson = new Gson()
      //      var message = gson.toJson(new DataTuple(min._2._1, min._2._2))
      //      Store.messageStore.addMessage(new ExportMessage(message))
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