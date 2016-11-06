package edu.sjsu.mithai.graphX

import org.apache.spark.graphx._

object GraphProcessor {

  def min(graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    return graph.vertices.min()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })
  }

  def max(graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    return graph.vertices.max()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })
  }

}


