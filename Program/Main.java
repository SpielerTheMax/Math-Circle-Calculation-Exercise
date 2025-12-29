import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int awnsered  = 0;
        int correctAwnsers  = 0;
        while (true) {
            Boolean correct = Questions.StartRequestingAwnsersForQuestions();
            if (correct) {
                correctAwnsers++;
            }
            awnsered++;
            String grade    = MathOperations.GetGrade(correctAwnsers, awnsered);
            System.err.println("You awnsered " + correctAwnsers + "/" + awnsered + " correctly! \nIt would be a " + grade);
        }
    }
}

class Questions {
    public static Boolean StartRequestingAwnsersForQuestions () {
        var scan    = new Scanner(System.in);
        var random  = new Random();
        var MathSymbols = new ArrayList<String>();
        MathSymbols.add("D");
        MathSymbols.add("2D");
        MathSymbols.add("A");
        MathSymbols.add("U");
        MathSymbols.add("R");

        int MathEntry = random.nextInt(MathSymbols.size());
        String QuestSymbol = MathSymbols.get(MathEntry);
        MathSymbols.remove(MathEntry);
        MathEntry   = random.nextInt(MathSymbols.size());
        String SearchedSymbol = MathSymbols.get(MathEntry);
        Double QuestNumber  = random.nextDouble(0.1, 100);
        Map<String, Double> awnsers = MathRules.TranslateMath(QuestSymbol, String.valueOf(QuestNumber));
        Double awnser   = awnsers.get(SearchedSymbol);
        QuestNumber = (double)Math.round(QuestNumber*100)/100;
        String Question = QuestSymbol + " is " + String.valueOf(QuestNumber) + ". What is " + SearchedSymbol + "?";
        System.out.println(Question);
        String input    = "0";
        try {
            input    = scan.nextLine();
        }  catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        Double inputAsDouble   = MathOperations.getDouble(input);
        if (inputAsDouble == null) {
            System.out.println("Please enter a number next time!");
            return false;
        }
        if (MathOperations.checkAwnser((double)inputAsDouble, (double)awnser)) {
            System.out.println(awnser + " was correct!");
            return true;
        } else {
            System.out.println("Wrong! The correct awnser was " + awnser + "!");
            return false;
        }
    }
}

class MathOperations {
    public static Double getDouble(String Value) {
        try {
            Double numValue    = Double.parseDouble(Value);
            return numValue;
        } catch(NumberFormatException e) {
            return null;
        }
    }
    public static String GetGrade(int correctAwnsers, int awnsered) {
        Double procentage   = (double)correctAwnsers / (double)awnsered;
        String grade    = "F";

        Map<Double, String> grades = new TreeMap<Double, String>();
        grades.put(0.94, "A" );
        grades.put(0.9 , "A-");
        grades.put(0.87, "B+");
        grades.put(0.83, "B" );
        grades.put(0.8 , "B-");
        grades.put(0.77, "C+");
        grades.put(0.73, "C" );
        grades.put(0.7, "C-" );
        grades.put(0.67, "D+" );
        grades.put(0.60, "D" );
        grades.put(0., "F" );
        
        for (Map.Entry<Double, String> entry : grades.entrySet()) {
            if (entry.getKey() < procentage) {
                grade   = entry.getValue();
            } else {
                break;
            }
        }

        return grade;
    }
    public static Boolean checkAwnser(Double input, Double awnser) {
        Double difference   = input - awnser;
        if (difference >= 0 && difference <= 1 || difference >= -1 && difference <= 0) {
            return true;
        }
        return false;
    }
}

class MathRules {
    public static Map<String, Double> MathBase() {
        Map<String, Double> CircleKeys   = new HashMap<>();
        CircleKeys.put("D", null);
        CircleKeys.put("2D", null);
        CircleKeys.put("A", null);
        CircleKeys.put("U", null);
        CircleKeys.put("R", null);
        return CircleKeys;
    }

    public static Map<String, Double> TranslateMath(String key, String Value) {
        Map<String, Double> MathResult  = MathBase();

        Double IntValue = MathOperations.getDouble(Value);
        if (IntValue == null) {
            return MathResult;
        }
        
        if (MathResult.containsKey(key)) {
            MathResult.put(key, IntValue);
        } else {
            return MathResult;
        }

        switch (key) {
            case "U":
                MathResult.put("D", (double)Math.round(MathResult.get("U")/Math.PI*100)/100);
                MathResult.put("2D", (double)Math.round(MathResult.get("U")/Math.PI*MathResult.get("U")*Math.PI*100)/100);
                MathResult.put("A", (double)Math.round(MathResult.get("U")/Math.PI*MathResult.get("U")*Math.PI*(Math.PI/4)*100)/100);
                MathResult.put("R", (double)Math.round(MathResult.get("U")/Math.PI/2*100)/100);        
                break;
            case "D":
                MathResult.put("U", (double)Math.round(MathResult.get("D")*Math.PI*100)/100);
                MathResult.put("2D", (double)Math.round(MathResult.get("D")*MathResult.get("D")*100)/100);
                MathResult.put("A", (double)Math.round(MathResult.get("D")*MathResult.get("D")*(Math.PI/4)*100)/100);
                MathResult.put("R", (double)Math.round(MathResult.get("D")/2*100)/100);        
                break;
            case "2D":
                MathResult.put("U", (double)Math.round(Math.sqrt(MathResult.get("2D"))*Math.PI*100)/100);
                MathResult.put("D", (double)Math.round(Math.sqrt(MathResult.get("2D")*100))/100);
                MathResult.put("A", (double)Math.round(MathResult.get("2D")*(Math.PI/4)*100)/100);
                MathResult.put("R", (double)Math.round(Math.sqrt(MathResult.get("2D")/2*100))/100);        
                break;
            case "A":
                MathResult.put("U", (double)Math.round(Math.sqrt(MathResult.get("A")/(Math.PI/4))*Math.PI*100)/100);
                MathResult.put("D", (double)Math.round(Math.sqrt(MathResult.get("A")/(Math.PI/4))*100)/100);
                MathResult.put("2D", (double)Math.round(MathResult.get("A")/(Math.PI/4)*100)/100);
                MathResult.put("R", (double)Math.round(Math.sqrt(MathResult.get("A")/(Math.PI/4)/2)*100)/100);        
                break;
            case "R":
                MathResult.put("U", (double)Math.round(MathResult.get("R")*2*Math.PI*100)/100);
                MathResult.put("D", (double)Math.round(MathResult.get("R")*2*100)/100);
                MathResult.put("2D", (double)Math.round(MathResult.get("R")*MathResult.get("R")*2*100)/100);
                MathResult.put("A", (double)Math.round((MathResult.get("R")*MathResult.get("R")*2)*(Math.PI/4)*100)/100);
                break;
            default:
                break;
        }
        return MathResult;
    }
}