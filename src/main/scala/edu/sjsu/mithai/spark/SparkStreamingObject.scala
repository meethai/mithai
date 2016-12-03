package edu.sjsu.mithai.spark

import edu.sjsu.mithai.data.SensorData
import edu.sjsu.mithai.export.{ExportMessage, HttpExportMessage, MessageStore}
import edu.sjsu.mithai.mqtt.MQTTDataReceiver
import org.apache.spark.graphx.{Graph, GraphXUtils, PartitionID}
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by kaustubh on 10/12/16.
  */
object SparkStreamingObject{

  //TODO get from prop file
  val appName: String = "MQTTReceiver"
  val sparkConf = new SparkConf()
    .setAppName(appName)
    .setMaster("local[5]")

  sparkConf.set("spark.scheduler.mode", "FAIR")
  sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  GraphXUtils.registerKryoClasses(sparkConf)

  //TODO: add registration method for custom classes
  sparkConf.registerKryoClasses(Array(
    classOf[org.apache.avro.generic.GenericData.Record],
    classOf[org.apache.avro.generic.GenericRecord],
    classOf[edu.sjsu.mithai.data.AvroGraphMetadata],
    classOf[Object],
    classOf[SensorData],
    classOf[MQTTDataReceiver[_]]
  ))

  var streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(10))
  val sparkContext: SparkContext = streamingContext.sparkContext
  //  streamingContext.awaitTermination();
  def getStream(brokerUrl: String,
                topic: String,
                ssc:StreamingContext = streamingContext): ReceiverInputDStream[String] =
    MQTTUtils.createStream(ssc, brokerUrl, topic)
}

object Store {
  var httpMessageStore = new MessageStore[HttpExportMessage](10)
  var messageStore = new MessageStore[ExportMessage](10)
  var mqttMessageStore = new MessageStore[(String, String)](100)
  var graph: Graph[(String, Double), PartitionID] = _
}
