import java.io.Serializable;

public class Stock implements Serializable {
    String tester;
    Stock(String test){
        tester = test;
    }
    public String getTester(){
        return tester;
    }
}
