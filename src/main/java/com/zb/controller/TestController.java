package com.zb.controller;

import com.zb.entity.Dto;
import com.zb.entity.DtoUtil;
import com.zb.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    //测试2.0
    private static List<User> userList ;
    static {
        userList=new ArrayList<>();
        userList.add(new User(1,"张三1"));
        userList.add(new User(2,"张三2"));
        userList.add(new User(3,"张三3"));
        userList.add(new User(4,"张三4"));
    }

    @ApiOperation(value = "全部查询用户信息",notes = "用户列表")
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    @ResponseBody
    public Dto all(){

        return DtoUtil.returnSuccess("ok",userList);
    }

    @ApiOperation(value = "根据用户编号信息",notes = "用户对象")
    @ApiImplicitParam(name = "id",value = "用户的ID",required = true,dataType = "int",paramType = "path")
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Dto get(@PathVariable("id") Integer id){
        User user = null;
        for(User u : userList){
            if(u.getId()==id){
                user=u;
                break;
            }
        }
        return DtoUtil.returnSuccess("ok",user);
    }

    @ApiOperation(value = "添加用户",notes = "添加用户")
    @ApiImplicitParam(name = "user",value = "用户的实体",required = true,dataType = "User")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Dto save(@RequestBody User user){
        userList.add(user);
        return DtoUtil.returnSuccess("ok");
    }





















    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String show(){
        for (int i = 0;i<10;i++){
            kafkaTemplate.send("dmservice","dm","test---kafka-----"+i);
        }
        return "发送消息到Kafka完毕";
    }

}
