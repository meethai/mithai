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

  client.connect()

  def sendDataToTopic(aData: Streamable, topic: String): Unit = {
      val msgtopic = client.getTopic(topic)
      val message = new MqttMessage(aData.getJsonBytes())
      msgtopic.publish(message)
  }

  def sendDataToTopic(data: String, topic: String): Unit = {
    val msgTopic = client.getTopic(topic)
    val message = new MqttMessage(data.getBytes())

    msgTopic.publish(message)
  }

  def sendDataToTopic(aData: Seq[Streamable], topic: String): Unit = {

      val msgtopic = client.getTopic(topic)
      aData.foreach(data => msgtopic.publish(new MqttMessage(data.getJsonBytes())))
   }


}
