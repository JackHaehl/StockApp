import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Refresher refresher = new Refresher();
        Scanner input = new Scanner(System.in);
        LeaderboardHandler leader = new LeaderboardHandler();
       // FetchStock msft = new FetchStock("AAPL");
        //FetchStock aapl = new FetchStock("MSFT");
        String command = "";
        String activeStock = "";
        boolean leaderboard = false;
        boolean simulation = false;
        if(!refresher.isRefreshed()){
            System.out.println("Some data is missing! Refreshing statistics...");
            refresher.refresh();
            System.out.println("Complete!");
        }
        Stock stock = new Stock("AAPL");
        System.out.println("Average Change: " + stock.averageChange());
        System.out.println("Weighted average change: " + stock.weightedAverageChange());
        System.out.println("Stability: " + stock.stability());
        System.out.println("Weighted Stability: " + stock.weightedStability());
        System.out.println("Payout Ratio: " + stock.payoutRatio());
        System.out.println("Average Yearly ROI: " + stock.yearlyROI());
        while(!command.equals("stop")){
            command = input.nextLine();
            switch(command){
                case "help":
                    System.out.println("Type \"stock\" then press enter and type a ticker symbol (IN CAPS) to access a stock.");
                    break;
                case "stock":

                    break;
                case "simulation":
                    simulation = true;
                    break;
                case "leaderboard":
                    leaderboard = true;
                    System.out.println("What data aspect would you like the leaderboard to sort by?");
                    break;
                case "exit":
                    activeStock = "";
                    leaderboard = false;
                    simulation = false;
                    System.out.println("The active stock has been cleared and you are in the main menu.");
                    break;
                case "price":
                    if(simulation){
                        leader = new LeaderboardHandler(2015,1);
                        leader.createLeaderboard("price");
                        leader.displayLeaderboard();
                    }
                    else if(leaderboard){
                        leader.createLeaderboard("price");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The daily percentage average change for " + activeStock + " is " + stock.averageChange());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "weighted price":
                    if(leaderboard){
                        leader.createLeaderboard("weightedPrice");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The daily percentage average change for " + activeStock + " is " + stock.weightedAverageChange());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "stability":
                    if(leaderboard){
                        leader.createLeaderboard("stability");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The stability coefficient for " + activeStock + " is " + stock.stability());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "weighted stability":
                    if(leaderboard){
                        leader.createLeaderboard("weightedStability");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The weighted stability coefficient for " + activeStock + " is " + stock.weightedStability());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "payout":
                    if(leaderboard){
                        leader.createLeaderboard("payout");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The dividend payout ratio for " + activeStock + " is " + stock.payoutRatio());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "roi":
                    if(leaderboard){
                        leader.createLeaderboard("ROI");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The Average yearly ROI for " + activeStock + " is " + stock.yearlyROI());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "consistency":
                    if(leaderboard){
                        leader.createLeaderboard("consistency");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The Average yearly consistency ratio for " + activeStock + " is " + stock.consistency());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "change":
                    if(leaderboard){
                        leader.createLeaderboard("change");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The total change % for " + activeStock + " is " + stock.totalChange());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "volatility":
                    if(leaderboard){
                        leader.createLeaderboard("volatility");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The volatility for " + activeStock + " is " + stock.volatility());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "hsi":
                    if(leaderboard){
                        leader.createLeaderboard("HSI");
                        leader.displayLeaderboard();
                    }else if(!activeStock.equals("")) {
                        System.out.println("The Average HSI for " + activeStock + " is " + stock.HSI());
                    }else{
                        System.out.println("There is no Active Stock");
                    }
                    break;
                case "import":
                    ImportStockFromFile importer = new ImportStockFromFile();
                    importer.importStocks();
                    break;
                default:
                    if(command.equals("stop")){
                        System.out.println("Exiting program...");
                    }else if(activeStock.equals("")) {
                        activeStock = command;
                        stock = new Stock(activeStock);
                        System.out.println("The active stock is now " + activeStock);
                    }else{
                        System.out.println("Invalid command!");
                    }
                    break;
            }
        }
    }
}

