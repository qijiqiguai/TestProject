package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IAdaptiveExt;
import org.apache.dubbo.common.URL;


/**
 * @author wq
 *
 * Wrapper 机制不是通过注解实现的，而是通过一套 Wrapper 规范实现的。
 * Wrapper 类在定义时需要遵循如下规范。
 *
 * 该类要实现 SPI 接口
 * 该类中要有 SPI 接口的引用
 * 该类中必须含有一个含参的构造方法且参数只能有一个类型为SPI借口
 * 在接口实现方法中要调用 SPI 接口引用对象的相应方法
 * 该类名称以 Wrapper 结尾
 */
public class AdaptiveExtFirstWrapper implements IAdaptiveExt {
    private IAdaptiveExt iAdaptiveExt;

    public AdaptiveExtFirstWrapper(IAdaptiveExt iAdaptiveExt) {
        this.iAdaptiveExt = iAdaptiveExt;
    }

    @Override
    public String echo(String msg, URL url) {
        System.out.println("Before Echo Run");
        String originalRes = iAdaptiveExt.echo(msg + " ModifiedParam", url);
        System.out.println("Before Echo Run");
        return "ModifiedRes:" + originalRes;
    }
}
