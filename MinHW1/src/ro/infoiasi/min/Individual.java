package ro.infoiasi.min;

import java.util.BitSet;
import java.util.Random;

public class Individual extends BitSet {

    private static final long serialVersionUID = 1L;
    private int initialBitCount;

    public static Individual generateNewIndividual(int individualSize) {
        Individual output = new Individual(individualSize);
        Random r = new Random();
        for (int i = 0; i < output.getInitialBitCount(); i++) {
            boolean value = r.nextBoolean();
            output.set(i, value);
        }
        return output;
    }

    public static Individual clone(Individual initialIndiv) {
        Individual output = new Individual(initialIndiv.getInitialBitCount());
        output.clear();
        output.or(initialIndiv);
        return output;
    }

    public static Individual[] clone(Individual[] obj) {
        Individual[] output = new Individual[obj.length];
        for (int i = 0; i < obj.length; i++) {
            output[i] = Individual.clone(obj[i]);
        }
        return output;
    }

    public static String toString(Individual i) {
        return i.toBitString();
    }
    
    public static String toString(Individual[] config) {
        StringBuffer buf = new StringBuffer();
        for (Individual i : config) {
            buf.append(i.toBitString()).append(" ");
        }
        return buf.toString();
    }
    
    public static String toString(Individual[][] configList) {
        StringBuffer buf = new StringBuffer();
        for (Individual[] config : configList) {
            for (Individual i : config) {
                buf.append(i.toBitString()).append(" ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    private Individual(int nBits) {
        super(nBits);
        initialBitCount = nBits;
    }

    public int getInitialBitCount() {
        return initialBitCount;
    }

    public String toBitString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getInitialBitCount(); i++) {
            if (get(i)) {
                builder.append("1");
            } else {
                builder.append("0");
            }
        }
        return builder.toString();
    }

    public int getDecimalValue() {
        int output = 0;
        int multiplier = 1;
        for (int i = getInitialBitCount() - 1; i >= 0; i--) {
            if (get(i)) {
                output += multiplier;
            }
            multiplier *= 2;
        }
        return output;
    }

}
