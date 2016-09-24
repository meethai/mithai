package edu.sjsu.mithai.mqtt

import edu.sjsu.mithai.data.Streamable
import org.apache.log4j.Logger
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttException, MqttMessage}


/**
  * Created by kaustubh on 9/17/16.
  */

class MQTTPublisher(brokerUrl: String) {
  val logger: Logger = Logger.getLogger(this.getClass)
  val persistence = new MemoryPersistence()
  var client: MqttClient = null
  var topic: String = null
  client = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence)

  def sendDataToTopic(aData: Streamable, topic: String): Unit = {
    try {

      client.connect()
      val msgtopic = client.getTopic(topic)
      val message = new MqttMessage(aData.getJsonBytes())
      msgtopic.publish(message)

    }
    catch {
      case e: MqttException if e.getReasonCode == MqttException.REASON_CODE_MAX_INFLIGHT =>
        logger.error("Queue is full, wait for to consume data from the message queue", e)
      case e: MqttException => println("Exception Caught: " + e)
    }
    finally {
      if (client != null) {
        client.disconnect()
      }
    }
  }

  def sendDataToTopic(aData: Seq[Streamable], topic: String): Unit = {
    try {
      client.connect()
      val msgtopic = client.getTopic(topic)
      aData.foreach(data => msgtopic.publish(new MqttMessage(data.getJsonBytes())))
    }
    catch {
      case e: MqttException if e.getReasonCode == MqttException.REASON_CODE_MAX_INFLIGHT =>
        logger.error("Queue is full, wait for to consume data from the message queue", e)
      case e: MqttException => logger.error("Exception Caught: ", e)
    }
    finally {
      if (client != null) {
        client.disconnect()
      }
    }
  }


}
