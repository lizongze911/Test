package demo1;

import java.io.File;
import java.io.IOException;


import org.springframework.web.multipart.MultipartFile;

public class FileIOUtil {
	/**
	  * 获取浏览器版本信息
	  */
	public static String getBrowserName(String agent) {
		if (agent.indexOf("msie 7") > 0) {
			return "ie7";
		} else if (agent.indexOf("msie 8") > 0) {
			return "ie8";
		} else if (agent.indexOf("msie 9") > 0) {
			return "ie9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "ie10";
		} else if (agent.indexOf("msie") > 0) {
			return "ie";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "ie11";
		} else if (agent.indexOf("mozilla") > 0) {
			return "mozilla";
		} else if (agent.indexOf("chrome") > 0) {
			return "chrome";
		} else {
			return "Others";
		}
	}
}
