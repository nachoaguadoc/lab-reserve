package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class Inicio de sesion {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://1-dot-isst-labreserve.appspot.com/");
		selenium.start();
	}

	@Test
	public void testInicio de sesion() throws Exception {
		selenium.open("/main");
		selenium.click("link=Login");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=Email", "labreserve2015@gmail.com");
		selenium.type("id=Passwd", "isst2015");
		selenium.click("id=signIn");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
