package Pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseModule {

    protected ChromeDriver driver;
    WebDriverWait wait;

    public BaseModule(ChromeDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }
    final static Logger logger = Logger.getLogger(BaseModule.class);

    public void navigate(String URL){
        driver.get(URL);
    }

    public WebElement element(By by){
        return driver.findElement(by);
    }
    public List<WebElement> elements(By by){
        return driver.findElements(by);

    }
    public void waitElement(By by){
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(by));
    }


    public void scroolDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(250,350)");

    }

    public WebElement getVisibility(By locator, int timeout) {
        WebElement element = null;
        wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }
    public void clickElementWhenClickable(By locator, int timeout) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (TimeoutException e) {
            scroolDown();
            clickElementWhenClickable(locator, timeout);
        }
    }


}
