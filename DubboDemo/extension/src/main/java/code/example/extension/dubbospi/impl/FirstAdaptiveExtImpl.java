package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IAdaptiveExt;
import org.apache.dubbo.common.URL;


/**
 * @author wq
 */
public class FirstAdaptiveExtImpl implements IAdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "First:" + msg;
    }
}
