package similarity;
import java.util.*;
import java.io.*;
public class WordContainsCheck extends simplecheck{//set two table together as library, compare each inside library, percentage
    double rate=-1;
    String fileid;////
    String compares1;
    String compares2;
    Set<String> templine;
    Set<String> tempall;
    public void setinput(String str1,String str2,String fid){
        this.compares1=str1;
        this.compares2=str2;
        this.fileid=fid;
    }
    @Override
    public double getrate(){
        return this.rate;
    }
    public String assignid(){////
        return this.fileid;
    }
    public double getSimilarity() {//
        String[] temp1 = compares1.split("\\s+");
        String[] temp2 = compares2.split("\\s+");
        HashSet<String> tempset1=new HashSet();
        HashSet<String> tempset2=new HashSet();
        int counter=0;
        for(String a:temp1)
            tempset1.add(a);
        for(String b:temp2)
            tempset2.add(b);
        System.out.println(tempset1);
        System.out.println(tempset2);
        tempall=(Set<String>) tempset1.clone();
        ArrayList<String> temparr1=new ArrayList(tempset1);
        ArrayList<String> temparr2=new ArrayList(tempset2);
        for(String b:temp2)
            tempall.add(b);
        ArrayList<Boolean> test=new ArrayList(tempall.size());
        for(int i=0;i<temparr1.size();i++){
            for(int j=0;j<temparr2.size();j++){
                Boolean te=temparr1.get(i).equals(temparr2.get(j));
                if(te==true)
                {
                    counter++;
                }
            }
        }
        rate=(double)counter/temparr2.size();
        return rate;
    }

    @Override
    public double getSimilarityNgrams(String[] token1, String[] token2) {//
        return rate;
    }
}
