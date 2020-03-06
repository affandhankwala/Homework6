import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyMiniSearchEngineTest {
    private List<String> documents() {
        return new ArrayList<String>(
                Arrays.asList(
                        "hello world",
                        "hello",
                        "world",
                        "world world hello",
                        "seattle rains hello abc world",
                        "sunday hello world fun"));
    }

    @Test
    public void testOneWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());
        List<Integer> result = engine.search("seattle");

        assertEquals(1, result.size());

        assertEquals(Integer.valueOf(4), result.get(0));
    }

    @Test
    public void testTwoWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());
        List<Integer> result = engine.search("hello world");

        assertEquals(2, result.size());

        assertEquals(List.of(0, 5), result);
    }

    @Test
    public void testThreeWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        String[] inputs = {
                "rains hello abc",
                "rai23ns Hello abc",
        };

        for (String input : inputs) {
            List<Integer> result = engine.search(input);
            assertEquals(1, result.size());
            assertEquals(List.of(4), result);
        }
    }

    @Test
    public void testFourWord() {

        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        String[] inputs = {
                "sunday hello world fun",
                "sun3day he23llo wor523ld 12fun",
        };

        for (String input : inputs) {
            List<Integer> result = engine.search(input);
            assertEquals(1, result.size());
            assertEquals(List.of(5), result);
        }
    }


    @Test
    public void testWordNotFound() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());
        List<Integer> result = engine.search("amazing");
        assertEquals(0, result.size());
        assertEquals(List.of(), result);

    }

    @Test
    public void fullTest() {
        //documents: empty, search: empty
        MyMiniSearchEngine engine1= new MyMiniSearchEngine(new ArrayList<>());
        List<Integer> result1 = engine1.search(new String(""));
        assertEquals(0, result1.size());
        assertEquals(List.of(), result1);

        //documents: empty, search:  a word
        String test2[] = {"hello"};
        List<Integer> result2 = engine1.search(test2[0]);
        assertEquals(0, result2.size());
        assertEquals(List.of(), result2);

        //documents : multiple entries with some caps and non alpha chars

        MyMiniSearchEngine engine2 = new MyMiniSearchEngine(new ArrayList<String> (Arrays.asList(
                "hello this is my world",
                "My world is big",
                "hello in 35different ways",
                "Seattle Has COLD wEAther",
                "counter",
                "COUNTER",
                "c1o2u3n4t5e6r7",
                "c~o~u~N~t~e~r",
                "This is a very long sentence that has lot of words but should pass"

        )));
        //search: empty
        List<Integer> result3 = engine2.search("");
        assertEquals(0, result3.size());
        assertEquals(List.of(), result3);

        //search: one word - lowercase
        List<Integer> result4 = engine2.search("my");
        assertEquals(2, result4.size());
        assertEquals(List.of(0,1), result4);

        //search: one word - mixed case and non alpha
        List<Integer> result5 = engine2.search("Co23ld");
        assertEquals(1, result5.size());
        assertEquals(List.of(3), result5);

        List<Integer> result5_2 = engine2.search("CoUnTeR");
        assertEquals(4, result5_2.size());
        assertEquals(List.of(4, 5, 6, 7), result5_2);

        //search: different phrases mixed cases and some non alpha
        List<Integer> result6 = engine2.search("my world");
        assertEquals(2, result6.size());
        assertEquals(List.of(0,1), result6);

        List<Integer> result7 = engine2.search("in differe35nt wAy8s");
        assertEquals(1, result7.size());
        assertEquals(List.of(2), result7);

        List<Integer> result8 = engine2.search("Seattle has cold weather");
        assertEquals(1, result8.size());
        assertEquals(List.of(3), result8);

        List<Integer> result9 = engine2.search("This is a very long sentence that has lot of words but should pass");
        assertEquals(1, result9.size());
        assertEquals(List.of(8), result9);

    }
}