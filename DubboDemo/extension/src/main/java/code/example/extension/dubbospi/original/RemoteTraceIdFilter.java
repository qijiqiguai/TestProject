package code.example.extension.dubbospi.original;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import java.util.UUID;

/**
 * @author wq
 */
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER}, order = -30000)
public class RemoteTraceIdFilter implements Filter {
    private static final String TRACE_ID = "TRACE_ID";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //判断是消费者  还是 服务提供者
        if ( null!=RpcContext.getContext().getUrl() && RpcContext.getContext().isConsumerSide()) {
            //消费者 将trace_id（业务流水号） set至上下文中
            String uuid = UUID.randomUUID().toString();
            System.out.println("Set TraceId From Consumer: " + uuid);
            RpcContext.getContext().setAttachment(TRACE_ID, uuid);
        } else {
            //取出业务流水号
            String traceId = RpcContext.getContext().getAttachment(TRACE_ID);
            System.out.println("Provider get TraceId: " + traceId);
        }
        try {
            return invoker.invoke(invocation);
        } finally {
            if ( null!=RpcContext.getContext().getUrl() && RpcContext.getContext().isProviderSide()) {
                // CLEAN
            }
        }
    }
}
