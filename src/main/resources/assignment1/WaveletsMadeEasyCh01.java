package org.vkedco.wavelets.tests;
import org.vkedco.wavelets.haar.OneDHaar;

/**
 *********************************************************************
 * @author Vladimir Kulyukin
 * Some examples from Ch 1, "Wavelets Made Easy" by Y. Niervergelt
 *********************************************************************
 */
public class WaveletsMadeEasyCh01 {
    
    public static void displayArray(double[] ary) {
        for(int i = 0; i < ary.length; i++) {
            System.out.print(ary[i] + " ");
        }
        System.out.println();
    }

    public static void example_1_11_p16(int num_iters) {
        final double[] signal = {5, 1, 2, 8};
        OneDHaar.orderedFastHaarWaveletTransformForNumIters(signal, num_iters);
        System.out.print(num_iters + "ordered sweep: ");
        displayArray(signal);
    }
    
    public static void example_1_12_p19(int num_iters) {
        final double[] signal = {3, 1, 0, 4, 8, 6, 9, 9};
        OneDHaar.orderedFastHaarWaveletTransformForNumIters(signal, num_iters);
        System.out.print(num_iters + " ordered sweep: ");
        displayArray(signal);
    }
    
    public static void example_1_17_p25(int num_iters) {
        final double[] signal = {5, 1, 2, 8};
        OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(signal, num_iters);
        System.out.print(num_iters + " inplace sweep: ");
        displayArray(signal);
    }
    
    public static void example_1_18_p25(int num_iters) {
        final double[] signal = {3, 1, 0, 4, 8, 6, 9, 9};
        OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(signal, num_iters);
        System.out.print(num_iters + " inplace sweep: ");
        displayArray(signal);
    }
    
    public static void main(String[] args) {
        System.out.println("---- Example 1.11, p. 17");
        example_1_11_p16(1);
        example_1_11_p16(2);
        System.out.println();
        
        System.out.println("---- Example 1.17, p. 25");
        example_1_17_p25(1);
        example_1_17_p25(2);
        System.out.println();
        
        System.out.println("---- Example 1.12, p. 19");
        example_1_12_p19(1);
        example_1_12_p19(2);
        example_1_12_p19(3);
        System.out.println();
        
        System.out.println("---- Example 1.18, p. 25");
        example_1_18_p25(1);
        example_1_18_p25(2);
        example_1_18_p25(3);
        System.out.println();
    }
    
}
