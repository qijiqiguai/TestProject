package code.demo.hytrix;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;



@RestController
@RequestMapping(value = "/contest/demo")
public class HelloController {
    //对controller层的接口做hystrix线程池隔离，可以起到限流的作用
    @HystrixCommand(
            commandKey = "helloCommand",//缺省为方法名
            threadPoolKey = "helloPool",//缺省为类名
            fallbackMethod = "fallbackMethod",//指定降级方法，在熔断和异常时会走降级方法
            commandProperties = {
                    //超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10")
            },
            threadPoolProperties = {
                    //并发，缺省为10
                    @HystrixProperty(name = "coreSize", value = "1")
            }
    )
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(HttpServletResponse httpServletResponse){
        return "Hello World!：00000";
    }

    /**
     *  降级方法，状态码返回503
     *  注意，降级方法的返回类型与形参要与原方法相同，可以多一个Throwable参数放到最后，用来获取异常信息
     */
    public String fallbackMethod(HttpServletResponse httpServletResponse,Throwable e){
        httpServletResponse.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return e.getMessage();
    }
}
