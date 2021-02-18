/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package code.example.dubbo.injvm;

import code.demo.DemoService;
import code.example.extension.dubbospi.IAdaptiveExt;
import code.example.extension.dubbospi.IDubboRepository;
import code.example.extension.javaspi.IJavaRepository;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;


@DubboService
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());

        ServiceLoader<IJavaRepository> serviceLoader = ServiceLoader.load(IJavaRepository.class);
        Iterator<IJavaRepository> it = serviceLoader.iterator();
        while (it != null && it.hasNext()){
            IJavaRepository demoService = it.next();
            demoService.save(name);
        }

        ExtensionLoader<IDubboRepository> extensionLoader = ExtensionLoader.getExtensionLoader(IDubboRepository.class);
        IDubboRepository extension = extensionLoader.getExtension("dmysql");
        extension.insert(name);

        ExtensionLoader<IAdaptiveExt> extExtensionLoader = ExtensionLoader.getExtensionLoader(IAdaptiveExt.class);
        IAdaptiveExt adaptiveExt = extExtensionLoader.getAdaptiveExtension();
//        URL url = URL.valueOf("test://localhost/test?adaptiveName=first");
//        System.out.println(adaptiveExt.echo("d", url));
//        URL url2 = URL.valueOf("test://localhost/test?adaptiveName=second");
//        System.out.println(adaptiveExt.echo("d", url2));
        URL url3 = URL.valueOf("test://localhost/test");
        System.out.println("DirectRun:" + adaptiveExt.echo("d", url3));

        // 等同于
        URL url4 = URL.valueOf("test://localhost/test");
        String extName = url4.getParameter("adaptiveName", "third");
        System.out.println("ExtName:" + extName);
        ExtensionLoader<IAdaptiveExt> loader = ExtensionLoader.getExtensionLoader(IAdaptiveExt.class);
        IAdaptiveExt adaptiveExt2 = loader.getExtension(extName);
        // extension返回的结果为PayWrapper1
        System.out.println("RunByName" + adaptiveExt2.echo("d", url4));

        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }

}