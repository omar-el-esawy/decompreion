import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {


    public static void decompression()
    {
        System.out.println("Enter Stream of data : ");
        Scanner cin = new Scanner(System.in);
        String stream=cin.nextLine();
        double Smallest_Range=1000;
        Map<Character,Double> probability = new HashMap<Character,Double>();
        System.out.println("Enter number of distinct Character ");
        int n=cin.nextInt();
        System.out.println("Enter probabilities for each char ex: (a 0.2) ");
        for(int i=0;i<n;i++)
        {
            char c=cin.next().charAt(0);
            double prob= cin.nextDouble();
            probability.put(c,prob);
            Smallest_Range=Math.min(Smallest_Range,prob);
        }
        //Find the Min number of Bits required to store a Code LESS than the Smallest Range
        int k=0;
        for(int i=1;i<=100;i++)
        {
            if(1/Math.pow(2,i)<=Smallest_Range)
            {
                k=i;
                break;
            }
        }
        //build Low Range and High Range for each Character
        Map<Character, Pair>low_high = new HashMap<Character, Pair>();
        double last=0;
        for(Map.Entry<Character,Double> current:probability.entrySet())
        {
            low_high.put(current.getKey(),new Pair(last,last+current.getValue()));
            last=last+current.getValue();
        }
        String firstK=stream.substring(0,k);
        int r=k;
        String orginal="";
        double lastLow=0,lastHigh=1;
        while (r<=stream.length())
        {
            //System.out.println(firstK);
            double curr=0;
            int ind=firstK.length()-1;
            //takeeeeee careeeeeeeeeeee!!!
            for (int i=0;i<firstK.length();i++)
            {
                if(firstK.charAt(i)=='1')
                    curr+=(Math.pow(2,ind));
                ind--;
            }
            curr=(curr)/(Math.pow(2,firstK.length()));
            curr=(curr-lastLow)/(lastHigh-lastLow);
            for(Map.Entry<Character,Pair> current:low_high.entrySet())
            {
                if(curr>=current.getValue().low&&curr<=current.getValue().high)
                {
                    orginal+=current.getKey();
                    break;
                }
            }

            if(r==stream.length())
                break;
            double lower=lastLow;
            lastLow=lower+(lastHigh-lower)*low_high.get(orginal.charAt(orginal.length()-1)).low;
            lastHigh=lower+(lastHigh-lower)*low_high.get(orginal.charAt(orginal.length()-1)).high;
            while ((0.5<lastLow &&.5<lastHigh)|| (0.5>lastLow &&.5>lastHigh))
            {
                firstK=firstK.substring(1);
                firstK+=stream.charAt(r);
                r++;
                if(0.5<lastLow && 0.5<lastHigh)
                {
                    lastLow=(lastLow-.5)*2;
                    lastHigh=(lastHigh-.5)*2;
                }
                else
                {
                    lastLow*=2;
                    lastHigh*=2;

                }
            }


        }
        System.out.println(orginal);
    }

    public static void main(String[] args) {
        decompression();
    }
}