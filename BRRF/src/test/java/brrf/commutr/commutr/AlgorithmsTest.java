package brrf.commutr.commutr;

import org.junit.jupiter.api.Test;

import java.util.*;

import static brrf.Algorithms.comfortRoute;
import static brrf.Algorithms.directRoute;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsTest {

    @Test
    public void directRouteTest() {
        List<List<Map<String, Object>>> katowiceRaciborz = new ArrayList();

        Map<String, Object> expectedKatowice = new LinkedHashMap<>();
        expectedKatowice.put("arr_time", "17:54:00");
        expectedKatowice.put("dept_time", "18:01:00");
        expectedKatowice.put("name", "PILECKI (1423)");
        expectedKatowice.put("carrier", "IC");
        expectedKatowice.put("platform", "2");
        expectedKatowice.put("track", "1");
        expectedKatowice.put("kilometers", "324.4");

        Map<String, Object> expectedRaciborz = new LinkedHashMap<>();
        expectedRaciborz.put("arr_time", "19:27:00");
        expectedRaciborz.put("dept_time", "null");
        expectedRaciborz.put("name", "PILECKI (1423)");
        expectedRaciborz.put("carrier", "IC");
        expectedRaciborz.put("platform", "2");
        expectedRaciborz.put("track", "1");
        expectedRaciborz.put("kilometers", "420.4");


        List<Map<String, Object>> katowice = List.of(expectedKatowice);
        List<Map<String, Object>> raciborz = List.of(expectedRaciborz);

        katowiceRaciborz.add(katowice);
        katowiceRaciborz.add(raciborz);

        assertEquals(katowiceRaciborz.toString(), directRoute("katowice", "raciborz", "17:00:00", "21:00:00").toString());



        List<List<Map<String, Object>>> emptyArray = new ArrayList();
        List<Map<String, Object>> emptylist1 = List.of();
        List<Map<String, Object>> emptylist2 = List.of();
        emptyArray.add(emptylist1);
        emptyArray.add(emptylist2);

        assertEquals(emptyArray.toString(), directRoute("krakow_glowny", "bielsko_biala_glowna", "10:00:00", "10:05:00").toString());

        assertEquals(emptyArray.toString(), directRoute("krakow_podgorze", "oswiecim", "10:00:00", "09:55:00").toString());


        List<List<Map<String, Object>>> kedziezynKozleGliwice = new ArrayList();

        Map<String, Object> expectedKedziezynKozle1 = new LinkedHashMap<>();
        expectedKedziezynKozle1.put("arr_time", "null");
        expectedKedziezynKozle1.put("dept_time", "11:57:00");
        expectedKedziezynKozle1.put("name", "94922");
        expectedKedziezynKozle1.put("carrier", "PR");
        expectedKedziezynKozle1.put("platform", "3");
        expectedKedziezynKozle1.put("track", "4");
        expectedKedziezynKozle1.put("kilometers", "0.0");

        Map<String, Object> expectedKedziezynKozle2 = new LinkedHashMap<>();
        expectedKedziezynKozle2.put("arr_time", "12:14:00");
        expectedKedziezynKozle2.put("dept_time", "12:15:00");
        expectedKedziezynKozle2.put("name", "KORMORAN (65100)");
        expectedKedziezynKozle2.put("carrier", "TLK");
        expectedKedziezynKozle2.put("platform", "3");
        expectedKedziezynKozle2.put("track", "4");
        expectedKedziezynKozle2.put("kilometers", "148.9");

        Map<String, Object> expectedGliwice1 = new LinkedHashMap<>();
        expectedGliwice1.put("arr_time", "12:32:00");
        expectedGliwice1.put("dept_time", "null");
        expectedGliwice1.put("name", "94922");
        expectedGliwice1.put("carrier", "PR");
        expectedGliwice1.put("platform", "3");
        expectedGliwice1.put("track", "7");
        expectedGliwice1.put("kilometers", "37.1");

        Map<String, Object> expectedGliwice2 = new LinkedHashMap<>();
        expectedGliwice2.put("arr_time", "12:44:00");
        expectedGliwice2.put("dept_time", "12:45:00");
        expectedGliwice2.put("name", "KORMORAN (65100)");
        expectedGliwice2.put("carrier", "TLK");
        expectedGliwice2.put("platform", "2");
        expectedGliwice2.put("track", "5");
        expectedGliwice2.put("kilometers", "186.0");

        List<Map<String, Object>> kedzierzynKozle = List.of(expectedKedziezynKozle1, expectedKedziezynKozle2);
        List<Map<String, Object>> gliwice = List.of(expectedGliwice1, expectedGliwice2);

        kedziezynKozleGliwice.add(kedzierzynKozle);
        kedziezynKozleGliwice.add(gliwice);

        assertEquals(kedziezynKozleGliwice.toString(), directRoute("kedzierzyn_kozle", "gliwice", "10:00:00", "14:55:00").toString());
    }


    @Test
    public void comfortRouteTest() {
        List<List<Map<String, Object>>> emptyArray = new ArrayList();
        List<Map<String, Object>> emptylist1 = List.of();
        List<Map<String, Object>> emptylist2 = List.of();
        emptyArray.add(emptylist1);
        emptyArray.add(emptylist2);

        List<List<Map<String, Object>>> oneOk = new ArrayList();
        List<List<Map<String, Object>>> oneNotOk = new ArrayList();
        List<List<Map<String, Object>>> input = new ArrayList();
        List<List<Map<String, Object>>> output = new ArrayList();

        Map<String, Object> start1 = new LinkedHashMap<>();
        start1.put("arr_time", "17:54:00");
        start1.put("dept_time", "18:01:00");
        start1.put("name", "PILECKI (1423)");
        start1.put("carrier", "IC");
        start1.put("platform", "2");
        start1.put("track", "1");
        start1.put("kilometers", "324.4");

        Map<String, Object> start2 = new LinkedHashMap<>();
        start2.put("arr_time", "null");
        start2.put("dept_time", "11:57:00");
        start2.put("name", "94922");
        start2.put("carrier", "PR");
        start2.put("platform", "3");
        start2.put("track", "4");
        start2.put("kilometers", "0.0");

        Map<String, Object> start3 = new LinkedHashMap<>();
        start3.put("arr_time", "12:14:00");
        start3.put("dept_time", "12:15:00");
        start3.put("name", "KORMORAN (65100)");
        start3.put("carrier", "TLK");
        start3.put("platform", "3");
        start3.put("track", "4");
        start3.put("kilometers", "148.9");

        Map<String, Object> end1 = new LinkedHashMap<>();
        end1.put("arr_time", "19:27:00");
        end1.put("dept_time", "null");
        end1.put("name", "PILECKI (1423)");
        end1.put("carrier", "IC");
        end1.put("platform", "2");
        end1.put("track", "1");
        end1.put("kilometers", "420.4");

        Map<String, Object> end2 = new LinkedHashMap<>();
        end2.put("arr_time", "12:32:00");
        end2.put("dept_time", "null");
        end2.put("name", "94922");
        end2.put("carrier", "PR");
        end2.put("platform", "3");
        end2.put("track", "7");
        end2.put("kilometers", "37.1");

        Map<String, Object> end3 = new LinkedHashMap<>();
        end3.put("arr_time", "12:44:00");
        end3.put("dept_time", "12:45:00");
        end3.put("name", "KORMORAN (65100)");
        end3.put("carrier", "TLK");
        end3.put("platform", "2");
        end3.put("track", "5");
        end3.put("kilometers", "186.0");

        List<Map<String, Object>> inputList1 = List.of(start1, start2, start3);
        List<Map<String, Object>> inputList2 = List.of(end1, end2, end3);
        List<Map<String, Object>> outputList1 = List.of(start1, start3);
        List<Map<String, Object>> outputList2 = List.of(end1, end3);
        List<Map<String, Object>> Listok1 = List.of(start1);
        List<Map<String, Object>> Listok2 = List.of(end1);
        List<Map<String, Object>> ListNotOk1 = List.of(start2);
        List<Map<String, Object>> ListNotOk2 = List.of(end2);

        input.add(inputList1);
        input.add(inputList2);
        output.add(outputList1);
        output.add(outputList2);
        oneOk.add(Listok1);
        oneOk.add(Listok2);
        oneNotOk.add(ListNotOk1);
        oneNotOk.add(ListNotOk2);


        assertEquals(output.toString(), comfortRoute(input).toString());
        assertEquals(emptyArray.toString(), comfortRoute(emptyArray).toString());
        assertEquals(emptyArray.toString(), comfortRoute(oneNotOk).toString());
        assertEquals(oneOk.toString(), comfortRoute(oneOk).toString());
    }

}