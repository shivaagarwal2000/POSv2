package com.pos.service;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

//@WebAppConfiguration("src/test/webapp")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QaConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration("/employee-spring-full/src/test/resources/com/pos/test.properties")
@Transactional
public abstract class AbstractUnitTest {
//@WebAppConfiguration("/employee-spring-full/src/test/resources/com/increff/employee/test.properties")
}
