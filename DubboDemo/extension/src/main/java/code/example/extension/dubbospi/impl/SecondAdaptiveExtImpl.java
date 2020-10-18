package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IAdaptiveExt;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;


/**
 * @author wq
 */
public class SecondAdaptiveExtImpl implements IAdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "Second:" + msg;
    }
}
