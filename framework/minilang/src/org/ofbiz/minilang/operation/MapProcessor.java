/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.minilang.operation;

import java.util.*;

import javolution.util.FastList;
import org.w3c.dom.*;
import org.ofbiz.base.util.*;

/**
 * Map Processor Main Class
 */
public class MapProcessor {
    
    String name;
    List<MakeInString> makeInStrings = FastList.newInstance();
    List<SimpleMapProcess> simpleMapProcesses = FastList.newInstance();

    public MapProcessor(Element simpleMapProcessorElement) {
        name = simpleMapProcessorElement.getAttribute("name");

        for (Element makeInStringElement: UtilXml.childElementList(simpleMapProcessorElement, "make-in-string")) {
            MakeInString makeInString = new MakeInString(makeInStringElement);

            makeInStrings.add(makeInString);
        }

        for (Element simpleMapProcessElement: UtilXml.childElementList(simpleMapProcessorElement, "process")) {
            SimpleMapProcess strProc = new SimpleMapProcess(simpleMapProcessElement);

            simpleMapProcesses.add(strProc);
        }
    }

    public String getName() {
        return name;
    }

    public void exec(Map<String, Object> inMap, Map<String, Object> results, List<Object> messages, Locale locale, ClassLoader loader) {
        if (makeInStrings != null && makeInStrings.size() > 0) {
            for (MakeInString makeInString: makeInStrings) {
                makeInString.exec(inMap, results, messages, locale, loader);
            }
        }

        if (simpleMapProcesses != null && simpleMapProcesses.size() > 0) {
            for (SimpleMapProcess simpleMapProcess: simpleMapProcesses) {
                simpleMapProcess.exec(inMap, results, messages, locale, loader);
            }
        }
    }
}
