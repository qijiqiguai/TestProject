package code.example.extension.dubbospi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("third")
public interface IAdaptiveExt {
    @Adaptive(value = "adaptiveName")
    String echo(String msg, URL url);
}
