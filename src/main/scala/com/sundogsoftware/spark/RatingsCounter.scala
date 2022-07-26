package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.log4j._

/** Count up how many of each star rating exists in the MovieLens 100K data set.
  */
object RatingsCounter extends App {

  // Set the log level to only print errors
  Logger.getLogger("org").setLevel(Level.ERROR)

  // Create a SparkContext using every core of the local machine, named RatingsCounter
  new SparkContext("local[*]", "RatingsCounter")
    // Load up each line of the ratings data into an RDD
    .textFile("data/ml-100k/u.data")
    // Convert each line to a string, split it out by tabs, and extract the third field.
    // (The file format is userID, movieID, rating, timestamp)
    .map(_.split("\t")(2))
    // Count up how many times each value (rating) occurs
    .countByValue()
    // Sort the resulting map of (rating, count) tuples
    .toSeq
    .sortBy(_._1)
    // Print each result on its own line.
    .foreach(println)

}
