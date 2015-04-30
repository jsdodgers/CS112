/**
 * 
 */
package ir.assignments.two.b;

import ir.assignments.two.a.Frequency;

/**
 * @author xuke
 *
 */
public class QuickSort {
	public static void sort(Frequency[] a){
		sort(a,0,a.length-1);
	}
	private static void sort(Frequency[] a,int lo,int hi){
		if(hi<=lo)return;
		int j=partition(a,lo,hi);
		sort(a,lo,j-1);
		sort(a,j+1,hi);
		}
	private static int partition(Frequency[] a,int lo,int hi){
		int i=lo;
		int j=hi+1;
		while(true){
			while(less(a[lo],a[++i]))
				if(i==hi)break;
			while(less(a[--j],a[lo]));
			if(i>=j)break;
			exch(a,i,j);
		}
		exch(a,lo,j);
		return j;
	}
	private static void exch(Frequency[] a,int v,int w){
		Frequency temp=a[v];
		a[v]=a[w];
		a[w]=temp;
		}
	private static boolean less(Frequency v,Frequency w){
		if(w.getText().compareTo(v.getText())<0)return true;
		else return false;
	}

}
