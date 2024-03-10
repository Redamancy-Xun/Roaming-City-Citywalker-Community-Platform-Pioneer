package com.forum.config;// package com.xiaowu.config;

// import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// /**
//  * 接口文档，生产时关闭
//  *
//  * @author yan on 2020-02-27
//  */
// @Configuration
// @EnableSwagger2
// @ConditionalOnExpression("${dev.enable:true}")//当enable为true时才选择加载该配置类
// public class Swagger2Config {

//     @Bean
//     public Docket createControllerApi() {
//         return new Docket(DocumentationType.SWAGGER_2);
//     }

//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//                 .title("TITLE")
//                 .description("DESCRIPTION")
//                 //.termsOfServiceUrl("http://124.222.112.118:8081/swagger-ui.html")//数据源
//                 .version("1.0")
//                 .build();
//     }
// }
