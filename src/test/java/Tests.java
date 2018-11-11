
import Pages.BaseModule;
import Pages.HomePage;
import Pages.SearchResultPage;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    ChromeDriver chrome = null;
    HomePage homePage =null;
    SearchResultPage searchResultPage = null;
    final static Logger logger = Logger.getLogger(BaseModule.class);

    @BeforeTest
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chrome = new ChromeDriver(chromeOptions);

        homePage = new HomePage(chrome);
        searchResultPage = new SearchResultPage(chrome);
    }

    @Test()
    public void test_1_1(){
        homePage.navigateToHomePage();
        String[] searchRequests = homePage.getListColors();
        List<String> errors = new ArrayList<>();
        for (String searchRequest: searchRequests) {
            homePage.search(searchRequest);
            errors.addAll(searchResultPage.checkSearchRequestInListSearchResult(searchRequest));
        }
        for (String error: errors){logger.error(error); }
    }
    @Test()
    public void test_1_2() throws InterruptedException {
        homePage.checkAllTabs();
    }
    @Test()
    public void test_1_3(){
        String[] searchRequests = homePage.getListColors();
        homePage.search(searchRequests[0]);
        searchResultPage.checkDefValueResultsPerPage();
    }





    @AfterTest
    public void SetDown(){
        chrome.close();
    }

}
