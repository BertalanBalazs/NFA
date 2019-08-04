package com.codecool.bertalan.nfa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected final WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10);
        PageFactory.initElements(this.driver,  this);
    }

    public void navigate(String subUrl) {
        driver.navigate().to(System.getenv("baseUrl") + subUrl);
    }

    public void navigate() {
        driver.navigate().to(System.getenv("baseUrl"));
    }

    public String getCurrentPageTitle() {
        return driver.getTitle();
    }
}
