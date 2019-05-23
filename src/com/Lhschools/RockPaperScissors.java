package com.Lhschools;
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
     
 
