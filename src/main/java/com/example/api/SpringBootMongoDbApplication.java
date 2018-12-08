package com.example.api;


import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.api.dao.RoleRepository;
import com.example.api.model.FileStorageProperties;
import com.example.api.model.Role;
@CrossOrigin(origins = {"http://localhost:4200"},
maxAge = 4800, allowCredentials = "false") 
@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class SpringBootMongoDbApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongoDbApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
	@Bean
	  public MultipartConfigElement multipartConfigElement() {
	      MultipartConfigFactory factory = new MultipartConfigFactory();
	      factory.setMaxFileSize("100Mb");
	      return factory.createMultipartConfig();
	  }
//	@Bean
//	 public MultipartResolver multipartResolver() {
//	     org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
//	     multipartResolver.setMaxUploadSize(1000000);
//	     return multipartResolver;
//	 }
//	@Bean
//	  public MultipartConfigElement multipartConfigElement() {
//	      MultipartConfigFactory factory = new MultipartConfigFactory();
//	      factory.setMaxFileSize("100Mb");
//	      return factory.createMultipartConfig();
//	  }
//	@Bean
//	  public MultipartConfigElement multipartConfigElement() {
//	      MultipartConfigFactory factory = new MultipartConfigFactory();
//	      factory.setMaxFileSize("100Mb");
//	      return factory.createMultipartConfig();
//	  }
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000000);
//        return multipartResolver;
//    }
//    @Bean
//    public CommonsMultipartResolver filterMultipartResolver() {
//        return new CommonsMultipartResolver();
//    }

//    @Bean
//    @Order(0)
//    public MultipartFilter multipartFilter() {
//        return new MultipartFilter();
//    }
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {
 
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
 
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
 
        return mongoTemplate;
 
    }
    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
            Role clientRole = roleRepository.findByRole("CLIENT");
            if (clientRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("CLIENT");
                roleRepository.save(newUserRole);
            }
        };

    }
    
}
     
