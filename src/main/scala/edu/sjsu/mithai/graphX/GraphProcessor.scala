package edu.sjsu.mithai.graphX

import com.google.gson.Gson
import edu.sjsu.mithai.util.TaskManager
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib.ShortestPaths

import scala.collection.JavaConverters._

object GraphProcessor {

  def min(graph: Graph[(String, Double), PartitionID]): Unit = {
    var minV = graph.vertices.min()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })

    var gson = new Gson()
    callHandlers("min", gson.toJson(minV))

  }

  def max(graph: Graph[(String, Double), PartitionID]): Unit = {
    var maxV = graph.vertices.max()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })

    var gson = new Gson()
    callHandlers("max", gson.toJson(maxV))
  }

  def shortestPath(graph: Graph[(String, Double), PartitionID], entry: String): Unit = {
    val firstVId = entry.hashCode
    println("Vertex ID --->" + firstVId)
    val vert: Seq[VertexId] = graph.vertices.collect().map(x=>x._1).toSeq
    //VertexIDs of only available slots to park
    val available: Seq[VertexId] = graph.vertices.collect().filter(x => x._2._2.!=(1)).map(x => x._1).toSeq
    println("Available Slots-->" + available)
    val result = ShortestPaths.run(graph, available)
    if (result.vertices != null && result.vertices.count() > 1) {
      val shortPath = result.vertices.filter(
        {
          case (vid, path) => {
            vid == firstVId
          }
        }).first()._2
      println("Shortest Path Map---->" + shortPath.toString())
      //Finds the non zero min value
      var second = shortPath.filterNot(_._2 == 0).min
      println("Shortest Path from vertices " + firstVId + " is: " + second._1 + " with length: " + second._2)
      var gson = new Gson()
      callHandlers("ShortestPath", gson.toJson(second))
    }
  }

  def average(graph: Graph[(String, Double), PartitionID]): Unit = {
    var average = graph.vertices.map(_._2._2).sum() / graph.vertices.count()
    var gson = new Gson()
    callHandlers("average", gson.toJson(average))
  }

  def callHandlers(caller: String, msg: String) = {
    TaskManager.getInstance().getHandlers.asScala.foreach(e => e.handle(caller, msg))
  }

  def graphOperation(f:Graph[(String, Double), PartitionID] => (VertexId, (String, Double)), graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    if (graph!=null) {
      val result = f(graph);
      return result;
    }
    else return f(graph);
  }
}


