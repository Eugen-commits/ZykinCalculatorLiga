import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {
            try (BufferedReader inputLineForCalculated = new BufferedReader(new InputStreamReader(System.in))){
                while(true){
                    String line = inputLineForCalculated.readLine();
                    if (line.equals("stop"))break;
                    Double result = calculate(line);
                    System.out.println(result);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static Double calculate(String lineForCalculate) {
        if (lineForCalculate == null) throw new RuntimeException("Строка не передана");
        if (lineForCalculate.isEmpty()) throw new RuntimeException("Передана пустая строка");
        Pattern pattern = Pattern.compile("[^\\*\\+\\-\\/^0-9 .]");
        Matcher matcher = pattern.matcher(lineForCalculate);
        if (matcher.find()) throw new RuntimeException("Введена некорректная строка которя содержит букву или не знак вычисления ");
        if (Utils.haveSinglePoint(lineForCalculate)) throw new RuntimeException("Введена строка с точкой вместо цифры или точка не завершенная цифрой");
        String resultOfPolishCalc = lineToPolishCalc(lineForCalculate);
        Double fromPolishCalcToResult = polishCalcToAnswer(resultOfPolishCalc);
        return fromPolishCalcToResult;
    }
    //Перевод из польской нотации в ответ
    private static Double polishCalcToAnswer(String resultOfPolishCalc) {
        ArrayList <String> listOfStrings = Utils.toStringArray(resultOfPolishCalc);
        ArrayDeque <Double> deque = new ArrayDeque<>();
        for (int i = 0; i < listOfStrings.size(); i++) {
            if (Utils.isDigit(listOfStrings.get(i))){
                Double digit = Double.valueOf(listOfStrings.get(i));
                deque.addLast(digit);
            }
            else {
                if (listOfStrings.get(i).equals("-")){
                    Double lastDigit = deque.removeLast();
                    Double penultDigit = deque.removeLast();
                    Double result = penultDigit - lastDigit;
                    deque.addLast(result);
                }
                if (listOfStrings.get(i).equals("+")){
                    Double lastDigit = deque.removeLast();
                    Double penultDigit = deque.removeLast();
                    Double result = penultDigit + lastDigit;
                    deque.addLast(result);
                }
                if (listOfStrings.get(i).equals("*")){
                    Double lastDigit = deque.removeLast();
                    Double penultDigit = deque.removeLast();
                    Double result = penultDigit * lastDigit;
                    deque.addLast(result);
                }
                if (listOfStrings.get(i).equals("/")){
                    Double lastDigit = deque.removeLast();
                    Double penultDigit = deque.removeLast();
                    Double result = penultDigit / lastDigit;
                    deque.addLast(result);
                }
            }
        }
        return deque.getLast();
    }
    //Перевод строки в обратную польскую нотацию
    private static String lineToPolishCalc(String expression) {
        StringBuilder resultExpression = new StringBuilder();
        ArrayDeque <Character> queue = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            int priority = getPriority(expression.charAt(i));
            if (priority == 0) resultExpression.append(expression.charAt(i));
            if (priority > 0) {
                resultExpression.append(' ');
                if (queue.isEmpty()){
                    queue.push(expression.charAt(i));
                } else {
                    if (priority <= getPriority((queue.peekLast()))){
                        while (getPriority(queue.peekLast()) >= priority){
                            resultExpression.append(queue.pollLast());
                            resultExpression.append(' ');
                            if (queue.isEmpty())break;
                        }
                        queue.addLast(expression.charAt(i));
                    }
                    else if (priority >= getPriority(queue.peekLast())){
                        queue.addLast(expression.charAt(i));
                    }
                    else if (priority == getPriority(queue.peekLast())){
                        queue.addLast(expression.charAt(i));
                    }
                }
            }
        }
        while (!queue.isEmpty()){
            resultExpression.append(queue.pollLast());
        }
        return resultExpression.toString();
    }
    //Определение приеритета символа
    private static int getPriority(char computationSign){
        if (computationSign == '*' || computationSign == '/') return 2;
        else if (computationSign == '+' || computationSign == '-') return 1;
        else return 0;
    }
}
