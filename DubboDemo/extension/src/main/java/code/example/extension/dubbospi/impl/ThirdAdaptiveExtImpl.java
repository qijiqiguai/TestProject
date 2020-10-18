package code.example.extension.dubbospi.impl;

import code.example.extension.dubbospi.IAdaptiveExt;
import org.apache.dubbo.common.URL;


/**
 * @author wq
 */
public class ThirdAdaptiveExtImpl implements IAdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "Third:" + msg;
    }
}
