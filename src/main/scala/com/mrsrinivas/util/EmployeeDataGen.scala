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

import com.mrsrinivas.data.Employee

import scala.collection.parallel.immutable.ParSeq

class EmployeeDataGen {

  def getRecords(number: Long): ParSeq[Employee] = {
    (0L to number).par.map(index => {
      val name = RandomDataGen.getParsonName
      val email = s"$name@${RandomDataGen.getDomain}"
      Employee(name, email, RandomDataGen.getDesignation)
    })
  }

  def getRecords(number: Int): ParSeq[Employee] = this.getRecords(number.toLong)

  def getRecord(): Employee = this.getRecords(1).head

}
