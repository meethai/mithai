package edu.sjsu.mithai.graphX

import edu.sjsu.mithai.spark.Store
import org.apache.spark.graphx.lib.ShortestPaths
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

  def shortestPath (graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) ={

    graph.vertices.first()
   // val result = ShortestPaths.run(graph, Seq(graph.vertices.collect())


  }

  def avg(): Unit ={

  }


  def graphOperation(f:Graph[(String, Double), PartitionID] => (VertexId, (String, Double)), graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    if (graph!=null) {
      val result = f(graph);
      return result;
    }
    else return f(graph);
  }

  def main(args: Array[String]) {
    graphOperation(max,Store.graph)
    graphOperation(min,Store.graph)
  }
}


