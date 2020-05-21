package similarity;
import java.io.*;
import java.util.*;

public interface TextSimilarityMeasure//based compare, need to be implement
{
    public void setinput(Map<String,Double> in,int n,String fid);
    public int getn();
    public double getrate();
    public Set<String> setngrams(String in);
    public double getSimilarity(String strings1, String strings2);
    public double getSimilarityNgrams(String[] token1, String[] token2);
}