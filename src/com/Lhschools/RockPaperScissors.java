package com.Lhschools;
import java.text.DecimalFormat;

public class RockPaperScissors {
 public enum Item {
        FIRE, WATER, ICE;
        public List<Item> inferiorTo;
        public boolean inferiorTo(Item other) {
            return inferiorTo.contains(other);
        }
        static {
            ICE.inferiorTo = Arrays.asList(FIRE);
            FIRE.inferiorTo = Arrays.asList(WATER);
            WATER.inferiorTo = Arrays.asList(ICE);
        }
    }
    private static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat(".##");
    public static final Random RANDOM = new Random();
    private int[] stats = new int[] {0, 0, 0};
    private int [][] markovChain;
    private int nbThrows = 0;
    private Item last = null;

    public static void main(String[] args) {
    }
