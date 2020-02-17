import java.lang.reflect.Array;
import java.util.ArrayList;

public class LeaderboardHandler {
    ArrayList<Stock> leaderboard;
    String aspect = "";
    StockList stockList;
    ArrayList<Double> dataSet;
    MergeSort sort;
    ArrayList<Stock> sortedList;
    private final double threshold = 0.000000001;
    LeaderboardHandler(){
        stockList = new StockList();
        stockList.buildList();
    }
    LeaderboardHandler(int startYear, int startMonth){
        stockList = new StockList();
        stockList.buildSimulationStockList(startYear,startMonth);
    }
    public void createLeaderboard(String dataPoint){

        aspect = dataPoint;
        formulateData();
        sort = new MergeSort();
        sort.mergeSort(getDataSet());
    }
    public void formulateData(){
        //TODO: MAKE THIS READ FROM STOCK DATA FILE TO SAVE COMPUTING (OR NOT?) IT'S ACTUALLY REALLY FAST.
        dataSet = new ArrayList<>();
        dataSet.clear();
        if(aspect.equals("price")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.averageChange())) {
                    dataSet.add(stock.averageChange());
                }
            });
        }else if(aspect.equals("weightedPrice")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.weightedAverageChange())) {
                    dataSet.add(stock.weightedAverageChange());
                }
            });
        }else if(aspect.equals("stability")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.stability())) {
                    dataSet.add(stock.stability());
                }
            });
        }else if(aspect.equals("weightedStability")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.weightedStability())) {
                    dataSet.add(stock.weightedStability());
                }
            });
        }else if(aspect.equals("payout")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.payoutRatio())) {
                    dataSet.add(stock.payoutRatio());
                }
            });
        }else if(aspect.equals("ROI")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.yearlyROI())) {
                    dataSet.add(stock.yearlyROI());
                }
            });
        }else if(aspect.equals("HSI")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.HSI())) {
                    dataSet.add(stock.HSI());
                }
            });
        }else if(aspect.equals("change")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.totalChange())) {
                    dataSet.add(stock.totalChange());
                }
            });
        }else if(aspect.equals("consistency")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.consistency())) {
                    dataSet.add(stock.consistency());
                }
            });
        }else if(aspect.equals("volatility")){
            stockList.getStockList().forEach((stock) -> {
                if(!Double.isNaN(stock.volatility())) {
                    dataSet.add(stock.volatility());
                }
            });
        }
    }
    public ArrayList<Double> getDataSet(){
        return dataSet;
    }
    public void displayLeaderboard(){
        try {
            sortStockList();
            for(int i = 0; i < getSortedList().size(); i++){
                System.out.println(getSortedList().size() - i + ". " + getSortedList().get(i).getSymbol() + " AverageChange: " + roundedPoint(getSortedList().get(i).averageChange()) + " Stability: " + roundedPoint(getSortedList().get(i).stability()) + " Payout Percentage: " + roundedPoint(getSortedList().get(i).payoutRatio()));
                System.out.println("Weighted AverageChange: " + getSortedList().get(i).weightedAverageChange() + " Weighted Stability: " + roundedPoint(getSortedList().get(i).weightedStability()) + " Yearly ROI: " + roundedPoint(getSortedList().get(i).yearlyROI()));
                System.out.println("Consistency: " + roundedPoint(getSortedList().get(i).consistency()) + " Total Change: " + roundedPoint(getSortedList().get(i).totalChange()) + " HSI Score: " + roundedPoint(getSortedList().get(i).HSI()) + " Volatility: " + roundedPoint(getSortedList().get(i).volatility()));
                System.out.println("----------------------------------");
            }
        }catch(Exception e){
            System.out.println("LeaderboardHandler Error Code 1");
        }
    }
    public void sortStockList(){
        sortedList = new ArrayList<>();
        getDataSet().forEach((point) -> {
            if(aspect.equals("price")) {
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.averageChange() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("weightedPrice")) {
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.weightedAverageChange() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("stability")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.stability() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
                }else if(aspect.equals("weightedStability")){
                    stockList.getStockList().forEach((testStock) -> {
                        if (Math.abs(testStock.weightedStability() - point) < threshold) {
                            sortedList.add(testStock);
                        }
                    });
            }else if(aspect.equals("payout")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.payoutRatio() - point) < threshold && testStock.payoutRatio() != 0.0) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("ROI")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.yearlyROI() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("consistency")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.consistency() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("HSI")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.HSI() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }else if(aspect.equals("change")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.totalChange() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            } else if(aspect.equals("volatility")){
                stockList.getStockList().forEach((testStock) -> {
                    if (Math.abs(testStock.volatility() - point) < threshold) {
                        sortedList.add(testStock);
                    }
                });
            }
        });
    }
    public ArrayList<Stock> getSortedList(){
        return sortedList;
    }
    public double roundedPoint(double point){
        return Math.floor(point * 1000) / 1000;
    }
}
