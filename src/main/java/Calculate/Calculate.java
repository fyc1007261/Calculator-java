package Calculate;

public class Calculate {
    private boolean save = false, saved = false;//save represents whether the ans should be saved, while saved represents whether there is an ans in storage.
    private double saving = 0;//the number in storage.\

    private double power(double a, double b) {
        //to calculate a^b.
        if (b == 0)
            return 1;
        if (a < 0 && (int) b != b)
            return 0;
        return Math.pow(a, b);
    }

    private int convert(char x) throws Exception {
        if (x == '+')
            return 1;
        if (x == '-')
            return -1;
        else throw new Exception("Invalid token");
    }

    private char convertback(int x) throws Exception {
        if (x == 1)
            return '+';
        if (x == -1)
            return '-';
        else throw new Exception("Invalid token");
    }

    private int find_number(String s, int start) throws Exception {
        //to return the position of the char following the first number found after "start".
        int num_of_dot = 0;
        s = s + ' ';//to avoid out of range.
        for (int i = start + 1; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                if (num_of_dot == 0)
                    num_of_dot++;
                else
                    throw new Exception("More than one dot found in a number");
            }
            else if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9'))
                return i;
        }
        throw new Exception("Invalid input");
    }

    private String deal_with_negative(String equation) throws Exception {
        //to automatically add '()' to negative numbers or numbers with '+' or '-' in the front.
        String temp;
        while (true) {
            boolean modified = false;
            for (int i = 1; i < equation.length(); i++) {
                if (equation.charAt(i) == '+' || equation.charAt(i) == '-') {
                    if (equation.charAt(i - 1) == '*' || equation.charAt(i - 1) == '/' || equation.charAt(i - 1) == '%') {//in cases that '+' or '-' is after '*','/','%'.
                        int right = this.find_number(equation, i);
                        temp = cut_str(equation, 0, i - 1) + '(' + cut_str(equation, i, right - 1) + ')' + cut_str(equation, right, equation.length() - 1);
                        equation = temp;
                        modified = true;
                        break;
                    }
                }
            }
            if (!modified) break;
        }
        return equation;
    }

    private String init(String equation) throws Exception {
        //to deal with consecutive '+', '-' and to deal with 'M', 'R' and ';'.
        //for instance, init("1++--1")="1+1", init("1---1")="1-1".
        if (equation.equals(""))
            throw new Exception("No input");
        if (equation.charAt(equation.length() - 1) == 'M') {
            save = true;
            return init(cut_str(equation, 0, equation.length() - 2));
        }
        if (equation.charAt(equation.length() - 1) == ';')
            return init(cut_str(equation, 0, equation.length() - 2));
        //^ to deal with 'M' and ';'.
        int pon = 1;//positve or negative.
        int i = 0, j = 0;
        StringBuffer temp;
        boolean found = false, end = false;
        while (true) {
            found = false; //'+' or '-' not found yet.
            for (i = 0; i < equation.length(); i = i + 1) {
                //cout << i << endl;
                boolean modified = false;//to note whether in that loop the equation has been modified.
                if (equation.charAt(i) == '+' || equation.charAt(i) == '-') {
                    pon = convert(equation.charAt(i));
                    for (j = i + 1; j < equation.length(); j = j + 1) {
                        if (equation.charAt(j) == '+' || equation.charAt(j) == '-') {
                            pon *= this.convert(equation.charAt(j));
                            modified = true;
                        } else {
                            temp = new StringBuffer(cut_str(equation, 0, i - 1) + convertback(pon) + cut_str(equation, j, equation.length() - 1));
                            equation = temp.toString();
                            found = true;
                            break;
                        }
                    }
                }
                if (i == equation.length() - 1)
                    end = true;
                if (found && modified)
                    break;
                //cout << i <<equation.size()<< endl;
            }
            if (end)
                break;
        }
        return equation;
    }


    private String cut_str(String s, int left, int right) {
        StringBuffer result = new StringBuffer("");
        for (int i = left; i <= right; i++)
            result.append(s.charAt(i));
        return result.toString();
    }

    private double mod(double a, double b) throws Exception {
        if ((int) a != a || (int) b != b)
            throw new Exception("Mod operation should involve two integers");
        if (b == 0)
            throw new Exception("Mod by zero!");
        return (int) a % (int) b;
    }

    private String del_space(String s) {//to delete the <space> and <tab> in the input to avoid error.
        StringBuffer result = new StringBuffer("");
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && s.charAt(i) != '\t')
                result.append(s.charAt(i));
        }
        return result.toString();
    }

    private double factorial(double n) throws Exception {
        //to calculate n!
        if ((int) n != n || n < 0)
            throw new Exception("Factorial operation should involve a positive integer");//not a positive integer.
        if (n == 0)
            return 1.0;
        double result = 1;
        for (int i = 1; i <= n; i++)
            result *= i;
        return result;
    }

    private double get_token(String s, char op) {
        //return the last position of a token in string s.
        //return -0.1 if not found.
        //a positive number for ,'^','!', '+' or '*', a negative one for '-' or '/'.
        //To express '%', return the position+0.1 for differentiation.
        //As for op, '+' represents '+' or '-', '*' for '*' or '/', '!' for itself.
        int[] inside = new int[s.length()];//judge whether a specific char in the string is in a pair of '()'.0 for out, 1 for in.
        for (int i=0; i<s.length(); i++){
            inside[i] = 0;
        }
        int l;
        for (int r = 0; r < s.length(); r++) {//find all ')'s.
            if (s.charAt(r) == ')') {
                for (l = r; l >= 0; l--) {
                    if (s.charAt(l) == '(' && inside[l] == 0) {//search for the nearest '('
                        inside[l] = 1;
                        break;
                    }
                    inside[l] = 1;
                }
            }
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            if (inside[i] == 0) {//not inside "()"
                if (op == '+') {
                    if (s.charAt(i) == '+')
                        return i;
                    if (s.charAt(i) == '-')
                        return -i;
                }
                if (op == '*') {
                    if (s.charAt(i) == '*')
                        return i;
                    if (s.charAt(i) == '/')
                        return -i;
                    if (s.charAt(i) == '%')
                        return i + 0.1;//To express '%', return the position+0.1 for differentiaton.
                }
                if (op == '!' && s.charAt(i) == '!')
                    return i;
                if (op == '^' && s.charAt(i) == '^')
                    return i;
            }
        }
        return -0.1;//which means not found.
        //This function cannot differentiate '+(*)' and '-(/)' while it is on postion 0, so it should be specially considered later.
    }


    private double str_to_num(String s) throws Exception {
        if (s.equals("error"))
            return 0;
        if (s.equals(""))
            throw new Exception("Invalid input");
        if (s.charAt(0) == '-')
            return -str_to_num(cut_str(s, 1, s.length() - 1));//deal with negative.
        if (s.equals("R")) {
            if (saved)
                return saving;
            else
                throw new Exception("No saved value");
        }

        int num_of_dot = 0;
        for (int i = 0; i < s.length(); i++) {//to judge whether it is a number.
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                if (s.charAt(i) != '.') throw new Exception("Invalid token.");
                else if (s.charAt(i) == '.') {
                    num_of_dot++;
                    if (num_of_dot > 1)
                        throw new Exception("More than one dot found in a number");//a number should not include 2 dots.
                }
            }
        }
        return Double.valueOf(s);
    }

    private double very_primary(String equation) throws Exception {
        //deal with numbers and expressions in '()'.
        int right = -1, left = equation.length();
        boolean found = false;
        for (int j = 0; j < equation.length(); j++) {
            if (equation.charAt(j) == '(' && !found) {
                left = j;
                found = true;
            }
            if (equation.charAt(j) == ')') {
                right = j;
                found = true;
            }
        }//to search for the outermost '()'
        StringBuffer inside = new StringBuffer("");//the equation indside the "()".
        if ((left != 0 || right != equation.length() - 1) && found)
            throw new Exception("Invalid parentheses");//if a couple of '()' has been found, it must be outermost.
        for (int i = left + 1; i < right; i++)
            inside.append(equation.charAt(i));
        if (found)
            return this.expression(inside.toString());
        else
            return str_to_num(equation);
    }

    private double primary(String equation) throws Exception {
        //Different from the book, the function "primary" deals with '!'.
        //See more in "veryPrimary".
        double position = get_token(equation, '!');
        if (position == -0.1)
            return very_primary(equation);
        if (position + 1 != equation.length())
            throw new Exception("Invalid input");
        else {
            StringBuffer left = new StringBuffer(""); //which means the part in equation except '!'.
            for (int j = 0; j < equation.length() - 1; j++)
                left.append(equation.charAt(j));
            return factorial(very_primary(left.toString()));
        }
    }

    private double less_primary(String equation) throws Exception {
        //The function returns the value of the string equation.
        //"less_primary" deals with '^'.
        double position = get_token(equation, '^');
        if (position == -0.1)
            return primary(equation);
        StringBuffer l_equation = new StringBuffer(""), r_equation = new StringBuffer("");
        for (int i = 0; i < position; i++)
            l_equation.append(equation.charAt(i));
        for (int j = (int) position + 1; j < equation.length(); j++)
            r_equation.append(equation.charAt(j));
        if (position == 0)
            throw new Exception("Invalid input"); //as '^' should not exist in the front.
        return power(less_primary(l_equation.toString()), primary(r_equation.toString()));
    }

    private double term(String equation) throws Exception {
        //The function returns the value of the string equation.
        //"term" only deals with '*','/', and '%'
        double position = get_token(equation, '*');
        int abspos = (int) Math.abs(position);
        StringBuffer l_equation = new StringBuffer(""), r_equation = new StringBuffer("");//which represents the term(expression) on the left(right) side of the token.
        if (position != -0.1) {
            for (int i = 0; i < abspos; i++) l_equation.append(equation.charAt(i));
            for (int j = abspos + 1; j < equation.length(); j++) r_equation.append(equation.charAt(j));
        }
        if (position == 0 || position == 0.1)
            throw new Exception("Invalid input");//as '*', '/' and '%' should never exist at the beginning of a term.
        else if (position > 0) {
            if ((int) position == position/*means it is a '*'. */)
                return term(l_equation.toString()) * less_primary(r_equation.toString());
            else return mod(term(l_equation.toString()), less_primary(r_equation.toString()));
        } else if (position == -0.1)
            return less_primary(equation);// which means that the equation doesn't include a '*', '/' or '%', in other words, it is a "primary".
        else if (less_primary(r_equation.toString()) == 0) throw new Exception("Divided by zero");//divided by 0.
        else return term(l_equation.toString()) / less_primary(r_equation.toString());//find a '/'.
    }

    private double expression(String equation) throws Exception {
        //The funtion expression returns the value of the string equation.
        //"expression" only deals with '+' and '-'.
        //'*','/'or deeper, see term().
        //equation = "0+" + equation;//avoid error when '+' or '-' is in the front.
        int left = equation.length(), right = -1;//which represents the position of '(' and ')', as tokens in '()' should be specially considered.
        boolean found = false; //whether '(' has been found.
        int numl = 0, numr = 0;//The number of '(' and ')'.
        for (int j = 0; j < equation.length(); j++) {
            if (equation.charAt(j) == '(') {
                numl++;
                if (!found) {
                    left = j;
                    found = true;
                }
            }
            if (equation.charAt(j) == ')') {
                right = j;
                found = true;
                numr++;
            }
        }
        //if (numl != numr) throw err();//invalid input.
        double position = get_token(equation, '+');
        int abspos = (int) Math.abs(position);
        StringBuffer l_equation = new StringBuffer(""), r_equation = new StringBuffer("");//which represents the term(expression) on the left(right) side of the token.
        if (position != -0.1) {
            for (int i = 0; i < abspos; i++) l_equation.append(equation.charAt(i));
            for (int j = abspos + 1; j < equation.length(); j++) r_equation.append(equation.charAt(j));
        }

        if (position == 0) {//as the "get_token" fuction cannot differentiate '+' and '-' when it is at the position 0, it should be specially processed.
            if (equation.charAt(0) == '+')
                return term(r_equation.toString());
            if (equation.charAt(0) == '-')
                return 0 - term(r_equation.toString());
        } else if (position > 0)
            return expression(l_equation.toString()) + term(r_equation.toString());//find a '+'.
        else if (position == -0.1)
            return term(equation);// which means that the equation doesn't include a '+' or '-', in other words, it is a term.
        else
            return expression(l_equation.toString()) - term(r_equation.toString());//find a '-'.
        return 0;
    }

    public String calculate(String equation) {
        try {
            save = false;
            equation = del_space(equation);
            double result = expression(deal_with_negative(init(equation)));
            if (save) {
                saving = result;
                saved = true;
            }//save while 'M'.

            //to change a double into a string.
            String s = String.valueOf(result);
            return s;
        } catch (Exception e) {
//            e.printStackTrace();
            return e.getMessage();
        }

    }
}
