package demo1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import demo1.User;
import demo1.UserMapper;



@Component
@Transactional
public class HelloService{
    
	@Autowired
	private UserMapper userMapper;
	
    public List hello(String username){
    	List h = new ArrayList();
    	h.add("duwenyi");
    	h.add("zhangshan");
        return h;

    }
    
    public Map hello2(String username){
    	Map h = new HashMap();
    	h.put("duwenyi","duwenyi2");
    	h.put("zhangshan","zhangshan2");
        return h;

    }
    
    
	public List getUsers() {
		List users=userMapper.getAll();
		return users;
	}

	public List getPage(int nowPage,int pageSize) {
	    PageHelper.startPage(nowPage,pageSize);  
		List users=userMapper.getPage();
		return users;
	}
	
	
	
	public User getOne() {
		User user=userMapper.getOne(1);
		return user;
	}
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void  insert() {
		userMapper.insert(new User(4,"duwenyi","wucuihua"));
		//userMapper.insert(new User(4,"duwenyi","wucuihua"));
	}
	
	public void  update() {
		userMapper.update(new User(1,"duwenyi1","wucuihua2"));
	}
	public void delete(int id,String name,String code) {
		userMapper.delete(new User(id,name,code));
	}
}