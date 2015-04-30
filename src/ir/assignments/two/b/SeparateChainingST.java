package ir.assignments.two.b;

import ir.assignments.two.a.*;
import java.util.ArrayList;


public class SeparateChainingST {
	int M=997;
	private Node[] nd=new Node[M];
	private class Node{
		private String key;
		private int value;
		private Node next;
		public Node(String key){
			this.key=key;
			value=1;
			next=null;
		}
	}
	private int hash(String key){
		return (key.hashCode()&0x7fffffff)%M;
	}
	public void put(String key){
		int i=hash(key);
		for(Node x=nd[i];x!=null;x=x.next){
			if(key.equals(x.key)){
				(x.value)++;
				return;
			}
		}
		Node temp=new Node(key);
		temp.next=nd[i];
		nd[i]=temp;		
	}
	public int get(String key){
		int i=hash(key);
		for(Node x=nd[i];x!=null;x=x.next){
			if(key.equals(x.key))return x.value;
		}
		return -1;
	}
	public ArrayList<Frequency> iterate(){
		ArrayList<Frequency> s=new ArrayList<Frequency>();
		for(int i=0;i<M;++i){
			for(Node x=nd[i];x!=null;x=x.next){
				Frequency f=new Frequency(x.key,x.value);
				s.add(f);
			}
		}
		return s;
	}

}
