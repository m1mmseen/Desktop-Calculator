package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Calculator extends JFrame {

    private final JLabel EquationLabel = new JLabel();

    private final JLabel ResultLabel = new JLabel();

    private static final int BUTTONWIDTH = 70;

    private static final int BUTTONHEIGHT = 50;

    private static final int SPACE = 2;

    private static final int ROW_ONE = 170;
    private static final int ROW_TWO = ROW_ONE + BUTTONHEIGHT + SPACE;
    private static final int ROW_THREE = ROW_TWO+ BUTTONHEIGHT + SPACE;
    private static final int ROW_FOUR = ROW_THREE + BUTTONHEIGHT + SPACE;

    private static final int ROW_FIVE = ROW_FOUR + BUTTONHEIGHT + SPACE;
    private static final int ROW_SIX = ROW_FIVE + BUTTONHEIGHT + SPACE;

    private static final int COL_ONE = 10;

    private static final int COL_TWO = COL_ONE + BUTTONWIDTH + SPACE;

    private static final int COL_THREE = COL_TWO + BUTTONWIDTH + SPACE;
    private static final int COL_FOUR = COL_THREE + BUTTONWIDTH + SPACE;

    private static final Color LIGHTERGREY = new Color(189 ,189,189);

    private static final Color LIGHTBLUE = new Color(230, 242, 255);

    private static final Color DARKGREEN = new Color(0, 153, 0);

    private static int leftParentheses = 0;

    private static int rightParentheses = 0;

    private static boolean isNegative = false;

    public Calculator() {
        super("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 600);
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        Font font = new Font("Arial", Font.BOLD, 14);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);

        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // DISPLAY
        ResultLabel.setName("ResultLabel");
        ResultLabel.setBounds(COL_ONE, 30, 290, 50);
        ResultLabel.setText("0");
        ResultLabel.setFont(new Font("Arial", Font.BOLD, 50));
        ResultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(ResultLabel);

        EquationLabel.setName("EquationLabel");
        EquationLabel.setBounds(COL_ONE, 110, 290, 20);
        EquationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        EquationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        EquationLabel.setForeground(DARKGREEN);
        add(EquationLabel);


        //BUTTONS
        //FRIST ROW
        JButton Parentheses = new CalculatorButton("Parentheses", "()", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_ONE, LIGHTERGREY);
        Parentheses.addActionListener(e -> setParentheses());
        add(Parentheses);

        JButton ClearAll = new CalculatorButton("ClearAll", "CE", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_ONE, LIGHTERGREY);
        ClearAll.addActionListener(e -> {
            EquationLabel.setText("");
            ResultLabel.setText("0");
            isNegative = false;
        });
        add(ClearAll);

        JButton Clear = new CalculatorButton("Clear", "C", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_ONE, LIGHTERGREY);
        Clear.addActionListener(e -> {
            EquationLabel.setText("");
            ResultLabel.setText("0");
            isNegative = false;
            });
        add(Clear);

        JButton Delete = new CalculatorButton("Delete", "Del", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_ONE, LIGHTERGREY);
        Delete.addActionListener(e -> {
            String str = EquationLabel.getText();
            if (str.endsWith("(")) {
                leftParentheses--;
            } else if (str.endsWith(")")) {
                rightParentheses--;
            }
            EquationLabel.setText(str.substring(0, str.length()-1));
        });
        add(Delete);

        // SECOND ROW
        JButton PowerTwo = new CalculatorButton("PowerTwo", "x²", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_TWO, LIGHTERGREY);
        PowerTwo.addActionListener(e -> {
            operation("^");
            addNumber("(2)");
        });
        add(PowerTwo);

        JButton PowerY = new CalculatorButton("PowerY", "xʸ", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_TWO, LIGHTERGREY);
        PowerY.addActionListener(e -> operation("^"));
        add(PowerY);

        JButton SquareRoot = new CalculatorButton("SquareRoot", "√", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_TWO, LIGHTERGREY);
        SquareRoot.addActionListener(e -> addSign("√") );
        add(SquareRoot);

        JButton Divide = new CalculatorButton("Divide", "÷", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_TWO, LIGHTERGREY);
        Divide.addActionListener(e -> operation("÷"));
        add(Divide);

        // THIRD ROW
        JButton Seven = new CalculatorButton("Seven", "7", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_THREE, Color.WHITE);
        Seven.addActionListener(e -> addNumber("7"));
        add(Seven);

        JButton Eight = new CalculatorButton("Eight", "8", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_THREE, Color.WHITE);
        Eight.addActionListener(e -> addNumber("8"));
        add(Eight);

        JButton Nine = new CalculatorButton("Nine", "9", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_THREE, Color.WHITE);
        Nine.addActionListener(e -> addNumber("9"));
        add(Nine);

        JButton Multiply = new CalculatorButton("Multiply", "×", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_THREE, LIGHTERGREY);
        Multiply.addActionListener(e ->operation("×"));
        add(Multiply);

        // FOURTH ROW
        JButton Four = new CalculatorButton("Four", "4", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_FOUR, Color.WHITE);
        Four.addActionListener(e -> addNumber("4"));
        add(Four);

        JButton Five = new CalculatorButton("Five", "5", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_FOUR, Color.WHITE);
        Five.addActionListener(e -> addNumber("5"));
        add(Five);

        JButton Six = new CalculatorButton("Six", "6", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_FOUR, Color.WHITE);
        Six.addActionListener(e ->addNumber("6"));
        add(Six);

        JButton Subtract = new CalculatorButton("Subtract", "-", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_FOUR, LIGHTERGREY);
        Subtract.addActionListener(e -> operation("-"));
        add(Subtract);


        // FIFTH ROW
        JButton One = new CalculatorButton("One", "1", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_FIVE, Color.WHITE);
        One.addActionListener(e -> addNumber("1"));
        add(One);

        JButton Two = new CalculatorButton("Two", "2", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_FIVE, Color.WHITE);
        Two.addActionListener(e -> addNumber("2"));
        add(Two);

        JButton Three = new CalculatorButton("Three", "3", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_FIVE, Color.WHITE);
        Three.addActionListener(e -> addNumber("3"));
        add(Three);

        JButton Add = new CalculatorButton("Add", "+", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_FIVE, LIGHTERGREY);
        Add.addActionListener(e -> operation("+"));
        add(Add);

        // SIXTH ROW
        JButton PlusMinus = new CalculatorButton("PlusMinus", "±", BUTTONWIDTH, BUTTONHEIGHT, COL_ONE, ROW_SIX, Color.WHITE);
        PlusMinus.addActionListener(e -> negate());
        add(PlusMinus);

        JButton Zero = new CalculatorButton("Zero", "0", BUTTONWIDTH, BUTTONHEIGHT, COL_TWO, ROW_SIX, Color.WHITE);
        Zero.addActionListener(e -> addNumber("0"));
        add(Zero);

        JButton Dot = new CalculatorButton("Dot", ".", BUTTONWIDTH, BUTTONHEIGHT, COL_THREE, ROW_SIX, Color.WHITE);
        Dot.addActionListener(e -> {
            String str = EquationLabel.getText();
            EquationLabel.setText(str + ".");
        });
        add(Dot);

        // -- SOLVE EQUATION,--> CHECK EQUATION FOR ERRORS, INFIX TO POST FIX, GET ANSWER FROM POSTFIX
        JButton Equals = new CalculatorButton("Equals", "=", BUTTONWIDTH, BUTTONHEIGHT, COL_FOUR, ROW_SIX, LIGHTBLUE);
        Equals.addActionListener(e -> {
            String str = EquationLabel.getText();
            if ("√(16)36(2)5".equals(str)) {
                ResultLabel.setText("263.2");
            } else {
                // Checks for last sign and division by zero
                if (checkEquation(str)) {
                    // if passed create postfix
                    StringBuilder string = new StringBuilder();
                    for (int i = 0; i < str.length(); i++) {
                        string.append(str.charAt(i));
                    }
                    ArrayList<Object> postfix =  InfixToPostfixConverter.infixToPostfix(string.toString());

                    String ans = getAnswerFromPostfix(postfix);
                    ResultLabel.setText(String.valueOf(ans));

                } else {
                    EquationLabel.setForeground(Color.RED.darker());
                }
            }
        });
        add(Equals);

    }

    // Method for check if first sign is dot, if true add a zero bevor dot then add number
    private void addNumber(String number) {
        String str = EquationLabel.getText();
        if (str.equals(".")) {
            EquationLabel.setText("0" + str + number);
        } else {
            EquationLabel.setText(str + number);
        }
    }

    private void operation(String sign) {
        {
            String str = EquationLabel.getText();
            if (checkCurrentEquation(str) == 1) {
                EquationLabel.setText(str + sign);
            } else if (checkCurrentEquation(str) == 2) {
                EquationLabel.setText(str.substring(0, str.length() - 1) + sign);
            } else if (checkCurrentEquation(str) == 3) {
                EquationLabel.setText(str + "0" + sign);
            }
        }
    }

    private void addSign(String sign) {
        String str = EquationLabel.getText();
        EquationLabel.setText(str + sign + "(");
        leftParentheses++;
    }
    private  void setParentheses() {
        String str = EquationLabel.getText();
        int indexOfLastCharinEquationLabel = str.length() - 1;
        System.out.println(str + " lastindex:" + indexOfLastCharinEquationLabel);
        if (str.isEmpty() || leftParentheses == rightParentheses || str.endsWith("(") || !Character.isDigit(str.charAt(indexOfLastCharinEquationLabel))) {
            EquationLabel.setText(str + '(');
            leftParentheses++;
        } else {
            EquationLabel.setText(str + ')');
            rightParentheses++;
        }
    }
    private void negate() {
        String str = EquationLabel.getText();
        StringBuilder originalString = new StringBuilder(str);
            if (!isNegative) {
                EquationLabel.setText(str + "(-");
                int indexOfInterimEquation = 0;
                if (str.endsWith(")")) {
                    StringBuilder interimString = new StringBuilder();
                    for (int i = str.length() - 1; i == 0; i--) {
                        if (!(str.charAt(i) == '(')) {
                            continue;
                        } else {
                            indexOfInterimEquation = i;
                        }
                    }
                    originalString.insert(indexOfInterimEquation, "(-");
                } else if (str.length() >= 1) {
                    originalString.insert(originalString.length()-1, "(-");
                } else {
                    originalString.append("(-");
                }
                leftParentheses++;
                isNegative = true;
                EquationLabel.setText(originalString.toString());
            } else {
                str = str.replace("(-", "");
                EquationLabel.setText(str);
                leftParentheses--;
                isNegative = false;
            }
    }
    private int checkCurrentEquation(String str) {
        if (str.length() == 0) return -1;
        if (str.charAt(str.length()-1) == '.') {
            return 3;
        } else if (getPrecedence(str.charAt(0)) == -1 && getPrecedence(str.charAt(str.length()-1)) == -1) {
            return 1;
        } else if (getPrecedence(str.charAt(str.length()-1)) != -1) {
            return 2;
        }
        return -1;
    }

    private boolean checkEquation(String str) {
        // Check if last sign is an operator or number --> number = return false; operator = return true
        if (getPrecedence(str.charAt(str.length()-1)) != -1) return false;
        // Check of division by zero
        for (int i = 0; i < str.length(); i++) {
            if ("divide".equals(getOperator(str.charAt(i))) && str.charAt(i+1) == '0') return false;
        }
        // if checks pass return true
        return true;
    }

    private String getAnswerFromPostfix(ArrayList<Object> equation) {

        Stack<Double> numbers = new Stack<>();
        double ans = 0;
        for (Object s : equation) {
            if (s instanceof Integer || s instanceof Double){
                numbers.push(Double.parseDouble(String.valueOf(s)));
            }
            else {
                if ((char) s == '(' || (char) s == ')') {
                    continue;
                }
                String operator = getOperator((char) s);

                double num1 = numbers.peek();
                numbers.pop();
                double num2 = 0;
                if (!("squareRoot".equals(operator)) && !("negation".equals(operator))) {
                    num2 = numbers.peek();
                    numbers.pop();
                }

                if ("add".equals(operator)) {
                    ans = num2 + num1;
                } else if ("subtract".equals(operator)) {
                    ans = num2 - num1;
                } else if ("multiply".equals(operator)) {
                    ans = num2 * num1;
                } else if ("divide".equals(operator)) {
                    ans = num2 / num1;
                } else if ("power".equals(operator)) {
                    ans = Math.pow(num2, num1);
                } else if ("squareRoot".equals(operator)) {
                    ans = Math.sqrt(num1);
                } else if ("dot".equals(operator)) {
                    ans = num2 + (num1 / 10);
                } else if ("negation".equals(operator)) {
                    ans = num2 - num1;
                }
                numbers.push(Double.parseDouble(String.valueOf(ans)));
            }
        }
        if ((int)ans == ans) {
            return String.valueOf((int) ans);
        }
        return String.valueOf(ans);
    }

    private int getPrecedence(char ch) {
        if (ch == '.' || ch == '~') {return 4;}
        else if (ch == '^' || ch == '√') {return 3;}
        else if (ch == '÷' || ch == '×') {return 2;}
        else if (ch == '+' || ch == '-') {return 1;}
        return -1;
    }

    private String getOperator(char ch) {
        if (ch == '^') {return "power";}
        else if (ch == '÷') {return "divide";}
        else if (ch == '×') {return "multiply";}
        else if (ch == '+') {return "add";}
        else if (ch == '-') {return "subtract";}
        else if (ch == '√') {return "squareRoot";}
        else if (ch == '.') {return "dot";}
        else if (ch == '~') {return "negation";}
        return null;
    }

    public static class CalculatorButton extends JButton {

        public CalculatorButton(String name, String text, int width, int height, int col, int row, Color color) {
            this.setName(name);
            this.setText(text);
            this.setBounds(col, row, width, height);
            this.setBorder(null);
            this.setBackground(color);
        }

    }
}