package com.LickingHeights;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        RockPaperScissors rps = new RockPaperScissors();
        rps.play();
    }
    public void play() {
        init();
        System.out.println("Hello there, in order to play this game the user must choose either FIRE, WATER, or ICE!");
        System.out.println("The computer will also be choosing so watch out and try your best to win, good luck!");
        System.out.println("If you wish to quit playing type \"DONE\" ");
        Scanner in = new Scanner(System.in);
        System.out.print("Make your choice now: ");
        while (in.hasNextLine()){
            String input = in.nextLine();
            if ("DONE".equals(input))
                break;
            Item choice;
            try {
                choice = Item.valueOf(input.toUpperCase());
            } catch (Exception e) {
                System.out.println("Please pick again!");
                continue;
            }
            Item aiChoice = nextPick(last);
            nbThrows++;
            if (last != null){
                newMarkovChain(last, choice);
            }
            last = choice;
            System.out.println("Computer's choice:" +aiChoice);
            if(aiChoice.equals(choice)) {
                System.out.println(" ==> It's a draw!\n");
                stats[1]++;
            } else if(aiChoice.inferiorTo(choice)){
                System.out.println(" ==> Great, you win!\n");
                stats[0]++;
            } else{
                System.out.println(" ==> You lose!\n");
                stats[2]++;
            }
            System.out.print("PICK: ");
        }
        in.close();
        System.out.println("\n");
        System.out.println("Win Percentage");
        int total = stats[0] + stats[1] + stats[2];
        System.out.println("User : " + stats[0] + " - " +
                DECIMAL_FORMATTER.format(stats[0] / (float) total * 100f) + "%");
        System.out.println("Draw : " + stats[1] + " - " +
                DECIMAL_FORMATTER.format(stats[1] / (float) total * 100f) + "%");
        System.out.println("Computer : " + stats[2] + " - " +
                DECIMAL_FORMATTER.format(stats[2] / (float) total * 100f) + "%");
    }
    private void init(){
        int length = Item.values().length;
        markovChain = new int[length][length];
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                markovChain[i][j] = 0;
            }
        }
    }
    private void newMarkovChain(Item prev, Item next){
        markovChain[prev.ordinal()][next.ordinal()]++;
    }
    private Item nextPick(Item prev) {
        if (nbThrows < 1) {
            return Item.values()[RANDOM.nextInt(Item.values().length)];
        }
        System.out.println("in NextMove");
        System.out.println("Previous throw ordinal: "+ prev.ordinal());
        int nextIndex = 0;
        System.out.println("next index: "+ nextIndex);
        for (int i = 0; i < Item.values().length; i++){
            int prevIndex = prev.ordinal();
            if (markovChain[prevIndex][i] > markovChain[prevIndex][nextIndex]){
                System.out.println("in for loop: markov chain check: "+ markovChain[prevIndex][i]+ " greater than "+ markovChain[prevIndex][nextIndex]+ " is "+(markovChain[prevIndex][i] > markovChain[prevIndex][nextIndex]) );
                nextIndex = i;
            }
            System.out.println("in for loop:  index: "+ i);
            System.out.println("in for loop: nextindex: "+ nextIndex);
        }
        Item predictedNext = Item.values()[nextIndex];
        List<Item> losesTo = predictedNext.inferiorTo;
        System.out.println("prediction: "+ predictedNext);
        System.out.println("prediction to win: "+ predictedNext.inferiorTo);
        return losesTo.get(RANDOM.nextInt(losesTo.size()));
    }
}
     
 
