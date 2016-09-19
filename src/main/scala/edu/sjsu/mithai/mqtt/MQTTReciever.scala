package edu.sjsu.mithai.mqtt

import java.util
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaustubh on 9/17/16.
  */
object MQTTReciever {
  def main(args: Array[String]) {
    if (args.length < 2) {
      // scalastyle:off println
      System.err.println(
        "Usage: MQTTWordCount <MqttbrokerUrl> <topic>")
      // scalastyle:on println
      System.exit(1)
    }

    val Seq(brokerUrl, topic) = args.toSeq
    val sparkConf = new SparkConf()
      .setAppName("MQTTWordCount")
      .setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val lines = MQTTUtils.createStream(ssc, brokerUrl, topic, StorageLevel.MEMORY_ONLY_SER_2)

    val words = lines.flatMap(x => x.split(" "))
    words.foreachRDD(rdd => {
      var hm = new util.ArrayList[String]()
      for(item <- rdd.collect()) {
        val a = item.split("-!-")
        val data:String = a{1}
        hm.add(data)
      }

      //TODO call madhuras func here
      for(x<-hm){
        println("\t\t"+x)
      }

    })

    ssc.start()
    ssc.awaitTermination()
  }

}
