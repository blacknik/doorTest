package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePage extends BaseModule {
    public HomePage(ChromeDriver driver) {
        super(driver);
    }

    By searchField = By.xpath("(//input[@id=\"search\"])[1]");
    By searchButton = By.xpath("(//button[@type=\"submit\"])[1]");
    By listColors = By.xpath(".//li[@class=\"m-color__item\"]//a//span");


    public void navigateToHomePage(){
        logger.info("Переходим на сайт : https://doordesignlab.com");
        navigate("https://doordesignlab.com/");
    }
    public void search(String serachName){
        logger.info("Ждём поисковую строку");
        waitElement(searchField);
        logger.info("Вводим в поискорвую строку: "+serachName);
        element(searchField).sendKeys(serachName);
        logger.info("Нажимаем кнопку поиска");
        element(searchButton).click();
    }

    public String[] getListColors() {
        int count = elements(listColors).size();
        String[] list = new String[count];
        for (int i = 0; i < count; i++) {
            WebElement el = elements(listColors).get(i);
            String name = el.getAttribute("innerHTML");
            list[i]= name;
        }
        return list;
    }

}
