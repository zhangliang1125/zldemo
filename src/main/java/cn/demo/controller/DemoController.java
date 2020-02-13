package cn.demo.controller;


import cn.demo.common.MyResult;
import cn.demo.dto.IdStringQuery;
import cn.demo.service.IDemoBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 示例demo
 * @author zhangliang
 */
@RestController
@RequestMapping("/rest/demo")
public class DemoController {
    @Autowired
    IDemoBiz demoService;

    /**
     * 示例demo get请求
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloWord(@RequestParam(value = "name", required = false) String name) {
        return demoService.hello(name);
    }

    /**
     * 示例 demo post 请求
     * 返回数据统一封装MyResult类型
     * @param query
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public MyResult save(@Valid @RequestBody IdStringQuery query) {
        return MyResult.build(()->demoService.demo(query.getId()));
    }

}
