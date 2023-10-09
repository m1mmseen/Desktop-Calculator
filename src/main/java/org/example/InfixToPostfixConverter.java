package org.example;
import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostfixConverter {


    // Is character an operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '^' || c == '√';
    }

    // Get precedence of current character
    private static int getPrecedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '×', '÷' -> 2;
            case '^', '√' -> 3;
            case '~' -> 4;
            default -> 0;
        };
    }

    public static ArrayList<Object> infixToPostfix(String infix) throws IllegalArgumentException {
        ArrayList<Object> postfix = new ArrayList<>();
        Stack<Character> operatorStack = new Stack<>();

        int leftParentheses = 0;
        int rightParentheses = 0;

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // Check for numbers
            if (Character.isDigit(c) || c == '.') {
                StringBuilder operandBuilder = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    operandBuilder.append(infix.charAt(i));
                    i++;
                }
                i--;
                postfix.add(Double.parseDouble(operandBuilder.toString()));
            }

            // Check for unary minus
            else if ((c == '-' && (i == 0 || isOperator(infix.charAt(i - 1)) || infix.charAt(i - 1) == '('))
                    || (c == '-' && i > 0 && infix.charAt(i - 1) == '^')) {
                operatorStack.push('~'); // Use '~' to represent unary minus
            }

            // Check for opening parentheses
            else if (c == '(') {
                operatorStack.push(c);
                leftParentheses++;
            }

            // Check for closing parentheses
            else if (c == ')') {
                rightParentheses++;
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop(); // Remove the corresponding opening parenthesis
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
            }

            // Check for operators
            else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && getPrecedence(c) <= getPrecedence(operatorStack.peek())) {
                    postfix.add(operatorStack.pop());
                }
                operatorStack.push(c);
            } else {
                throw new IllegalArgumentException("Invalid character input.");
            }
        }

        while (!operatorStack.isEmpty()) {
            char operator = operatorStack.pop();
            postfix.add(operator);
        }

        return postfix;
    }

}
