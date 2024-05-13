package com.example.examsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                //用于定义API主界面的信息
                .apiInfo(apiInfo())
                //用于控制接口被swagger做成文档
                .select()
                //要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.any())
                //扫描路径选择
                .paths(PathSelectors.any())
                .build();

    }

    /**
     * 用于定义API主界面的信息
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                //文档标题
                .title("考试系统")
                //接口概述
                .description("自定义描述")
                //联系人的信息
                .contact(new Contact("费基辉","www.ferao.com","2146321711@qq.com"))
                //版本号
                .version("1.0")
                //定义服务的域名
                .termsOfServiceUrl(String.format("url"))
                //.license("LICENSE")//证书
                //.licenseUrl("http://www.guangxu.com")//证书的url
                .build();
    }

}
