### Welcome to STOCK SCRAPPER  <img src="https://raw.githubusercontent.com/MartinHeinz/MartinHeinz/master/wave.gif" width="30px">

**A program that scrapes Yahoo Finance and checks whether the stock have increased or decreased**

## Technologies Used
- <img src="https://cdn.worldvectorlogo.com/logos/java-4.svg" width="15px"> Java 15 
- <img src="./assets/selenium.png" width="20px"> Selenium
- <img src="https://www.perl.com/images/spidering-websites-with-headless-chrome-and-selenium/chrome.png" width="20px"> Chrome Driver

## How it works

* First of all, you have to add selenium jar files in the external libraries
(you can download it from this [link](https://www.selenium.dev/downloads/))

* Using ScheduledExecutorService, we created a task and scheduled it to run every two minutes

```java
        public static void main(String []args) {
                ScheduledExecutorService exec =  Executors.newScheduledThreadPool(1) ;
                // do the scrapping every 5 minutes
                ScheduledFuture<?> scraper = exec.scheduleAtFixedRate(new ParallelStockScrapper(), 0, 2,TimeUnit.MINUTES );
                // set cancellation time, after 3 hours the program stops
                exec.schedule(new Runnable() {
                    public void run() { scraper.cancel(true); }
                },3,TimeUnit.HOURS);
            }
```

* The task is navigating to Yahoo Finance and scrapping the stocks table
```java
    public void run() {
     
             /**
              * Settings for the chrome drive
              */
             System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\IdeaProjects\\StockScrapper\\driver\\chromedriver.exe");
             driver = new ChromeDriver();
             WebDriverWait wait = new WebDriverWait(driver,40);
     
             driver.navigate().to("https://finance.yahoo.com/most-active?offset=0&count=100");
     
             /**
             * You Can Check the rest of the code in the file ParallelStockScrapper.java
             **/
         }
```

***For more optimisation***, We included a ForkJoinTable, to divide the table between different threads. (Check the complete code in the ForkJoinTable.java file)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/nour-karoui/StockScrapper/issues). You can also take a look at the [contributing guide](https://github.com/nour-karoui/StockScrapper/blob/master/CONTRIBUTING.md).

## Show your support

Give a [STAR](https://github.com/nour-karoui/StockScrapper) if this project helped you!

