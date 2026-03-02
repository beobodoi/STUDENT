package com.example.student.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .version("1.0.0")
                        .description("API quản lý sinh viên: CRUD, tìm kiếm, lọc GPA, kích hoạt/vô hiệu hóa"))
                .addServersItem(new Server().url("/").description("Default Server"));
    }
}