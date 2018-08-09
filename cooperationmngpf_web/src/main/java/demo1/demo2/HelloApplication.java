package demo1.demo2;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageHelper;

import demo1.HelloService;
import demo1.MppUtil;
import demo1.SchProjectTask;
import demo1.User;
import demo1.UserMapper;

@Controller
@Transactional
public class HelloApplication{
    
	@Autowired
	private HelloService helloService;
	private RedisService redisService;
	
    @RequestMapping("/hello")
    @ResponseBody
    public List hello(String username){
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
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(int id,String name,String code) {
		helloService.delete(id, name, code);
	}
		
    /* * 获取file页面*/    
    @RequestMapping("/file")
    public String file(){
        return "/file";
    }
    
    /**
     * 实现文件上传
     * */
	@RequestMapping("/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam("fileName") MultipartFile file) {
		if (file.isEmpty()) {
			return "false";
		}
		String fileName = file.getOriginalFilename();
		int size = (int) file.getSize();
		System.out.println(fileName + "-->" + size);

		String path = "D:/test";
		File dest = new File(path + "/" + fileName);
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest); // 保存文件
			return "true";
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}
	
	/*实现文件下载*/
	@RequestMapping("/fileDownload")
	@ResponseBody
	public String fileDownload(HttpServletResponse response) {
		String fileName="1.txt";
		String filePath="D://test//";
		File file=new File(filePath+"/"+fileName);
		if(file.exists()) {
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;fileName"+fileName);
			byte[] buffer=new byte[1024];
			FileInputStream fis=null;
			BufferedInputStream bis=null;
			try {
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis);
				OutputStream os=response.getOutputStream();		 
						
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	/*public String fileDownload(HttpServletRequest request,HttpServletResponse response){
		String fileName = "1.txt";
		if (fileName != null) {
			String realPath = "D://test//";
			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("success");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}*/
	 @RequestMapping("/test")
	 @ResponseBody
	    public void demoTest(){
	        redisService.set("1","value22222");
	    }
	 
	/* @Autowired
	    RedisConnectionFactory factory;
	        
	 @RequestMapping("/test")
	 @ResponseBody
	    public void testRedis(){
	        //得到一个连接
	        RedisConnection conn = factory.getConnection();
	        conn.set("hello".getBytes(), "world".getBytes());
	        System.out.println(new String(conn.get("hello".getBytes())));
	    }*/
	 @RequestMapping("/uploadForm")
	 @ResponseBody
	 public String uploadFile(@RequestParam("pic")CommonsMultipartFile pic,HttpServletRequest req,HttpServletResponse response,String modelName) throws IOException{
		return null;
	 }
	 
	 @RequestMapping("/mpp")
	 @ResponseBody
	 public void mppdemo() throws FileNotFoundException {
		// TODO Auto-generated method stub
		 List<SchProjectTask> taskBeanList = MppUtil.getTaskList("D:/CEBIM使用.mpp");
	        System.out.println(taskBeanList.size());
	        for (SchProjectTask task : taskBeanList) {
	            System.out.println(task);
	            }
	 }
	 
	 
}