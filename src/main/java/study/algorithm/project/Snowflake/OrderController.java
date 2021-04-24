package study.algorithm.project.Snowflake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.algorithm.project.Snowflake.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService ;

    @RequestMapping("/snowflake")
    public String index(){
        String id =  orderService.getIDBySnowFlake() ;
        return id ;
    }

}
