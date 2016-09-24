package edu.sjsu.mithai.data

import com.google.gson.Gson
import com.google.gson.annotations.Expose

/**
  * Created by kaustubh on 9/17/16.
  * A streamable data is one that can be converted to Bytes
  */


trait Streamable {
  def getJsonBytes(): Array[Byte]
}



