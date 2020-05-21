package similarity;
import java.util.*;
import java.io.*;
//based on cosine check for n-grams, used on simple token
public class simplecheck implements TextSimilarityMeasure{
    int n=-1;
    double rate=-1;
    String fileid;////
    String compares;
    Set<String> templine;
    Map<String,Double> tempall;
    @Override
    public void setinput(Map<String,Double> in,int n,String fid){
        this.tempall=in;
        this.n=n;
        this.fileid=fid;
    }
    public Map getmap(Set<String> templine){//
        Map<String,Double>tempmap=new HashMap<>(); 
        for (String ngram:templine){ 
            double count=0; 
            if (tempmap.containsKey(ngram)) { 
                count=tempmap.get(ngram); 
                System.out.println(count);
            } 
            count++;
            tempmap.put(ngram,count); 
        } 
    return tempmap;
    }
    @Override
    public Set<String> setngrams(String in){
        templine=new HashSet<>();
        if(n>=0){
            for(int i=0;i<in.length()-(n-1);i++){
                String ngram = in.substring(i, i + n);//eg:0-3,1-4,2-5 to the end
                templine.add(ngram);
            }
        }
        return templine;
    }
    @Override
    public int getn(){
        return this.n;
    }
    @Override
    public double getrate(){
        return this.rate;
    }
    public String assignid(){////
        return this.fileid;
    }
    
    @Override
    public double getSimilarity(String strings1, String strings2) {//
        if(strings1==null){
            return -1;
        }
        if(strings2==null){
            return -1;
        }
        Set<String> ngrams1 = setngrams(strings1); 
        Set<String> ngrams2 = setngrams(strings2); 
        //delete space and insert set
        templine = new HashSet<>(); 
        templine.addAll(ngrams1); 
        templine.addAll(ngrams2);
        Map<String,Double>map1=getmap(ngrams1);
        Map<String,Double>map2=getmap(ngrams2); 
        //System.out.println("first: "+map1);//mix map
        //System.out.println("second: "+map2);
        double[]diff1=new double[templine.size()]; 
        double[]diff2=new double[templine.size()]; 
   
        int index = 0; 
        for(String ngram:templine){//
            if(map1.containsKey(ngram)&&tempall.containsKey(ngram)){//map1 key contains
                diff1[index]=map1.get(ngram)*tempall.get(ngram); 
            } 
            else {
                diff1[index] = 0.0; 
            }
            
            if (map2.containsKey(ngram)&&tempall.containsKey(ngram)){//map2 key contains
                diff2[index]=map2.get(ngram)*tempall.get(ngram); 
            } 
            else {
                diff2[index] = 0.0; 
            }
            index++; 
        }
        //compute cosine difference//////
        //https://stackoverflow.com/questions/1746501/can-someone-give-an-example-of-cosine-similarity-in-a-very-simple-graphical-wa
        double upper=0; 
        for(int i=0;i<templine.size();i++){//count devider
            upper=upper+diff1[i]*diff2[i]; 
        } 
        double vec1 = 0.0; 
        for(int i=0; i<templine.size();i++){//count devider
            vec1=vec1+Math.pow(diff1[i], 2); 
        } 
        double vec2=0.0; 
        for (int i=0;i<templine.size();i++){
            vec2=vec2+Math.pow(diff2[i],2); 
        }
        return upper / (Math.sqrt(vec1) * Math.sqrt(vec2)); 
    }
    @Override
    public double getSimilarityNgrams(String[] token1, String[] token2) {//if corey getting array
        return rate;
    }
}
