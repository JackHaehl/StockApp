public class PerStockDataPoint {
    int dayOfYear;
    int year;
    int month;
    int day;
    double volume;
    double price;
    double dividend;
    PerStockDataPoint(int dayOfYear, int year, int month, int day, double price, double dividend, double volume){
        this.dayOfYear = dayOfYear;
        this.year = year;
        this.month = month;
        this.day = day;
        this.price = price;
        this.dividend = dividend;
        this.volume = volume;
    }
    public int getDayOfYear(){
        return dayOfYear;
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public double getPrice(){
        return price;
    }
    public double getDividend(){
        return dividend;
    }
}
