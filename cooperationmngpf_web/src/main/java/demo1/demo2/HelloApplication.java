package demo1.demo2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageHelper;
import com.mysql.fabric.xmlrpc.base.Data;
/*import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.Static;*/

import demo1.FileIOUtil;
import demo1.HelloService;
import demo1.MppUtil;
import demo1.SchProjectTask;
import demo1.User;
import demo1.UserMapper;

@Controller
@Transactional
public class HelloApplication {

	@Autowired
	private HelloService helloService;
	private RedisService redisService;

	@RequestMapping("/hello")
	@ResponseBody
	public List hello(String username) {
		return helloService.hello(username);

	}

	@RequestMapping("/hello2")
	@ResponseBody
	public Map hello2(String username) {
		return helloService.hello2(username);

	}

	@RequestMapping("/getAll")
	@ResponseBody
	public List getUsers() {
		return helloService.getUsers();
	}

	@RequestMapping("/getPage")
	@ResponseBody
	public List getPage(int nowPage, int pageSize) {
		PageHelper.startPage(nowPage, pageSize);
		return helloService.getUsers();
	}

	@RequestMapping("/getOne")
	@ResponseBody
	public User getOne() {
		return helloService.getOne();
	}

	@RequestMapping("/insert")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public void insert() {
		helloService.insert();
	}

