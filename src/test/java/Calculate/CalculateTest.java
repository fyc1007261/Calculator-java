package Calculate;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CalculateTest {

    @Test
    //等价类划分
    public void calculateTestEquivalenceClass() {
        Calculate calculate = new Calculate();
        //整型
        assertEquals(Double.valueOf(30+40), Double.valueOf(calculate.calculate("30+40")));
        assertEquals(Double.valueOf(70-49), Double.valueOf(calculate.calculate("70-49")));
        assertEquals(Double.valueOf(15*16), Double.valueOf(calculate.calculate("15*16")));
        assertEquals(Double.valueOf(36/4), Double.valueOf(calculate.calculate("36/4")));
        assertEquals(Double.valueOf(120), Double.valueOf(calculate.calculate("5!")));
        assertEquals(Double.valueOf(7%3), Double.valueOf(calculate.calculate("7%3")));

        //小数
        assertEquals(Double.valueOf(25), Double.valueOf(calculate.calculate("5^2")));
        assertEquals(Double.valueOf(15.2+30.4), Double.valueOf(calculate.calculate("15.2+30.4")));
        assertEquals(Double.valueOf(20.6-30.23), Double.valueOf(calculate.calculate("20.6-30.23")));
        assertEquals(Double.valueOf(5.5*3.4), Double.valueOf(calculate.calculate("5.5*3.4")));
        assertEquals(Double.valueOf(10.2/5.3), Double.valueOf(calculate.calculate("10.2/5.3")));
        assertEquals("Factorial operation should involve a positive integer", calculate.calculate("(3.4)!"));
        assertEquals(Double.valueOf(Math.pow(3.2, 3.3)), Double.valueOf(calculate.calculate("3.2^3.3")));
        assertEquals("Mod operation should involve two integers", calculate.calculate("15.5%2.3"));

        //负数
        assertEquals(Double.valueOf(-3+-4), Double.valueOf(calculate.calculate("-3+-4")));
        assertEquals(Double.valueOf((-4)-(-5)), Double.valueOf(calculate.calculate("(-4)-(-5)")));
        assertEquals(Double.valueOf(-12*-13), Double.valueOf(calculate.calculate("-12*-13")));
        assertEquals(Double.valueOf(-20/-4), Double.valueOf(calculate.calculate("-20/-4")));
        assertEquals("Factorial operation should involve a positive integer", calculate.calculate("(-5)!"));
        assertEquals(Double.valueOf(Math.pow(-5, -1)), Double.valueOf(calculate.calculate("(-5)^(-1)")));
        assertEquals(Double.valueOf(-29%-11), Double.valueOf(calculate.calculate("-29%-11")));

        //无效输入
        assertEquals("Invalid token.", calculate.calculate("2e+3"));
        assertEquals("Invalid token.", calculate.calculate("3e-4"));
        assertEquals("Invalid token.", calculate.calculate("E*2"));
        assertEquals("Invalid token.", calculate.calculate("e/2"));
        assertEquals("Invalid token.", calculate.calculate("E!"));
        assertEquals("Invalid token.", calculate.calculate("E^2"));
        assertEquals("Invalid token.", calculate.calculate("E%5"));
    }
    @Test
    //边界值分析
    public void calculateTestBoundaryValue() {
        Calculate calculate = new Calculate();
        //加
        assertEquals(Double.valueOf(10.5+10), Double.valueOf(calculate.calculate("10.5+10")));
        assertEquals(Double.valueOf(0+10.5), Double.valueOf(calculate.calculate("0+10.5")));
        assertEquals(Double.valueOf(70), Double.valueOf(calculate.calculate("70+0")));
        assertEquals(Double.valueOf(-75+10), Double.valueOf(calculate.calculate("-75+10")));
        assertEquals(Double.valueOf(100+-10), Double.valueOf(calculate.calculate("100+-10")));
        assertEquals(Double.valueOf(101+-10.5), Double.valueOf(calculate.calculate("101+-10.5")));

        //减
        assertEquals(Double.valueOf(10.5-10), Double.valueOf(calculate.calculate("10.5-10")));
        assertEquals(Double.valueOf(0-10.5), Double.valueOf(calculate.calculate("0-10.5")));
        assertEquals(Double.valueOf(70), Double.valueOf(calculate.calculate("70-0")));
        assertEquals(Double.valueOf(-75-10), Double.valueOf(calculate.calculate("-75-10")));
        assertEquals(Double.valueOf(100-(-10)), Double.valueOf(calculate.calculate("100--10")));
        assertEquals(Double.valueOf(101-(-10.5)), Double.valueOf(calculate.calculate("101--10.5")));

        //乘
        assertEquals(Double.valueOf(10.5*10), Double.valueOf(calculate.calculate("10.5*10")));
        assertEquals(Double.valueOf(0*10.5), Double.valueOf(calculate.calculate("0*10.5")));
        assertEquals(Double.valueOf(0), Double.valueOf(calculate.calculate("70*0")));
        assertEquals(Double.valueOf(-75*10), Double.valueOf(calculate.calculate("-75*10")));
        assertEquals(Double.valueOf(100*-10), Double.valueOf(calculate.calculate("100*-10")));
        assertEquals(Double.valueOf(101*-10.5), Double.valueOf(calculate.calculate("101*-10.5")));

        //除
        assertEquals(Double.valueOf(10.5/10), Double.valueOf(calculate.calculate("10.5/10")));
        assertEquals(Double.valueOf(0/10.5), Double.valueOf(calculate.calculate("0/10.5")));
        assertEquals("Divided by zero", calculate.calculate("70/0"));
        assertEquals(Double.valueOf(-75.0/10.0), Double.valueOf(calculate.calculate("-75/10")));
        assertEquals(Double.valueOf(100/-10), Double.valueOf(calculate.calculate("100/-10")));
        assertEquals(Double.valueOf(101/-10.5), Double.valueOf(calculate.calculate("101/-10.5")));

        //指数
        assertEquals(Double.valueOf(Math.pow(10.5, 10)), Double.valueOf(calculate.calculate("10.5^10")));
        assertEquals(Double.valueOf(Math.pow(0, 10.5)), Double.valueOf(calculate.calculate("0^10.5")));
        assertEquals(Double.valueOf(Math.pow(70, 0)), Double.valueOf(calculate.calculate("70^0")));
        assertEquals(Double.valueOf(Math.pow(-75, 10)), Double.valueOf(calculate.calculate("(-75)^10")));
        assertEquals(Double.valueOf(Math.pow(100, -10)), Double.valueOf(calculate.calculate("100^(-10)")));
        assertEquals(Double.valueOf(Math.pow(101, -10.5)), Double.valueOf(calculate.calculate("101^(-10.5)")));

        //阶乘
        assertEquals("Factorial operation should involve a positive integer", calculate.calculate("10.5!"));
        assertEquals(Double.valueOf(1), Double.valueOf(calculate.calculate("0!")));
        assertEquals(Double.valueOf(10*9*8*7*6*5*4*3*2), Double.valueOf(calculate.calculate("10!")));
        assertEquals("Factorial operation should involve a positive integer", calculate.calculate("(-10)!"));


        //取余
        assertEquals("Mod operation should involve two integers", calculate.calculate("10.5%10"));
        assertEquals("Mod operation should involve two integers", calculate.calculate("0%10.5"));
        assertEquals("Mod by zero!", calculate.calculate("70%0"));
        assertEquals(Double.valueOf(-5), Double.valueOf(calculate.calculate("-75%10")));
        assertEquals(Double.valueOf(0), Double.valueOf(calculate.calculate("100%-10")));
        assertEquals(Double.valueOf(0), Double.valueOf(calculate.calculate("0%-7")));
    }

    private boolean assertNear(double a, double b, double precision){
        return Math.abs(a-b) <= precision;
    }

    @Test
    //复杂表示式
    public void calculateTestComplex() {
        Calculate calculate = new Calculate();
        assertEquals(Double.valueOf(10), Double.valueOf(calculate.calculate("3+++4--3")));
        assertEquals("Divided by zero", calculate.calculate("3*(2+3)/0"));
        assertEquals(Double.valueOf(67), Double.valueOf(calculate.calculate("3+4^2*4")));
        assertEquals(Double.valueOf(2), Double.valueOf(calculate.calculate("4/2^2*2")));
        assertEquals(Double.valueOf(1), Double.valueOf(calculate.calculate("36/3!^2")));
        assertEquals(Double.valueOf(-53), Double.valueOf(calculate.calculate("-10^2/2+-3")));
        assertTrue(assertNear(1.28, Double.valueOf(calculate.calculate("1.6^2/2!")), 0.001));
        assertEquals(Double.valueOf(56), Double.valueOf(calculate.calculate("1-2*(30+(-40.0/5)*(19-4!/2))-(-4*3)/2^(8-3*2)")));
        assertEquals(Double.valueOf(5), Double.valueOf(calculate.calculate("2+3M")));
        assertEquals(Double.valueOf(25), Double.valueOf(calculate.calculate("R*5")));
        assertEquals(Double.valueOf(0), Double.valueOf(calculate.calculate("3-3M")));
        assertEquals("Mod by zero!", calculate.calculate("9%R"));
    }

    @Test
    //无效输入
    public void calculateTestInvalidInput() {
        Calculate calculate = new Calculate();
        assertEquals("Invalid input", calculate.calculate("3/"));
        assertEquals("Invalid input", calculate.calculate("**5"));
        assertEquals("Invalid input", calculate.calculate("5*/7"));
        assertEquals("More than one dot found in a number", calculate.calculate("1...3*5"));
        assertEquals("No input", calculate.calculate(""));
        assertEquals("No saved value", calculate.calculate("R+1"));
        assertEquals("Invalid parentheses", calculate.calculate("5*(6+(3-1)"));
        assertEquals("Invalid input", calculate.calculate("!7"));
    }
}