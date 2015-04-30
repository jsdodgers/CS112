/**
 * 
 */
package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;;

/**
 * @author xuke
 *
 */
public class MergeSort {
	public static void sort(Frequency[] a){
		Frequency[] aux=new Frequency[a.length];
		sort(a,aux,0,a.length-1);
	}
	private static void sort(Frequency[] a,Frequency[] aux,int lo,int hi){
		if(hi<=lo)return;
		int mid=lo+(hi-lo)/2;
		sort(a,aux,lo,mid);
		sort(a,aux,mid+1,hi);
		merge(a,aux,lo,mid,hi);
	}
	private static void merge(Frequency[] a,Frequency[] aux,int lo,int mid,int hi){
		for(int i=lo;i<=hi;++i){
			aux[i]=a[i];
		}
		int i=lo;
		int j=mid+1;
		for(int k=lo;k<=hi;++k){
			if(i>mid)a[k]=aux[j++];
			else if(j>hi)a[k]=aux[i++];
			else if((aux[i].getFrequency()<aux[j].getFrequency()))a[k]=aux[j++];
			else a[k]=aux[i++];
		}
	}

}
