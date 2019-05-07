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
package com.mrsrinivas.util

object SizeUtils {

  def sizeStrToBytes(str: String): Long = {
    val lower = str.toLowerCase
    if (lower.endsWith("k")) {
      lower.substring(0, lower.length - 1).toLong * 1000
    } else if (lower.endsWith("m")) {
      lower.substring(0, lower.length - 1).toLong * 1000 * 1000
    } else if (lower.endsWith("g")) {
      lower.substring(0, lower.length - 1).toLong * 1000 * 1000 * 1000
    } else if (lower.endsWith("t")) {
      lower.substring(0, lower.length - 1).toLong * 1000 * 1000 * 1000 * 1000
    } else {
      // no suffix, so it's just a number in bytes
      lower.toLong
    }
  }

  def sizeToSizeStr(size: Long): String = {
    val kbScale: Long = 1000
    val mbScale: Long = 1000 * kbScale
    val gbScale: Long = 1000 * mbScale
    val tbScale: Long = 1000 * gbScale
    if (size > tbScale) {
      size / tbScale + "TB"
    } else if (size > gbScale) {
      size / gbScale + "GB"
    } else if (size > mbScale) {
      size / mbScale + "MB"
    } else if (size > kbScale) {
      size / kbScale + "KB"
    } else {
      size + "B"
    }
  }
}
