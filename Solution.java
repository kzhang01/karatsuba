import java.util.Scanner;
import java.io.IOException;

class Result {
    public static long mod(long n){
        final long MOD = 1000000009;
        if(n >= MOD)
            return n % MOD;
        if(n < 0)
            return ((n % MOD) + MOD) % MOD;
        return n;
    }
   
    public static long[] naive(long[] x, long[] y, int n){
        long[] c = new long[2*n - 1];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                c[i+j] += mod(x[i] * y[j]);
            }
        }
        return c;
    }
   
    public static long[] smellCosmos(long[] a, long[] b, int n) {
        //initialize variables
        long[] aHI = new long[n/2];
        long[] aLO = new long[n/2];
        long[] bHI = new long[n/2];        
        long[] bLO = new long[n/2];
        long[] c = new long[2*n - 1];
        long[] d1 = new long[n/2];
        long[] d2 = new long[n/2];
       
        //Naive algorithm is more efficient when n < 64
        if(n <= 64) {
            return naive(a, b, n);
           
        //Karatsuba is more efficient when n >= 64
        }else{
           
            //Populate aLO and bLO
            for(int i = 0; i < n/2; i++) {
                aLO[i] = a[i];
                bLO[i] = b[i];
            }
           
            //Populate aHI and bHI
            for(int i = n/2; i < n; i++) {
                aHI[i-n/2] = a[i];
                bHI[i-n/2] = b[i];
            }
           
            //Populate d1 and d2
            for(int i = 0; i < n/2; i++) {
                d1[i] = mod(aLO[i] + aHI[i]);
                d2[i] = mod(bLO[i] + bHI[i]);
            }

            //recursive calls
            long[] cMID = smellCosmos(d1, d2, n/2);
            long[] cLO = smellCosmos(aLO, bLO, n/2);
            long[] cHI = smellCosmos(aHI, bHI, n/2);

            for(int i = 0; i < n-1; i++) {
                c[i] = cLO[i];
            }
            c[n-1] = 0;

            for(int i = 0; i < n-1; i++) {
                c[n+i] = cHI[i];
            }

            for(int i = 0; i < n-1; i++) {
                c[n/2 + i] += mod(cMID[i] - (cLO[i] + cHI[i]));
            }
           
            return c;
        }
    }
}


public class Solution {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);        
        int n = input.nextInt() + 1;
        long[] a = new long[n];
        long[] b = new long[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = input.nextLong();
        }
        
        for (int i = 0; i < n; i++) {
            b[i] = input.nextLong();
        }
        
        long[] result = Result.smellCosmos(a, b, n);
        
        for(int i = 0; i < result.length; i++) {
            System.out.print(Result.mod(Result.mod(result[i])) + " ");
        }
    }
}
