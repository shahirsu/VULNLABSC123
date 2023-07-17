package com.vulnsc.social;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class MyController {

    @GetMapping("/")
    @ResponseBody
    public Resource getPage() {
        // Path to the HTML file relative to the "resources" folder
        String filePath = "templates/index.html";

        // Load the HTML file as a resource
        Resource resource = new ClassPathResource(filePath);

        return resource;

    }
}
