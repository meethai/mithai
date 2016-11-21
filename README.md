# Project Mithai

[![Build Status](https://travis-ci.com/kaustubh-walokar/mithai.svg?token=Yu4haBFCKfHia5eAMyZo&branch=master)](https://travis-ci.com/kaustubh-walokar/mithai)

## What is Mithai?
Mithai (_me-thaa-ee_) is distributed graph processing system built using Apache Spark for IoT devices.

## Why Mithai?

1. Save network bandwidth
2. Reduced cost
3. Process graph of IoT generated data locally

## How it works?
![Workflow](./docs/images/workflow.png "Workflow")


## List of gradle tasks
 Run using ``` gradle <taskName> ``` in terminal from project root
 1. startMosquitto - starts teh Mosquitto server using the config in the resources directory
 2. runSparkServer - starts spark server using port,host specified in gradle.properties file

