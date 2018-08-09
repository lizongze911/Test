package demo1.demo2;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;

import demo1.HelloService;
import demo1.User;
import demo1.UserMapper;

@Controller
@Transactional
public class HelloApplication{
    Logger _log = (Logger) LoggerFactory.getLogger(HelloApplication.class);
	@Autowired
	private HelloService helloService;
	
    @RequestMapping("/hello")
    @ResponseBody
    public List hello(String username){
    	_log.info("wo is hello");
        return helloService.hello(username);

    }
    
    @RequestMapping("/hello2")
    @ResponseBody
    public Map hello2(String username){
        return helloService.hello2(username);

    }
    
    
	@RequestMapping("/getAll")
	@ResponseBody
	public List getUsers() {
		return helloService.getUsers();
	}
	@RequestMapping("/getPage")
	@ResponseBody
	public List getPage(int nowPage,int pageSize) {
	    PageHelper.startPage(nowPage,pageSize);  
		return helloService.getUsers();
	}
	
	
	
	@RequestMapping("/getOne")
	@ResponseBody
	public User getOne() {
		return helloService.getOne();
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void  insert() {
		helloService.insert();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void  update() {
		helloService.update();
	}
	
    
}