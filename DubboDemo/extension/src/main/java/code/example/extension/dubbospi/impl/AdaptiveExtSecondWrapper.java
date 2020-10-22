package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IAdaptiveExt;
import org.apache.dubbo.common.URL;


/**
 * @author wq
 *
 * 如果需要多个Wrapper只需要定义多个符合规范的类，并填写在服务提供者文件上即可，但是需要注意，执行的顺序为后添加的先执行。
 */
public class AdaptiveExtSecondWrapper implements IAdaptiveExt {
    private IAdaptiveExt iAdaptiveExt;

    public AdaptiveExtSecondWrapper(IAdaptiveExt iAdaptiveExt) {
        this.iAdaptiveExt = iAdaptiveExt;
    }

    @Override
    public String echo(String msg, URL url) {
        System.out.println("Second Before Echo Run");
        String originalRes = iAdaptiveExt.echo(msg, url);
        System.out.println("Second Before Echo Run");
        return originalRes;
    }
}
