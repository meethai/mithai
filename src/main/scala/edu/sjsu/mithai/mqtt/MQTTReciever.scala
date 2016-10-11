package edu.sjsu.mithai.mqtt


import edu.sjsu.mithai.config.Configuration
import edu.sjsu.mithai.config.MithaiProperties._
import edu.sjsu.mithai.data.{AvroSerializationHelper, SerializationHelper}
import edu.sjsu.mithai.graphX.{GraphCreator, GraphProc}
import org.apache.avro.generic.GenericRecord
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.reflect.ClassTag

/**
  * Created by kaustubh on 9/17/16.
  */
class MQTTReciever[D: ClassTag](val brokerUrl: String, val topic: String) {
  private val logger: Logger = Logger.getLogger(this.getClass)
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  private var _serializationHelper: SerializationHelper[D] = _

  logger.debug("setting spark context")

  private var data: List[D] = _

  private val streamingObject = new StreamingObject("MQTTReceiver@" + brokerUrl + "-" + topic)

  val gc = new GraphCreator

  val gp = new GraphProc

  private val stream = streamingObject.getStream(brokerUrl, topic)
  stream.foreachRDD(r => {
    data = r.collect().toList.map(_serializationHelper.deserialize(_))
    //TODO sendToGraphProcessor(data)
    //data.foreach(x=>println(x+"<--mb rocks"))
    val graph = gc.createGraph(data,streamingObject.ssc.sparkContext)
    gp.process(graph)

  })

  /**
    * This method needs to be called explicitly from now on to start the spark context.
    *
    * @throws java.lang.NullPointerException if the Serilization helper is not set
    */
  @throws(classOf[NullPointerException])
  def start(): Unit = {
    if (_serializationHelper == null) {
      throw new NullPointerException("SerializationHelper cannot be null in MQTTReciever")
    }
    streamingObject.ssc.start()
  }

  /**
    * This method stops the Spark Streaming (not Graceful)
    */
  def stop(stopSparkContext: Boolean = true): Unit = streamingObject.ssc.stop(stopSparkContext)

  /**
    * Wait for the execution to stop. Any exceptions that occurs during the execution
    * will be thrown in this thread.
    */
  def awaitTermination(): Unit = streamingObject.ssc.awaitTermination()

  /**
    * Sets the Serilization Helper required to be used for de-serializing the input stream to type D
    *
    * @param serializationHelper
    */
  def setSerializationHelper(serializationHelper: SerializationHelper[D]): Unit = {
    _serializationHelper = serializationHelper
  }

}

class StreamingObject(appName: String = "MQTTReceiver") {
  val sparkConf = new SparkConf()
    .setAppName(appName)
    .setMaster("local[2]")
  sparkConf.set("spark.scheduler.mode", "FAIR")
  val ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(2))

  def getStream(brokerUrl: String, topic: String) = MQTTUtils.createStream(ssc, brokerUrl, topic)
}

object MQTTReciever {
  def main(args: Array[String]): Unit = {
    val config = new Configuration(getClass.getClassLoader.getResource("application.properties").getFile)
    val mr = new MQTTReciever[GenericRecord](config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC))
    val avro = new AvroSerializationHelper
    avro.loadSchema("sensor.json")
    mr.setSerializationHelper(avro)
    mr.start()
    mr.awaitTermination()
  }
}