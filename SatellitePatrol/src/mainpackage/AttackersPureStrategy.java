package mainpackage;

import java.util.List;

import mainpackage.AttackersPureStrategy;

public class AttackersPureStrategy {
	public List<Integer> path;
	
	public AttackersPureStrategy(List<Integer> path){
		this.path = path;
	}
	
	public boolean equals(AttackersPureStrategy s){
		
		if(this.path.size()!=s.path.size()){
			return false;
		}
		int counter=0;
		for (int i:this.path){
			if(i!=s.path.get(counter)){
				return false;
			}
			counter++;
		}
		return true;
	}
	
	public String toString(){
		String result="";
		result=result +"|";
		for (int i:path){
			result=result+i+" ";
		}
		return result;
	}
}
