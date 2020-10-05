package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IDubboRepository;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DubboMysqlRepository implements IDubboRepository {
    @Override
    public void insert(String data) {
        System.out.println("Insert " + data + " to Mysql");
    }
}
