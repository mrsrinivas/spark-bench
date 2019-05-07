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

object RandomDataGen {
  private val random = new java.util.Random

  private val domains = Array[String]("apache.org", "yahoo.com", "apple.com", "domain.com",
    "amazon.com", "google.com")

  private val designations = Array[String]("Trainee Engineer", "Software Engineer", "Sr System Analyst",
    "Programmer Analyst", "Sr Software Engineer", "Project Engineer", "Sr Project Engineer",
    "Sr Program Manager")

  private val lowers = Array[Char]('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
  private val l = lowers.length

  def getParsonName: String = {
    var rv = ""

    rv += lowers(random.nextInt(l))
    rv += lowers(random.nextInt(l))
    rv += lowers(random.nextInt(l))
    rv += lowers(random.nextInt(l))
    rv += lowers(random.nextInt(l))
    rv += lowers(random.nextInt(l))
    rv
  }

  def getDomain: String = {
    domains(random.nextInt(domains.length))
  }

  def getDesignation: String = {
    designations(random.nextInt(designations.length))
  }
}
