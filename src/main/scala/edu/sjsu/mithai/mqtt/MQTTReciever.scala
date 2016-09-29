package edu.sjsu.mithai.mqtt


import edu.sjsu.mithai.config.Configuration
import edu.sjsu.mithai.config.MithaiProperties._
import edu.sjsu.mithai.data.{AvroSerializationHelper, SerializationHelper}
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaustubh on 9/17/16.
  */
class MQTTReciever(val brokerUrl: String, val topic: String) {
  private val logger: Logger = Logger.getLogger(this.getClass)

  private var _sh : SerializationHelper = getDefaultSeriailzationHelper()

  private val sparkConf = new SparkConf()
    .setAppName("MQTTWordCount")
    .setMaster("local[2]")
  logger.debug("setting spark context")
  private val _ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(2))
  private val lines = MQTTUtils.createStream(_ssc, brokerUrl, topic)

  private var data :List[Any] = null
  lines.foreachRDD(r=>{
    data=r.collect().toList.map(_sh.deserialize)
    //TODO sendToGraphProcessor(data)
    data.foreach(println)
  })

  _ssc.start()

  def ssc = _ssc

  def setSerializationHelper(serializationHelper: SerializationHelper) :Unit ={ _sh= serializationHelper}

  private def getDefaultSeriailzationHelper() = {val avro=new AvroSerializationHelper;avro.loadSchema("sensor.json");avro}

}

object MQTTReciever{
  def main(args: Array[String]): Unit = {
    val config = new Configuration(getClass.getClassLoader.getResource("application.properties").getFile)
    val mr = new MQTTReciever(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC))
    val avro=new AvroSerializationHelper
    avro.loadSchema("sensor.json")
    mr.setSerializationHelper(avro)
    mr.ssc.awaitTermination()
  }
}