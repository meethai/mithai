package edu.sjsu.mithai.mqtt

import java.util

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaustubh on 9/17/16.
  */
class MQTTReciever(val brokerUrl: String, val topic: String) {
  private val logger: Logger = Logger.getLogger(classOf[this])
  private val sparkConf = new SparkConf()
    .setAppName("MQTTWordCount")
    .setMaster("local[1]")
  logger.debug("setting spark context")
  private val lines = MQTTUtils.createStream(_ssc, brokerUrl, topic, StorageLevel.MEMORY_ONLY_SER_2)
  private val words = lines.flatMap(x => x.split(" "))
  private var _ssc: StreamingContext = new StreamingContext(sparkConf, Seconds(2))

  words.foreachRDD(rdd => {
    var hm = new util.ArrayList[String]()
    for (item <- rdd.collect()) {
      val a = item.split("-!-")
      val data: String = a {
        1
      }
      hm.add(data)
    }

    //TODO call madhuras func here
    for (i <- 0 to hm.size() - 1) {
      println("\t\t" + hm.get(i))
    }

  })

  _ssc.start()

  def ssc = _ssc
}
