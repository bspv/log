package com.bazzi.manager.config;

import com.bazzi.manager.util.Constant;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Resource
    private DefinitionProperties definitionProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!"prod".equals(definitionProperties.getActiveProfile()))//正式环境禁用Swagger
                .useDefaultResponseMessages(false)//缺省true，设置false时会把响应状态里除200以外的状态码去掉
                .groupName(definitionProperties.getApplicationName())//分组名
                .globalOperationParameters(buildGlobalOperationParameters())//全局参数设置
                .apiInfo(new ApiInfoBuilder()
                        .title("Finance Log Manager API")//大标题
                        .description("和信基金---日志后台管理系统---接口文档")//详细描述
                        .termsOfServiceUrl("http://wiki.keji.com/pages/viewpage.action?pageId=5178504")
                        .contact(new Contact("API组", null, null))
                        .version("1.0")//版本
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hxlc.manager.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private static List<Parameter> buildGlobalOperationParameters() {
        List<Parameter> list = new ArrayList<>();
        list.add(new ParameterBuilder().name(Constant.TOKEN_HEADER).description("动态会话dynamicToken")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        return list;
    }

}
