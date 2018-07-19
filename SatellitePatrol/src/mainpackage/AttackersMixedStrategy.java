package mainpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import basicdatastructures.Tuple;

public class AttackersMixedStrategy implements Iterable<Tuple<Double, AttackersPureStrategy>>{
	public List<Tuple<Double, AttackersPureStrategy>> y;
	
	
	public AttackersMixedStrategy(){
		this.y=new ArrayList<Tuple<Double, AttackersPureStrategy>>();
	}


	@Override
	public Iterator<Tuple<Double, AttackersPureStrategy>> iterator() {
		return this.y.iterator();
	}
	
	public int getSize(){
		return y.size();
	}
	
	public String toString(){
		String result = "";
		for (Tuple<Double, AttackersPureStrategy> t:y){
			if(t.x>0){
			result=result+t.x+" | "+t.y.toString()+"\n"; 
			}
		}
		return result;
	}
	
	public boolean equals(AttackersMixedStrategy a){
		for(int i=0;i<y.size();i++){
			if(y.get(i).x!=a.y.get(i).x || !y.get(i).y.equals(a.y.get(i).y)) return false;
		}
		return true;
	}
	
	public void differs(AttackersMixedStrategy a){
		System.out.println("----------------------");
		for(int i=0;i<y.size();i++){
			 if(y.get(i).x>=0.0000){
			boolean contained = false;
			for(int j=0;j<a.y.size();j++){
			 if(y.get(i).y.equals(a.y.get(j).y)) contained=true;
			}
			if(!contained) System.out.println(y.get(i));
			  }
		}
		System.out.println("----------------------");
		for(int i=0;i<a.y.size();i++){
			 if(a.y.get(i).x>=0.0000){
			boolean contained = false;
			for(int j=0;j<y.size();j++){
			 if(a.y.get(i).y.equals(y.get(j).y)) contained=true;
			}
			if(!contained) System.out.println(a.y.get(i));
			  }
		}
	}
}
