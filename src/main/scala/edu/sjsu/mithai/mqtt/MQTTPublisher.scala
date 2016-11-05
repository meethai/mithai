package edu.sjsu.mithai.mqtt

import edu.sjsu.mithai.export.MessageStore
import edu.sjsu.mithai.spark.Store
import org.apache.log4j.Logger
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttMessage}

class MQTTPublisher(brokerUrl: String) {

  val logger: Logger = Logger.getLogger(this.getClass)
  val persistence = new MemoryPersistence()
  var client: MqttClient = null
  var topic: String = null
  val messageStore: MessageStore[(String, String)] = Store.mqttMessageStore

  client = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence)

  client.connect()

  def sendDataToTopic(data: String, topic: String): Unit = {
    messageStore.addMessage((topic, data))
    println(messageStore.getMessageQueue.size())
  }

  def publishData(data:String, topic:String):Unit = {
        val msgTopic = client.getTopic(topic)
        val message = new MqttMessage(data.getBytes())
        msgTopic.publish(message)

        // To avoid burst of messages
        Thread.sleep(50)
  }
}
