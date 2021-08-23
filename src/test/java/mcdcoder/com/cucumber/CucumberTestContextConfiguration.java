package mcdcoder.com.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import mcdcoder.com.ReactDemoApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = ReactDemoApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
