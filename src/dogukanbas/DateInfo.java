package dogukanbas;

import java.io.Serializable;

public class DateInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;

    public DateInfo(int startMonth, int startYear, int endMonth) {
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endMonth = endMonth;
        if(startMonth > endMonth){
            this.endYear = startYear+1;
        }
        else{
            this.endYear = startYear;
        }

    }
    public int getStartMonth() {
        return startMonth;
    }
    public int getStartYear() {
        return startYear;
    }
    public int getEndYear() {
        return endYear;
    }
    public int getEndMonth() {
        return endMonth;
    }

    @Override
    public String toString() {
        return "DateInfo{" +
                "startMonth=" + startMonth +
                ", startYear=" + startYear +
                ", endMonth=" + endMonth +
                '}';
    }
}
