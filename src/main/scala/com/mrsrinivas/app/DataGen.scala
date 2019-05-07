/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mrsrinivas.app

import ch.cern.sparkmeasure.StageMetrics
import com.mrsrinivas.util.EmployeeDataGen
import com.mrsrinivas.util.SizeUtils._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object DataGen {

  def main(args: Array[String]): Unit = {
    if (args.length < 3) {
      println("Usage:")

      println("spark-submit " +
        "--class com.mrsrinivas.app.DataGen " +
        "spark-bench-1.0-fat.jar " +
        "[total-output-size M|G|T|P] [number-of-files] [output-directory]")
      println(" ")
      println("Example:")
      println("spark-submit " +
        "--class com.mrsrinivas.app.DataGen " +
        "spark-bench-1.0-fat.jar " +
        "100G 200 file:///scratch/username/datagen_in")
      println(" ")

      System.exit(0)
    }

    // Process command line arguments
    val outputSizeInBytes = sizeStrToBytes(args(0))
    val parts = args(1).toLong
    val outputPath = args(2)

    val size = sizeToSizeStr(outputSizeInBytes)

    val conf = new SparkConf()
      .setAppName(s"DataGen ($size) in $parts")
      .set("spark.default.parallelism", s"$parts")

    val spark = SparkSession.builder
      .config(conf)
      .getOrCreate

    val sc = spark.sparkContext

    import spark.implicits._

    val initialDataSet = (1L to parts).map(id => {
      (new EmployeeDataGen).getRecord
    }).toDF()

    val approxRecordSizeInBytes = 41

    val recordsPerPartition = outputSizeInBytes / parts / approxRecordSizeInBytes
    val numRecords = recordsPerPartition * parts

    println("===========================================================================")
    println("===========================================================================")
    println(s"Input size(total): $size")
    println(s"Total number of records: $numRecords")
    println(s"Number of output partitions: $parts")
    println("Number of records/output partition: " + (numRecords / parts))
    println("Record size in bytes: ~ " + approxRecordSizeInBytes)
    println("Output path: " + outputPath)
    println("===========================================================================")
    println("===========================================================================")

    assert(recordsPerPartition < Long.MaxValue,
      s"records per file > ${Long.MaxValue}." +
        s" Consider increasing more [number-of-files] to spark-submit")


    val bcRecordsPerPartition = sc.broadcast(recordsPerPartition)

    val fullDataSet = initialDataSet.mapPartitions(df => {
      (new EmployeeDataGen).getRecords(bcRecordsPerPartition.value).toIterator
    })

    val stageMetrics = StageMetrics(spark)

    stageMetrics.runAndMeasure {
      fullDataSet.write
        .format("csv")
        .option("header", "true")
        .mode("overwrite")
        .save(outputPath + "/employees")
    }

    stageMetrics.createStageMetricsDF()
      .coalesce(1)
      .write
      .format("csv")
      .option("header", "true")
      .mode("overwrite")
      .save(outputPath + "/stage-metrics")
  }

}
