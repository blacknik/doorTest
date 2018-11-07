package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends BaseModule{
    public SearchResultPage(ChromeDriver driver) {
        super(driver);
    }

    By listSearchResult = By.xpath("(//li[@itemtype])");
    public By elementName(int num){return By.xpath("(//li[@itemtype]//h2//a)["+num+"]");}
    By resultsPerPage =By.xpath("(//select[@title=\"Results per page\"])[1]//option[@selected]");
    public int resultPerPageCount() {return Integer.parseInt(element(resultsPerPage).getText().replace(" ", ""));}
    By nextButton = By.xpath("(//a[@class=\"next i-next\"])[2]");


    public List<String> checkSearchRequestInListSearchResult(String request){
        int allResult =0;
        boolean status = true;
        List<String> errors = new ArrayList<>();
        do {
            waitElement(listSearchResult);
            int listSearchResultCount = elements(listSearchResult).size();
            allResult = allResult + listSearchResultCount;
            for (int i = 1; i <= listSearchResultCount; i++) {
                logger.info("Получаем результат " + i + " из " + listSearchResultCount);
                String title = element(elementName(i)).getAttribute("title").toLowerCase();
                logger.info("Выполняем поиск слова \"" + request + "\" в строке: " + title);
                try {
                    Assert.assertTrue(title.contains(request.toLowerCase()));
                } catch (AssertionError e){
                    errors.add("Не найден \"" + request + "\" в строке: " + title);
                }


                logger.info("Слово найдено");
            }
            try {
                logger.info("Переходим на следующую вкладку");
                element(nextButton).click();
            }catch (NoSuchElementException e){
                logger.info("Это последняя вкладка");
                status = false;
            }
        }while (status);

        logger.info("Результатов всего: "+allResult);
        return errors;
    }

    public void checkDefValueResultsPerPage(){
        logger.info("Проеряем что по дефолту отображается 12 результатов");
        Assert.assertTrue(resultPerPageCount() == 12);
    }



}
