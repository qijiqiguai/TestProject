package code.example.consumer;


import code.demo.DemoService;
import code.example.consumer.impl.DemoServiceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wq
 */
@RestController
public class DemoController {
    @Autowired
    DemoServiceComponent demoService;

    @RequestMapping("/common")
    @ResponseBody
    public String commonApi() {
        String result = demoService.sayHello("test");
        return "Common API:" + result;
    }

}
