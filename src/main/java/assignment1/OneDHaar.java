package assignment1;

/**
 * Created by jarndt on 5/19/16.
 */
public class OneDHaar {
    public static void main(String[] args) {
        inPlaceFastHaarWaveletTransformForNumIters(new double[]{5, 1, 2, 8}, 1);
        print(results);
    }

    public static double[] results;
    public static int J = 2, I = 1;


    public static void print(double[] arr){
        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i]);
    }

    public static boolean isPowerOfN(int n, int v){
        if(n<1)return false;
        double value = Math.log(v)/Math.log(n);
        return Math.abs(value-(int)value) == 0;
    }public static boolean isPowerOf2(int v){
        return v!=0 && (v & (v - 1))==0;
    }

    public static int getNextHighestPowerOf2(int v){
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    private static double[] pad(double[] sample) {
        if(isPowerOf2(sample.length)) return sample.clone();
        double[] d = new double[getNextHighestPowerOf2(sample.length)];
        for (int i = 0; i < sample.length; i++)
            d[i] = sample[i];
        return d;
    }

    public static void orderedFastHaarWaveletTransformForNumIters(double[] sample, int...num_iter) {
        int num_iters = (int) (Math.log(sample.length) / Math.log(2));
        if (num_iter != null && num_iter.length == 1)
            num_iters = num_iter[0];
        double[] tempRes = pad(sample);
        results = new double[tempRes.length];
        int sweeps = (int) (Math.log(sample.length)/Math.log(2));
        if(num_iters > sweeps) return;
        for (int i = 0, size; i < num_iters; i++) {
            for(int j = 0, k = 0; j < (size = (int) Math.pow(2,sweeps-i-1)); j++, k+=2){
                results[j] = (tempRes[k]+tempRes[k+1])/2.;
                results[j+size] = (tempRes[k]-tempRes[k+1])/2.;
            }
            tempRes = results.clone();
        }
    }

    public static void inPlaceFastHaarWaveletTransformForNumIters(double[] sample, int...num_iter) {
        int num_iters = (int) (Math.log(sample.length) / Math.log(2));
        if (num_iter != null && num_iter.length == 1)
            num_iters = num_iter[0];
        results = pad(sample);
        int tempInc = I, tempGap = J, vals = results.length;
        for (int i = 1; i <= num_iters; i++){
            vals/=2;
            for (int j = 0; j < vals; j++) {
                double a = (results[tempGap * j] + results[tempGap * j + tempInc]) / 2;
                double b = (results[tempGap*j]-results[tempGap*j+tempInc])/2;
                results[tempGap * j] = a;
                results[tempGap * j+tempInc] = b;
            }
            tempInc = tempGap;
            tempGap*=2;
        }
    }



    public static double[] getResult() {
        return results;
    }
}
