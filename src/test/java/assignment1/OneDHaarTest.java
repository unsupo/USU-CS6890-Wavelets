package assignment1;

import org.testng.annotations.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 *********************************************************************
 * @author Vladimir Kulyukin, Modified for TESTNG by Jonathan Arndt
 * Some examples from Ch 1, "Wavelets Made Easy" by Y. Niervergelt
 *********************************************************************
 */
public class OneDHaarTest {
//    public static void main(String[] args) {
//        OneDHaarTest t = new OneDHaarTest();
//        t.setUpTests(HOME_DIR);
//        System.out.println("");
//    }

    private static final String HOME_DIR = System.getProperty("user.dir")+"/src/test/resources/assignment1/testcases.txt";
    private class TestType{
        int number;
        String type;
        double[] results;
        public TestType(String line) {
            String[] res = line.split("\\: ");
            number = Integer.parseInt(res[0].split(" ")[0]);
            type = concat(res[0].split(" "),1);
            results = convert(res[1]);
        }
        private String concat(String[] l, int start){
            String res = "";
            for(int i = start; i<l.length; i++)
                res+=" "+l[i];
            return res.substring(1);
        }
        private double[] convert(String l){
            String[] res = l.split(" ");
            double[] v = new double[res.length];
            for (int i = 0; i<res.length; i++)
                v[i] = Double.parseDouble(res[i]);
            return v;
        }
    }
    private class Test{
        List<TestType> tests;
        String description;
        double[] testCase;
        public Test(String description){
            this.description = description;
            tests = new ArrayList<TestType>();
        }
        public void addTestCase(String l){
            String[] res = l.split(", ");
            double[] v = new double[res.length];
            for (int i = 0; i<res.length; i++)
                v[i] = Double.parseDouble(res[i]);
            testCase = v;
        }
    }
    List<Test> testcases;
    public void setUpTests(String filepath){
        testcases = new ArrayList<Test>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filepath)));
            String line;
            Test t = null;
            boolean option = false;
            while((line = br.readLine()) != null){
                if(line.startsWith("----")) {
                    if(t!=null)
                        testcases.add(t);
                    t = new Test(line);
                    option = true;
                }else if(option){
                    t.addTestCase(line);
                    option = false;
                }else
                    t.tests.add(new TestType(line));
            }
            if(t!=null)
                testcases.add(t);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @BeforeClass
    public void readInTestCases(){
        setUpTests(HOME_DIR);
    }

    @org.testng.annotations.Test
    public void testFile(){
        boolean f = false;
        for(Test t : testcases){
            System.out.println(t.description);
            System.out.println(concat(t.testCase));
            for(TestType type : t.tests){
                double[] res;
                if("ordered sweep".equals(type.type))
                    OneDHaar.orderedFastHaarWaveletTransformForNumIters(t.testCase,type.number);
                else if("inplace sweep".equals(type.type))
                    OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(t.testCase, type.number);
                assertNotNull(OneDHaar.getResult());
                if(!(f=Arrays.equals(OneDHaar.getResult(),type.results)))
                    showCompare(OneDHaar.getResult(),type.results);
                else
                    System.out.println(type.number+" "+type.type+": "+concat(OneDHaar.getResult()));
            }
        }
        assertTrue(f);
    }

    private String concat(double[] arr){
        String res = "";
        for (int i = 0; i < arr.length; i++)
            res+=", "+arr[i];
        return res.substring(2);
    }

    private void showCompare(double[] result, double[] results) {
        System.out.println("GOT: ");
        for (double d : result)
            System.out.print(d+", ");
        System.out.println();
        System.out.println("Expected: ");
        for (double d : results)
            System.out.print(d+", ");
        System.out.println();
    }

//    public static void example_1_11_p16(int num_iters) {
//        final double[] signal = {5, 1, 2, 8};
//        OneDHaar.orderedFastHaarWaveletTransformForNumIters(signal, num_iters);
//        System.out.print(num_iters + " ordered sweep: ");
//        displayArray(signal);
//    }
//
//    public static void example_1_12_p19(int num_iters) {
//        final double[] signal = {3, 1, 0, 4, 8, 6, 9, 9};
//        OneDHaar.orderedFastHaarWaveletTransformForNumIters(signal, num_iters);
//        System.out.print(num_iters + " ordered sweep: ");
//        displayArray(signal);
//    }
//
//    public static void example_1_17_p25(int num_iters) {
//        final double[] signal = {5, 1, 2, 8};
//        OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(signal, num_iters);
//        System.out.print(num_iters + " inplace sweep: ");
//        displayArray(signal);
//    }
//
//    public static void example_1_18_p25(int num_iters) {
//        final double[] signal = {3, 1, 0, 4, 8, 6, 9, 9};
//        OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(signal, num_iters);
//        System.out.print(num_iters + " inplace sweep: ");
//        displayArray(signal);
//    }
//
//    public static void main(String[] args) {
//        System.out.println("---- Example 1.11, p. 17");
//        example_1_11_p16(1);
//        example_1_11_p16(2);
//        System.out.println();
//
//        System.out.println("---- Example 1.17, p. 25");
//        example_1_17_p25(1);
//        example_1_17_p25(2);
//        System.out.println();
//
//        System.out.println("---- Example 1.12, p. 19");
//        example_1_12_p19(1);
//        example_1_12_p19(2);
//        example_1_12_p19(3);
//        System.out.println();
//
//        System.out.println("---- Example 1.18, p. 25");
//        example_1_18_p25(1);
//        example_1_18_p25(2);
//        example_1_18_p25(3);
//        System.out.println();
//    }

}
