package Calculate;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CalculatorUnitTest {

    private Calculate calculator;
    private Class c;

    @Before
    public void init() {
        calculator = new Calculate();
        c = calculator.getClass();
    }

    @Test
    public void testPower() {
        try {
            Method method = c.getDeclaredMethod("power", new Class[]{double.class, double.class});
            method.setAccessible(true);
            Object result;

            // two positive
            result = method.invoke(calculator, 2, 3);
            assertEquals(8.0, result);


            // negative and positive
            result = method.invoke(calculator, -2, 3);
            assertEquals(-8.0, result);


            // invalid
            result = method.invoke(calculator, -2, 0.5);
            assertEquals(0.0, result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindNumber() {
        try {
            Method method = c.getDeclaredMethod("find_number", new Class[]{String.class, int.class});
            method.setAccessible(true);
            Object result;

            // simple number
            result = method.invoke(calculator, "1+2", 0);
            assertEquals(1, result);


            // long number
            result = method.invoke(calculator, "(12333+3.0)", 1);
            assertEquals(6, result);

            // invalid number
            try {
                result = method.invoke(calculator, "(12..333+3.0)", 1);
                fail();
            } catch (Exception ex){
                assert true;
            }

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    public void testDealWithNegative() {
        try {
            Method method = c.getDeclaredMethod("deal_with_negative", String.class);
            method.setAccessible(true);
            Object result;

            // nothing to change
            result = method.invoke(calculator, "1+2");
            assertEquals("1+2", result);

            // negative
            result = method.invoke(calculator, "22*-3333");
            assertEquals("22*(-3333)", result);

            // positive
            result = method.invoke(calculator, "22*+3333");
            assertEquals("22*(+3333)", result);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMod() {
        try {
            Method method = c.getDeclaredMethod("mod", double.class, double.class);
            method.setAccessible(true);
            Object result;

            // normal
            result = method.invoke(calculator,  2, 5);
            assertEquals(2.0, result);

            // invalid number
            try {
                result = method.invoke(calculator, 3.3, 1);
                fail();
            } catch (Exception ex){
                assert true;
            }

            // divided by zero
            try {
                result = method.invoke(calculator, 3, 0);
                fail();
            } catch (Exception ex){
                assert true;
            }

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }


    @Test
    public void testDelSpace() {
        try {
            Method method = c.getDeclaredMethod("del_space", String.class);
            method.setAccessible(true);
            Object result;

            // space only
            result = method.invoke(calculator,  "123 +  333");
            assertEquals("123+333", result);

            // tab only
            result = method.invoke(calculator,  "123    *  333");
            assertEquals("123*333", result);

            // combined
            result = method.invoke(calculator,  "123333      *  333");
            assertEquals("123333*333", result);

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    public void testFactorial() {
        try {
            Method method = c.getDeclaredMethod("factorial", double.class);
            method.setAccessible(true);
            Object result;

            // normal
            result = method.invoke(calculator,  5);
            assertEquals(120.0, result);

            // normal
            result = method.invoke(calculator,  0);
            assertEquals(1.0, result);

            // invalid number
            try {
                result = method.invoke(calculator, 3.3);
                fail();
            } catch (Exception ex){
                assert true;
            }

            // invalid number
            try {
                result = method.invoke(calculator, -3);
                fail();
            } catch (Exception ex){
                assert true;
            }

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }


    @Test
    public void testGetToken() {
        try {
            Method method = c.getDeclaredMethod("get_token", String.class, char.class);
            method.setAccessible(true);
            Object result;

            // +/-
            result = method.invoke(calculator,  "(1+2)-3", '+');
            assertEquals(-5.0, result);
            result = method.invoke(calculator,  "1*222+3", '+');
            assertEquals(5.0, result);

            // * and /
            result = method.invoke(calculator,  "1*222+3", '*');
            assertEquals(1.0, result);

            result = method.invoke(calculator,  "11.3/222+3!", '*');
            assertEquals(-4.0, result);

            result = method.invoke(calculator,  "(1123*222+3)%4", '*');
            assertEquals(12.1, result);

            // !/^
            result = method.invoke(calculator,  "1234!+3", '!');
            assertEquals(4.0, result);
            result = method.invoke(calculator,  "(1234!-13)+3^2", '^');
            assertEquals(12.0, result);


            // not found
            result = method.invoke(calculator,  "1234!+3", '*');
            assertEquals(-0.1, result);

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    public void testStrToNum() {
        try {
            Method method = c.getDeclaredMethod("str_to_num", String.class);
            method.setAccessible(true);
            Object result;

            // +/-
            result = method.invoke(calculator,  "123");
            assertEquals(123.0, result);
            result = method.invoke(calculator,  "-123.9");
            assertEquals(-123.9, result);
            result = method.invoke(calculator,  "error");
            assertEquals(0.0, result);

            // invalid number
            try {
                result = method.invoke(calculator, "");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "asdasda");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "123...33");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "123+3");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "R");
                fail();
            } catch (Exception ex) {
                assert true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testVeryPrimary() {
        try {
            Method method = c.getDeclaredMethod("very_primary", String.class);
            method.setAccessible(true);
            Object result;

            // normal
            result = method.invoke(calculator,  "123");
            assertEquals(123.0, result);
            result = method.invoke(calculator,  "-123.9");
            assertEquals(-123.9, result);
            result = method.invoke(calculator,  "(2333.3)");
            assertEquals(2333.3, result);

            // invalid number
            try {
                result = method.invoke(calculator, "");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "asdasda");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "123...33");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "123+3");
                fail();
            } catch (Exception ex){
                assert true;
            }

            try {
                result = method.invoke(calculator, "R");
                fail();
            } catch (Exception ex) {
                assert true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}