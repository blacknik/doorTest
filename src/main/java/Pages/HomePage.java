package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class HomePage extends BaseModule {
    public HomePage(ChromeDriver driver) {
        super(driver);
    }

    By searchField = By.xpath("(//input[@id=\"search\"])[1]");
    By searchButton = By.xpath("(//button[@type=\"submit\"])[1]");
    By listColors = By.xpath(".//li[@class=\"m-color__item\"]//a//span");
    By menus = By.xpath("//ul[@id=\"nav\"]//li[contains(@class, \"level\")]");

// (//ul[@id="nav"]//li[contains(@class, "level")])[1]//div[contains(@style," display: none;")]


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

    public void navigateMouseToElement(WebElement element){

        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void checkAllTabs() throws InterruptedException {
        logger.info("Начинаем проверку всех вкладок меню");
        int count = elements(menus).size();
        logger.info("Всего вкладок "+count);
        for (int i=1;i<=count;i++){
            int status;
            String name = element(By.xpath("((//ul[@id=\"nav\"]//li[contains(@class, \"level\")])["+i+"]//span)[1]")).getText();
            logger.info("Наводим курсор на вкладку "+name);
            navigateMouseToElement(element(By.xpath("(//ul[@id=\"nav\"]//li[contains(@class, \"level\")])["+i+"]")));
            Thread.sleep(500);
            if (name.equals("Interior Doors")||name.equals("Hardware")||name.equals("Sale")||name.equals("Help")) {
                logger.info("Проверяем что вкладка "+name+" раскрывается");
                status = elements(By.xpath("(//ul[@id=\"nav\"]//li[contains(@class, \"level\")])[" + i + "]//div[contains(@style,\" display: block;\")]")).size();
                assert (status == 0);
            }else {
                logger.info("Проверяем что вкладка "+name+" не раскрывается");
                status = elements(By.xpath("(//ul[@id=\"nav\"]//li[contains(@class, \"level\")])[" + i + "]//div[contains(@style,\" display: none;\")]")).size();
                assert(status == 0);
            }
        }
    }
}
