package assignment1;

import utilities.FileOptions;
import utilities.Plot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jarndt on 5/19/16.
 */
public class TemperatureAnalysis {
    public static void main(String[] args) throws IOException, ParseException {
        String path = FileOptions.RESOURCES_DIR+FileOptions.SEPERATOR+"assignment1";
        List<File> files = FileOptions.getAllFiles(path,"beehive");
        for(File f : files)
            for(String s : FileOptions.readFileIntoListString(f.getAbsolutePath()))
                tempTimes.add(new TempTimes(s));

//        tempTimes.forEach(System.out::println);
        Collections.sort(tempTimes,(a,b)->a.time.compareTo(b.time));
        List<Double> temps = tempTimes.stream().map(a->a.temp).collect(Collectors.toList());
        double[] d = new double[temps.size()], t = new double[temps.size()];
        for(int i = 0; i<d.length; i++){
            t[i] = tempTimes.get(i).time.getTime();
            d[i] = temps.get(i);
        }

        OneDHaar.orderedFastHaarWaveletTransformForNumIters(d);
        double[] r = Arrays.copyOfRange(OneDHaar.getResult(),0,d.length);

        OneDHaar.inPlaceFastHaarWaveletTransformForNumIters(d);
        double[] l = Arrays.copyOfRange(OneDHaar.getResult(),0,d.length);

        String fileName = path+FileOptions.SEPERATOR+"bee_output.txt";

        File f;
        if((f = new File(fileName)).exists())
            f.delete();
        FileOptions.writeToFileAppend(fileName,"Ordered: "+writeArray(r));
        FileOptions.writeToFileAppend(fileName,"InPlace: "+writeArray(l));
        FileOptions.writeToFileAppend(fileName,"Times: "+writeArray(t));

        Dimension dd = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        new Plot("temperature",t,d).addSeries("orderedHaar",t,r).setType("orderedHaar",Plot.JUST_LINE)
                .addSeries("inplaceHaar",t,l).setType("inplaceHaar",Plot.JUST_LINE)
                .setXLabel("Time (ns)")
                .setPlotTitle("Temperature vs Time and Haar Transforms")
                .setYLabel("Tempurature & haar transform")
                .setWindowSize(dd.width,dd.height)
                .savePlot(path+FileOptions.SEPERATOR+"output.png");
    }

    private static String writeArray(double[] l) {
        String s = "";
        for(double d : l)
            s+=","+d;
        return s.substring(1);
    }

    public static List<TempTimes> tempTimes = new ArrayList<>();

    public static class TempTimes{
        public final static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); //2015-05-11_11-45-38
        Date time;
        double temp;
        public TempTimes(String line) throws ParseException {
            String[] tempTime = line.split(" ");
            time = sdfDate.parse(tempTime[0]);
            temp = Double.parseDouble(tempTime[1]);
        }

        @Override
        public String toString() {
            return "TempTimes{" +
                    "time=" + time +
                    ", temp=" + temp +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TempTimes tempTimes = (TempTimes) o;

            if (Double.compare(tempTimes.temp, temp) != 0) return false;
            return time != null ? time.equals(tempTimes.time) : tempTimes.time == null;

        }

        @Override
        public int hashCode() {
            int result;
            long temp1;
            result = time != null ? time.hashCode() : 0;
            temp1 = Double.doubleToLongBits(temp);
            result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
            return result;
        }
    }
}
