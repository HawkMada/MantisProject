/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Token;

/**
 *
 * @author cw08td
 */
// Corey Wehr 4283123
// A simple pairs class used by the counting methods to record the combination of letters and frequency together

public class Pair<L,R> {
	    private L l;
	    private R r;
	    
	    // creates Pair
	    public Pair(L l, R r){
	        this.l = l;
	        this.r = r;
	    }
	    
	    // returns requested element
	    public L getL(){ return l; }
	    public R getR(){ return r; }
	    
	    // changes requested element
	    public void setL(L l){ this.l = l; }
	    public void setR(R r){ this.r = r; }
	
}