	@RequestMapping("/update")
	@ResponseBody
	public void update() {
		helloService.update();
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(int id, String name, String code) {
		helloService.delete(id, name, code);
	}

	/* * 获取file页面 */
	@RequestMapping("/file")
	public String file() {
		return "/file";
	}
	@RequestMapping("/fileUpload1")
	@ResponseBody
	public String fileUpload1(HttpServletRequest request) throws IllegalStateException,IOException
	{
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String path = "D://test" + file.getOriginalFilename();
					// 上传
					file.transferTo(new File(path));
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Spring方法的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return "true";
	}
	
	@RequestMapping("/fileUpload2")
	@ResponseBody
	public String fileUpload2(@RequestParam("file") MultipartFile file) throws IOException {
		long  startTime=System.currentTimeMillis();
		if (file.isEmpty()) {
			return "false";
		}
		String fileName = file.getOriginalFilename();//获得上传文件原名
		File fileuri = new File(fileName);
		String filepath = fileuri.getName();//从file中获取文件组件的名字
		String filename=filepath.substring(0, filepath.lastIndexOf("."));
		String suffix=filepath.substring(filepath.lastIndexOf(".") + 1);
		String path = "D://test";
		
		Calendar Cld = Calendar.getInstance();
		int YY = Cld.get(Calendar.YEAR) ;//年
		int MM = Cld.get(Calendar.MONTH)+1;//月
		int DD = Cld.get(Calendar.DATE);//日
		int HH = Cld.get(Calendar.HOUR_OF_DAY);//时
		int mm = Cld.get(Calendar.MINUTE);//分
		int SS = Cld.get(Calendar.SECOND);//秒
		int MI = Cld.get(Calendar.MILLISECOND);//毫秒
		String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		/*File dest = new File(path + "/" + filename+"_"+time+"."+suffix);*/
		File dest = new File(path+"/"+fileName);
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest); // 保存文件
			long  endTime=System.currentTimeMillis();
	        System.out.println("采用流上传的方式的运行时间："+String.valueOf(endTime-startTime)+"ms");
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

	@RequestMapping("/fileUpload3")
	@ResponseBody
	public String fileUpload3(@RequestParam("file") MultipartFile file) throws IOException{
		long startTime = System.currentTimeMillis();
		if (file.isEmpty()) {
			return "false";
		}
		String fileName = file.getOriginalFilename();
		File fileuri = new File(fileName);
		String filepath = fileuri.getName();
		String path = "D://test";
		File dest = new File(path + "/" + filepath);
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			// 获取输出流
			OutputStream os = new FileOutputStream(dest);
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			InputStream is = file.getInputStream();
			byte[] bts = new byte[1024];
			// 一个一个字节的读取并写入
			while (is.read(bts) != -1) {
				os.write(bts);
			}
			os.flush();
			os.close();
			is.close();
			long endTime = System.currentTimeMillis();
			System.out.println("采用流上传的方式的运行时间：" + String.valueOf(endTime - startTime) + "ms");
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
	/* 实现文件下载 */
	@RequestMapping("/fileDownload")
	@ResponseBody
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
		  String fileName = "1.txt";// 设置文件名，根据业务需要替换成要下载的文件名
		  if (fileName != null) {
		    //设置文件路径
		    String realPath = "D://test//";
		    File file = new File(realPath , fileName);
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
		}
	@RequestMapping("/fileDownload2")
	@ResponseBody
	public String downloadFile2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "1.txt";// 设置文件名，根据业务需要替换成要下载的文件名
		  if (fileName != null) {
		    //设置文件路径
		    String realPath = "D://test//";
		    /* 第一步:根据文件路径获取文件 */
		    File file = new File(realPath , fileName);
			try {
				if (file.exists()) {
					/* 第二步：根据已存在的文件，创建文件输入流 */
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					/* 第三步：创建缓冲区，大小为流的最大字符数 */
					byte[] buffer = new byte[inputStream.available()]; // int available() 返回值为流中尚未读取的字节的数量
					/* 第四步：从文件输入流读字节流到缓冲区 */
					inputStream.read(buffer);
					/* 第五步： 关闭输入流 */
					inputStream.close();
					String filename = file.getName();// 获取文件名
					/*把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，
					 * 中文不要太多，最多支持17个中文，因为header有150个字节限制
					 * */
					filename=new String(filename.getBytes("utf-8"), "iso8859-1");
					response.reset();
					response.setContentType("application/x-director");// 设置强制下载不打开
					/*Content-Disposition中指定的类型是文件的扩展名，
					 * 并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，
					 * 点保存后，文件以filename的值命名，保存类型以Content中设置的为准。
					 * 注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。  
					 */
					response.addHeader("Content-Disposition",
							"attachment;filename=" + filename);
					response.addHeader("Content-Length", "" + file.length());
					/* 第六步：创建文件输出流 */
					OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
					/* 第七步：把缓冲区的内容写入文件输出流 */
					outputStream.write(buffer);
					/* 第八步：刷空输出流，并输出所有被缓存的字节 */
					outputStream.flush();
					/* 第九步：关闭输出流 */
					outputStream.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				return "false";
			}
		}
		return null;
	}

	/**
	 * 实现文件读取
	 * @param file
	 * @throws IOException
	 */
	@RequestMapping("/fileRead")
	@ResponseBody
	public void readfileByBytes(@RequestParam("file2") MultipartFile file,HttpServletRequest request)throws IOException {			
		String filepath = getBrowserFilepath(file, request);		
		InputStream in = null;
		try {
			System.out.println(filepath);
			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(filepath);
			int temptype;
			while ((temptype = in.read()) != -1) {
				System.out.write(temptype);
			}
		} catch (IOException e1) {
			// TODO: handle exception
			e1.printStackTrace();
			return;
		}
		try {
			System.out.println(filepath);
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");  
            // 一次读多个字节  
            byte[] tempbytes = new byte[100];  
            int byteread = 0;  
            in = new FileInputStream(filepath);   
            // 读入多个字节到字节数组中，byteread为一次读入的字节数  
            while ((byteread = in.read(tempbytes)) != -1) {  
                System.out.write(tempbytes, 0, byteread);  
            }  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}finally {  
            if (in != null) {  
                try {  
                    in.close();  
                } catch (IOException e2) {  
                }  
            }  
        }  
	}
	/**
	 * 获取不同浏览器文件的地址
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String getBrowserFilepath(MultipartFile file, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent").toLowerCase();
		String brotype = FileIOUtil.getBrowserName(agent);//获取浏览器类型
		String filename = file.getOriginalFilename();
		File fileuri = new File(filename);
		String filepath;
		//非ie浏览器，文件上传到服务器
		if (!brotype.contains("ie")) {
			filepath = fileuri.getName();
			String hostpath = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
			File dest = new File(hostpath + filepath);
			file.transferTo(dest);//文件上传到服务器
			filepath = dest.getAbsolutePath();
		}else {
			filepath=fileuri.getAbsolutePath();
		}
		return filepath;
	}
	
	@RequestMapping("/fileRead2")
	@ResponseBody
	public void readfileByChars (@RequestParam("file2") MultipartFile file, HttpServletRequest request)throws IOException{
		String filepath = getBrowserFilepath(file, request);		
		Reader reader= null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
            // 一次读一个字符  
            reader = new InputStreamReader(new FileInputStream(filepath));  
            int tempchar;  
            while ((tempchar = reader.read()) != -1) {  
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                // 但如果这两个字符分开显示时，会换两次行。  
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
                if (((char) tempchar) != '\r') {  
                    System.out.print((char) tempchar);  
                }  
            }  
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
		try {
			System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(filepath));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}
		}catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
	}
	@RequestMapping("/fileRead3")
	@ResponseBody
	public void readfileByLines(@RequestParam("file2") MultipartFile file,HttpServletRequest request) throws IOException {
		String filepath = getBrowserFilepath(file, request);	
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(filepath));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	@RequestMapping("/fileWrite")
	@ResponseBody
	public void writefile1(@RequestParam("file2") MultipartFile file,String content,HttpServletRequest request) throws IOException{
		String filepath = getBrowserFilepath(file, request);	
		content = "test\r\n";
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(filepath, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@RequestMapping("/fileWrite2")
	@ResponseBody
	public void writefile2(@RequestParam("file2") MultipartFile file,String content,HttpServletRequest request) throws IOException{
		String filepath = getBrowserFilepath(file, request);	
		content = "test\r\n";
		 try {  
	            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
	            FileWriter writer = new FileWriter(filepath, true);  
	            //filewrite写入文件
	            writer.write(content);  
	            writer.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}
	@RequestMapping("/fileWrite3")
	@ResponseBody
	public void writefile3(@RequestParam("file2") MultipartFile file,String content,HttpServletRequest request) throws IOException{
		String filepath = getBrowserFilepath(file, request);	
		content = "test\r\n";
		 try {   
	            FileWriter writer = new FileWriter(filepath,true);  
	            //bufferedwriter写入文件
	            BufferedWriter bwriter=new BufferedWriter(writer);
	            bwriter.write(content);
	            bwriter.close();
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}
	@RequestMapping("/fileWrite4")
	@ResponseBody
	public void writefile4(@RequestParam("file2") MultipartFile file,String content,HttpServletRequest request) throws IOException{
		String filepath = getBrowserFilepath(file, request);	
		System.out.println(filepath);
		content = "test\r\n";
		FileOutputStream fop=null;
		 try {   
	            fop=new FileOutputStream(filepath,true);
	            byte[] contentInBytes=content.getBytes();
	            fop.write(contentInBytes);
	            fop.flush();
	            fop.close();
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  finally {
				try {
					if(fop!=null) {
						fop.close();
					}
				}catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
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