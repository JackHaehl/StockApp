public class SimulationHandler {
    private int sY, sM, eM, eY;
    StockList stockList;
    SimulationHandler(int startYear, int startMonth, int endMonth, int endYear){
        sY = startYear;
        sM = startMonth;
        eM = endMonth;
        eY = endYear;
    }
    public void constructSimulationData(){
        stockList.buildSimulationStockList(sY,sM);
        for(Stock stock : stockList.getStockList()){
            stock.constructSimulationDataArray(sY,sM);
        }
    }
    public int getStartYear(){
        return sY;
    }
    public int getEndYear(){
        return eY;
    }
    public int getStartMonth(){
        return sM;
    }
    public int getEndMonth(){
        return eM;
    }
}
