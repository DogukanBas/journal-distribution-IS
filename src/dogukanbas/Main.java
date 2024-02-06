package dogukanbas;

import gui.DistributorPage;
import gui.ReportPage;

public class Main {

    public static void main(String[] args) {

        DistributorPage distributorPage = new DistributorPage();
        ReportPage reportPage = new ReportPage();
        Thread thread = new Thread(distributorPage);


        thread.start();

        Thread thread2 =new Thread(reportPage);
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }












    }
}
