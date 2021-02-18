package code.example.dubbo.injvm;


import code.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @DubboReference(url = "injvm://127.0.0.1/code.demo.DemoService")
    private DemoService demoService;

    @RequestMapping("/injvm")
    @ResponseBody
    public String commonApi() {
        String result = demoService.sayHello("injvm Say Hello");
        return "OK";
    }



}
