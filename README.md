# Project Mithai

[![Build Status](https://travis-ci.com/kaustubh-walokar/mithai.svg?token=Yu4haBFCKfHia5eAMyZo&branch=master)](https://travis-ci.com/kaustubh-walokar/mithai)

## What is Mithai?

Mithai (_me-thaa-ee_) is distributed graph processing system built using Apache Spark for IoT devices. Mithai follows fog computing paradigm. 

Mithai provided end-to-end framework to generate data, stream it within cluster of IoT devices, process it using graphx or Spark job push processed output to cloud using desired exporter. Mithai also provides visualization of the graph as well as spark jobs.

## Why Mithai?

1. Save network bandwidth
2. Reduced cost
3. Process graph of IoT generated data locally

## How it works?

![Workflow](./docs/images/workflow.png "Workflow")

## Built with

* [Apache Spark](http://spark.apache.org/) - Data Processing and Streaming
* [Apache Spark GraphX](http://spark.apache.org/graphx/) - Graph Processing 
* [Apache Avro](https://avro.apache.org/) - Data Serialization
* [Mosquitto](https://mosquitto.org/) - Messaging Protocol for IoT devices
* [Gradle](https://gradle.org/) - Dependency management
* [AngularJS](https://angularjs.org/) - Front-end Framework
* [d3JS](https://d3js.org/) - Visualization Framework
* [RaspberryPI](https://www.raspberrypi.org/) - Small scale computer for Data Processing and sensor management
* [DHT11, DHT22 Sensors](https://www.adafruit.com/product/386) - Temperature and Humidity with 1% and 0.1% Resolution

## List of gradle tasks

Run using ``` gradle <taskName> ``` in terminal from project root
 1. startMosquitto -> starts teh Mosquitto server using the config in the resources directory
 2. runSparkServer -> starts spark server using port,host specified in gradle.properties file
 3. jar -> build a fat jar
 4. run -> run application
 5. temp_application '-Pmithaiargs=path_to_application_properties' -> run temperature application
 6. parking_application '-Pmithaiargs=path_to_application_properties' -> run parking application
