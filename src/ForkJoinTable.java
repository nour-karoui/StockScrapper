import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;


public class ForkJoinTable extends RecursiveAction {
    private int length;
    private final int index;
    private final WebDriver driver;

    public ForkJoinTable(int index,int length, WebDriver driver) {
        this.index = index;
        this.length = length;
        this.driver = driver;
    }
    @Override
    protected void compute() {
        if (length > 1) {
            length = length % 2 == 1? length / 2 + 1 : length / 2;
            ForkJoinTable task1=new ForkJoinTable(index, length, driver);
            ForkJoinTable task2=new ForkJoinTable(index + length, length, driver);
            task1.fork();
            task2.fork();
            task1.join();
            task2.join();
        } else {
            String ch = driver.findElement(By.xpath("//*[@id=\"scr-res-table\"]/div[1]/table/tbody/tr[" + index + "]")).getText();
            String[] list = ch.split(" ");
            String valueChange = list[list.length - 6];
            String[] companyNameArray = Arrays.copyOfRange(list, 0, list.length - 7);
            StringBuilder sb = new StringBuilder();
            for (String s : companyNameArray) {
                sb.append(s);
                sb.append(" ");
            }
            String companyName = sb.toString();

            /**
             * do operations
             */
            if(valueChange.charAt(0) == '+') {
                System.out.println("I am selling my stocks of " + companyName + " with an interest of " + valueChange);
            } else {
                System.out.println("I am buying the stocks of " + companyName + " with an interest of " + valueChange);
            }
            System.out.println("===============");
        }
    }

}
