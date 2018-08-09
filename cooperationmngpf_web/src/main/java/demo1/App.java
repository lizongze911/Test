package demo1;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;





@SpringBootApplication
@Configuration
@ComponentScan(basePackages="demo1")
@EnableTransactionManagement
public class App extends SpringBootServletInitializer{
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
//����ע��
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }
       
    
}