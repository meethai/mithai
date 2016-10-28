package edu.sjsu.mithai.mqtt


import edu.sjsu.mithai.config.Configuration
import edu.sjsu.mithai.config.MithaiProperties._
import edu.sjsu.mithai.data.{AvroGraphMetadata, AvroSerializationHelper, GenericSerializationHelper, SerializationHelper}
import edu.sjsu.mithai.export.{ExportMessage, GraphVisualizationUtil}
import edu.sjsu.mithai.graphX.{GraphCreator, GraphProcessor}
import edu.sjsu.mithai.spark.{SparkStreamingObject, Store}
import org.apache.avro.generic.GenericRecord
import org.apache.log4j.{Level, Logger}

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

  private val streamingObject = SparkStreamingObject

  private val stream = streamingObject.getStream(brokerUrl, topic)
  val gc = new GraphCreator

  val gp = new GraphProcessor

  stream.foreachRDD(r => {
    r.collect().toList.foreach(x=>println(x+"<--recieved raw"))
    data = r.collect().toList.map(_serializationHelper.deserialize(_))
    //TODO sendToGraphProcessor(data)
//    data.foreach(x=>println(x+"<--"))
    data.foreach(x=>{

      if(x.isInstanceOf[AvroGraphMetadata]){
        var metadataVisualization:String = GraphVisualizationUtil.parseGraphTuple(x.asInstanceOf[AvroGraphMetadata])
        println(metadataVisualization)
        Store.messageStore.addMessage(new ExportMessage(metadataVisualization))
        println(Store.messageStore.getMessageQueue.size())
        gc.createMetaDataGraph(x.asInstanceOf[AvroGraphMetadata],streamingObject.sparkContext)
      } else {
        println(x)
      }
    })

      val graph = gc.createGraph(data, streamingObject.sparkContext)

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
    streamingObject.streamingContext.start()
  }

  /**
    * This method stops the Spark Streaming (not Graceful)
    */
  def stop(stopSparkContext: Boolean = true): Unit = streamingObject.streamingContext.stop(stopSparkContext)

  /**
    * Wait for the execution to stop. Any exceptions that occurs during the execution
    * will be thrown in this thread.
    */
  def awaitTermination(): Unit = streamingObject.streamingContext.awaitTermination()

  /**
    * Sets the Serilization Helper required to be used for de-serializing the input stream to type D
    *
    * @param serializationHelper
    */
  def setSerializationHelper(serializationHelper: SerializationHelper[D]): Unit = {
    _serializationHelper = serializationHelper
  }

}

object MQTTReciever {
  def main(args: Array[String]): Unit = {
    val config = new Configuration(getClass.getClassLoader.getResource("application.properties").getFile)

    val dataReciever = new MQTTReciever[GenericRecord](config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC))

    val metadataReciever = new MQTTReciever[Object](config.getProperty(MQTT_BROKER), "metadata")
    val metadataSerializer = new GenericSerializationHelper(AvroGraphMetadata.getClassSchema.getClass)
//    metadataSerializer.loadSchema("metadata.json")
    metadataReciever.setSerializationHelper(metadataSerializer)
    metadataReciever.start()
    val avro = new AvroSerializationHelper()
    avro.loadSchema("sensor.json")
    dataReciever.setSerializationHelper(avro)
//    dataReciever.start()
//    dataReciever.awaitTermination()
    metadataReciever.awaitTermination()
  }
}