package com.cy.view.service.weibo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ViewApplication.class)
public class ViewApplication {

    @Autowired
    TopSearchService topSearchService;


}
