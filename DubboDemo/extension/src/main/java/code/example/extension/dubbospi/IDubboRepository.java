package code.example.extension.dubbospi;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface IDubboRepository {
    void insert(String data);
}
