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

/**
  * Created by kaustubh on 9/17/16.
  */
class MQTTReciever[D](val brokerUrl: String, val topic: String, val ssc : StreamingContext) {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  private val logger: Logger = Logger.getLogger(this.getClass)

  private var _serializationHelper: SerializationHelper[D] = _

  logger.debug("setting spark context")

  private var data: List[D] = _

  private val streamingObject = new StreamingObject("MQTTReceiver@" + brokerUrl + "-" + topic)

  private val stream = streamingObject.getStream(brokerUrl, topic, ssc)
  val gc = new GraphCreator

  val gp = new GraphProc

  stream.foreachRDD(r => {
    data = r.collect().toList.map(_serializationHelper.deserialize(_))
    //TODO sendToGraphProcessor(data)
    data.foreach(println)
    val graph = gc.createGraph(data.map(_.toString),ssc.sparkContext)
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
    ssc.start()
  }

  /**
    * This method stops the Spark Streaming (not Graceful)
    */
  def stop(stopSparkContext: Boolean = true): Unit = ssc.stop(stopSparkContext)

  /**
    * Wait for the execution to stop. Any exceptions that occurs during the execution
    * will be thrown in this thread.
    */
  def awaitTermination(): Unit = ssc.awaitTermination()

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

  def getStream(brokerUrl: String, topic: String, ssc:StreamingContext) = MQTTUtils.createStream(ssc, brokerUrl, topic)
}

object MQTTReciever {
  def main(args: Array[String]): Unit = {
    val config = new Configuration(getClass.getClassLoader.getResource("application.properties").getFile)
    val sparkConf = new SparkConf()
      .setAppName("MQTTReceiver")
      .setMaster("local[20]")

    val ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(2))

    val mr = new MQTTReciever[GenericRecord](config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC), ssc)

    val metadataReciever = new MQTTReciever[GenericRecord](config.getProperty(MQTT_BROKER), "metadata", ssc)
    val metadataSerializer = new AvroSerializationHelper()
    metadataSerializer.loadSchema("metadata.json")
    metadataReciever.setSerializationHelper(metadataSerializer)
    metadataReciever.start()
    val avro = new AvroSerializationHelper()
    avro.loadSchema("sensor.json")
    mr.setSerializationHelper(avro)
    mr.start()
    mr.awaitTermination()
    metadataReciever.awaitTermination()
  }
}