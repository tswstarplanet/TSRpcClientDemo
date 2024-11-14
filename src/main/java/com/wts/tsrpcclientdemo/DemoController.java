/*
 * Copyright 2024 wts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wts.tsrpcclientdemo;

import com.wts.tsrpcclientdemo.tsprcclient.ProviderService;
import com.wts.tsrpc.demo.interfaces.params.Request;
import com.wts.tsrpc.demo.interfaces.params.Response;
import com.wts.tsrpc.demo.interfaces.params.SubRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/test")
    public String test(String arg1, String arg2) {
//        ProviderService providerService = applicationContext.getBean("com.wts.tsrpcclientdemo.tsprcclient.ProviderService", ProviderService.class);
//        return arg1 + ": " + arg2;
        try {
            String result = providerService.primitiveService(arg1, arg2);
            result = "Client: " + result;
            return result;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @RequestMapping("/test2")
    public Response test2(String arg1, Integer arg2) {
        try {
            Request request = new Request();
            request.setArg1(arg1);
            request.setArg2(arg2);
            Map<String, List<SubRequest>> map = new HashMap<>();
            map.put("key1", new ArrayList<>(List.of(new SubRequest("subReq1", "subReq2"), new SubRequest("subReq3", "subReq4"))));
            request.setArg3(map);
            Response response = providerService.complexService(request);
            return response;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            return null;
        }
    }
}
