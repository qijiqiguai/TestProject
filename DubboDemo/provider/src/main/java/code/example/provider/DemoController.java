package code.example.provider;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/common")
    @ResponseBody
    public String commonApi() {
        return "Common API";
    }

}
