import java.lang.reflect.Array;
import java.util.ArrayList;

public class MergeSort {
    private ArrayList<Double> myArray;
    private ArrayList<Double> tempArray;
    public void mergeSort(ArrayList<Double> arr){
        myArray = arr;
        int length = arr.size();
        tempArray = new ArrayList<>();
        for(int i = 0; i < length; i++){
            tempArray.add(0.0);
        }
        //tempArray = new int[length];
        setUpMerge(0, length-1);
    }
    private void setUpMerge(int lower, int higher){
        if(lower < higher){
            int middle = lower + (higher - lower) / 2;
            setUpMerge(lower,middle);
            setUpMerge(middle + 1, higher);
            doTheMerge(lower,middle,higher);
        }
    }
    private void doTheMerge(int lower, int middle, int higher){
        for(int i = lower; i <= higher; i++){
            tempArray.set(i, myArray.get(i));
        }
        int i = lower;
        int j = middle + 1;
        int k = lower;
        while(i <= middle && j <= higher){
            if(tempArray.get(i) <= tempArray.get(j)){
                myArray.set(k,tempArray.get(i));
                i++;
            }else{
                myArray.set(k,tempArray.get(j));
                j++;
            }
            k++;
        }
        while(i <= middle){
            myArray.set(k,tempArray.get(i));
            k++;
            i++;
        }
    }
    public ArrayList<Double> getSortedData(){
        return myArray;
    }
}
/*
public class MergeSort {
    private static int[] myArray;
    private static int[] tempArray;
    private void mergeSort(int arr[]){
        myArray = arr;
        int length = arr.length;
        tempArray = new int[length];
        setUpMerge(0, length-1);
    }
    private void setUpMerge(int lower, int higher){
        if(lower < higher){
            int middle = lower + (higher - lower) / 2;
            setUpMerge(lower,middle);
            setUpMerge(middle + 1, higher);
            doTheMerge(lower,middle,higher);
        }
    }
    private void doTheMerge(int lower, int middle, int higher){
        for(int i = lower; i <= higher; i++){
            tempArray[i] = myArray[i];
        }
        int i = lower;
        int j = middle + 1;
        int k = lower;
        while(i <= middle && j <= higher){
            if(tempArray[i] <= tempArray[j]){
                myArray[k] = tempArray[i];
                i++;
            }else{
                myArray[k] = tempArray[j];
                j++;
            }
            k++;
        }
        while(i <= middle){
            myArray[k] = tempArray[i];
            k++;
            i++;
        }
    }
}
*/

