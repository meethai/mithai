//package edu.sjsu.mithai.publisher
//
//import edu.sjsu.mithai.config.{Configuration, MithaiProperties}
//import edu.sjsu.mithai.data.Streamable
//import edu.sjsu.mithai.mqtt.MQTTPublisher
//
///**
//  * Created by kaustubh on 9/17/16.
//  */
//class Publisher {
//  private val config: Configuration = new Configuration("/Users/kaustubh/295B/mithai/src/main/resources/application.properties")
//  private val mqttPublisher = new MQTTPublisher(config.getProperty(MithaiProperties.MQTT_BROKER))
//  private val topic = config.getProperty(MithaiProperties.MQTT_TOPIC)
//
//  def publish(data: Streamable) =
//    mqttPublisher.sendDataToTopic(data, topic)
//
//}
