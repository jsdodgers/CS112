/**
 * 
 */
package ir.assignments.two.d;

/**
 * @author xuke
 *
 */
public class BinarySearch {
	public static int search(int[] a,int i){
		return search(a,0,a.length-1,i);
	}
    private static int search(int[] a,int lo,int hi,int i){
    	if(hi<lo)return -1;
    	int mid=lo+(hi-lo)/2;
    	if(i>a[mid])return search(a,mid+1,hi,i);
    	else if(i<a[mid])return search(a,lo,mid-1,i);
    	else return mid;
    }

}
