package demo1;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

//实现序列化接口
public class User implements Serializable{

	private long id;
	// 用户名称
	private String username = null;
	// 用户代码
	private String usercode = null;
	// 密码
	private String passwd = null;
	// 用户单位
	private String userunit = null;
	//用户文件
	private MultipartFile file=null;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUserunit() {
		return userunit;
	}
	public void setUserunit(String userunit) {
		this.userunit = userunit;
	}
	
	public MultipartFile getImage() {
		return file;
	}
	public void setImage(MultipartFile file) {
		this.file = file;
	}

	public User(){
	   
	}
	
	public User(long id,String username,String usercode){
	    this.id = id;
	    this.username = username;
	    this.usercode = usercode;
	}
	
}
