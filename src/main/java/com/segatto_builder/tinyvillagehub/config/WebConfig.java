package com.segatto_builder.tinyvillagehub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Inject the path defined in application.properties
//    @Value("${app.upload.dir}")
//    private String uploadDir;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        // Convert the relative path into a URI format for the file system
//        String absolutePath = new java.io.File(uploadDir).getAbsolutePath();
//        String fileUri = "file:///" + absolutePath.replace("\\", "/");
//
//        // Map the URL path /uploads/** to the file system directory
//        // Example: http://localhost:8080/uploads/image.png will load the file from
//        // C:\Users\YourUser\...\TinyVillageHub\item-images\image.png
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations(fileUri + "/");
//    }
}