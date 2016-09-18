package edu.sjsu.mithai.mqtt

import edu.sjsu.mithai.data.{SerialisableData, AbstractData, TemperatureData}
import org.eclipse.paho.client.mqttv3.{MqttException, MqttMessage, MqttClient}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


/**
  * Created by kaustubh on 9/17/16.
  */

class MQTTPublisher(brokerUrl: String) {

  val persistence = new MemoryPersistence()
  var client: MqttClient = null
  var topic: String = null
  client = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence)

  def sendDataToTopic(aData: SerialisableData, topic: String) = {
    try {

      client.connect()
      val msgtopic = client.getTopic(topic)
      val message = new MqttMessage(aData.getJsonBytes())
      msgtopic.publish(message)

    }
    catch {
      case e: MqttException if e.getReasonCode == MqttException.REASON_CODE_MAX_INFLIGHT =>
        println("Queue is full, wait for to consume data from the message queue")
      case e: MqttException => println("Exception Caught: " + e)
    }
    finally {
      if (client != null) {
        client.disconnect()
      }
    }
  }

  object MQTTPublisher {
    def main(args: Array[String]) {
      if (args.length < 2) {
        System.err.println("Usage: MQTTPublisher <MqttBrokerUrl> <topic>")
        System.exit(1)
      }

      val Seq(brokerUrl, topic) = args.toSeq

      var client: MqttClient = null

      try {
        val persistence = new MemoryPersistence()
        client = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence)

        client.connect()

        val msgtopic = client.getTopic(topic)
        val msgContent = "hello mqtt demo for spark streaming"
        //      val message = new MqttMessage(msgContent.getBytes("utf-8"))
        var abstractData: AbstractData = null
        abstractData = new TemperatureData()
        var message = new MqttMessage(abstractData.getJsonBytes())

        while (true) {
          try {
            msgtopic.publish(message)
            println("Published data. topic: ${msgtopic.getName()}; Message: $message")
          } catch {
            case e: MqttException if e.getReasonCode == MqttException.REASON_CODE_MAX_INFLIGHT =>
              Thread.sleep(1000)
              println("Queue is full, wait for to consume data from the message queue")
          }
        }
      } catch {
        case e: MqttException => println("Exception Caught: " + e)
      } finally {
        if (client != null) {
          client.disconnect()
        }
      }
    }

  }


}
