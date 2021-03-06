package demo1;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.pagehelper.PageHelper;

@Configuration  
public class MyBatisConfiguration {  
     
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        //properties.setProperty("dialect","oracle");    //properties.setProperty("dialect","mysql");
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
   } 
}  