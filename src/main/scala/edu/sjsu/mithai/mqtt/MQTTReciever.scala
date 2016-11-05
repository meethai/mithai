package edu.sjsu.mithai.mqtt

import edu.sjsu.mithai.data._
import edu.sjsu.mithai.export.{ExportMessage, GraphVisualizationUtil}
import edu.sjsu.mithai.graphX.GraphCreator
import edu.sjsu.mithai.spark.{SparkStreamingObject, Store}
import org.apache.log4j.{Level, Logger}

import scala.reflect.ClassTag

class MQTTReciever[D: ClassTag](val brokerUrl: String, val topic: String) {
  private val logger: Logger = Logger.getLogger(MQTTReciever.this.getClass)
  Logger.getLogger("org").setLevel(Level.ERROR)
  Logger.getLogger("akka").setLevel(Level.ERROR)

  private var _serializationHelper: SerializationHelper[D] = _

  logger.debug("setting spark context")

  private var data: List[D] = _

  private val streamingObject = SparkStreamingObject

  private val stream = streamingObject.getStream(brokerUrl, topic)

  val gc = new GraphCreator

  stream.foreachRDD(r => {
    r.collect().toList.foreach(x=>println(x+"<--recieved raw"))
    data = r.collect().toList.map(_serializationHelper.deserialize(_))

    data.foreach(x=>{

      if(x.isInstanceOf[AvroGraphMetadata]){
        println("Metadata Task")
        var metadataVisualization:String = GraphVisualizationUtil.parseGraphTuple(x.asInstanceOf[AvroGraphMetadata])
//        logger.debug("Metadata Visualization:" + metadataVisualization)
        Store.messageStore.addMessage(new ExportMessage(metadataVisualization))
        logger.debug("Message count in export message store: " + Store.messageStore.getMessageQueue.size())
        Store.graph = gc.createMetaDataGraph(Store.graph, x.asInstanceOf[AvroGraphMetadata],streamingObject.sparkContext)
      }
    })
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
//    streamingObject.streamingContext.start()
  }

  /**
    * This method stops the Spark Streaming (not Graceful)
    */
  def stop(stopSparkContext: Boolean = true): Unit = {
    stream.stop();
  }

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