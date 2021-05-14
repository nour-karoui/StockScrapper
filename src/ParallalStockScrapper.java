import java.util.List;
import java.util.concurrent.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ParallelStockScrapper implements Runnable {

    public static WebDriver driver = null;

    public void run() {

        /**
         * Settings for the chrome drive
         */
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\IdeaProjects\\StockScrapper\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver,40);

        driver.navigate().to("https://finance.yahoo.com/most-active?offset=0&count=100");

        /**
         * extract the size of the table
         */
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"scr-res-table\"]/div[1]/table")));
        List elements = driver.findElements(By.xpath("//*[@id=\"scr-res-table\"]/div[1]/table/tbody/tr/td[1]"));
        System.out.println("No of rows are : " + elements.size());

        /***
         * extract each row,
         * then extract the value change and company name
         */
        ForkJoinTable taskFJ=new ForkJoinTable(1, elements.size(), driver);
        ForkJoinPool pool=new ForkJoinPool();
        pool.execute(taskFJ);
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) {
        ScheduledExecutorService exec =  Executors.newScheduledThreadPool(1) ;
        // do the scrapping every 5 minutes
        ScheduledFuture<?> scraper = exec.scheduleAtFixedRate(new ParallelStockScrapper(), 0, 2,TimeUnit.MINUTES );
        // set cancellation time, after 3 hours the program stops
        exec.schedule(new Runnable() {
            public void run() { scraper.cancel(true); }
        },3,TimeUnit.HOURS);

    }
}
