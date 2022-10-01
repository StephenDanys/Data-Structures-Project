import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetBenefit{
    public static void main(String args[]){
        int i=1; //counter designed in such way that all integers are placed in correct queues
        int counter=0; //counter that keeps track of the remaining stocks available for sale
        int profit=0; //keeps track of the profit
        File f = null;
        IntQueueImpl<Integer> quantityQueue=new IntQueueImpl<Integer>(); //we design 2 queues, one to retain info for the quantity of each group of stocks and their price is placed in the same position to the other queue
        IntQueueImpl<Integer> priceQueue=new IntQueueImpl<Integer>();
        BufferedReader reader = null;
        String line;
        Pattern intsOnly = Pattern.compile("\\d+"); //use of regex, reads only numbers
        try {
            f = new File(args[0]);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }

        try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
        try {
            line = reader.readLine();
            while (line != null) {
                if(line.startsWith("buy")){         //if the line starts with buy, the following process is initiated
                    Matcher makeMatch = intsOnly.matcher(line);     //find patterns 
                    while (makeMatch.find()) {
                        if(i%2!=0){     //every 2 numbers that are read are placed in quantityQueue, the rest in priceQueue
                            quantityQueue.put(Integer.valueOf(makeMatch.group()));
                            counter+=Integer.valueOf(makeMatch.group());
                        }else{
                            priceQueue.put(Integer.valueOf(makeMatch.group()));
                        }
                        i++;
                    }
                }
                if(line.startsWith("sell")){    //if the line starts with buy, the following process is initiated
                    int sellQuantity=0; 
                    int sellPrice=0;
                    Matcher makeMatch = intsOnly.matcher(line); //search for patterns(numbers)
                    while (makeMatch.find()) { //same logic as above
                        if(i%2!=0){
                            sellQuantity=Integer.valueOf(makeMatch.group());
                        }else{
                            sellPrice=Integer.valueOf(makeMatch.group());
                        }
                        i++;
                    }
                    if(counter<sellQuantity){   //if the remaining stocks are less than the stocks we want to sell, we exit the loop
                        System.out.println("not enough stocks");
                        break;
                    }
                    
                    while(sellQuantity>=Integer.valueOf(quantityQueue.peek())){     //while there are enough 
                        profit+=quantityQueue.peek()*(sellPrice-priceQueue.peek()); //increase profit
                        sellQuantity-=quantityQueue.get(); //remove the stocks that are sold from the remaining number of stocks that need to be sold and remove the block of stocks from our queue
                        priceQueue.get(); //delete price node from priceQueue since it is no longer needed
                    }
                    if(sellQuantity!=0){ //if sellQuantity is smaller than the next node on quantityQueue then sell remaining stocks and decrease number of stocks in node
                        profit+=(sellQuantity)*(sellPrice-priceQueue.peek());
                        quantityQueue.setFirstData(quantityQueue.peek()-sellQuantity);
                    }
                }
                line=reader.readLine();
			}
        } //try
        catch (IOException e) {
            System.out.println	("Error reading line ...");
        }
        if (profit >=0 ) System.out.println("Your profit was: " + profit);
        else System.out.println("You have not profit. Your loses are: " + profit );
    }
}