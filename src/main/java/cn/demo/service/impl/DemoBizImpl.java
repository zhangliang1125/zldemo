package cn.demo.service.impl;


import cn.demo.service.IDemoBiz;
import org.springframework.stereotype.Service;
/**
 * @author zhangliang
 */
@Service
public class DemoBizImpl implements IDemoBiz {

    @Override
    public String hello(String name) {
        return "hello "+name+" get success";
    }

    @Override
    public String demo(String param) {
        return "demo service param is "+param;
    }
}