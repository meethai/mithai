package edu.sjsu.mithai.publisher

import edu.sjsu.mithai.PropertiesValues
import edu.sjsu.mithai.data.{Streamable, AbstractData}
import edu.sjsu.mithai.mqtt.MQTTPublisher

/**
  * Created by kaustubh on 9/17/16.
  */
class Publisher {
  private val mqttPublisher = new MQTTPublisher(PropertiesValues.mqttBrokerUrl)
  private val topic = PropertiesValues.mqttTopic

  def publish(data: Streamable) =
    mqttPublisher.sendDataToTopic(data, topic)

}
