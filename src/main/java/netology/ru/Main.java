package netology.ru;

import java.util.*;

public class Main {
    public static final int threadsQt = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        String[] routs = new String[threadsQt];
        for (int i = 0; i < threadsQt; i++) {
            routs[i] = generateRoute("RLRFR", 100);
        }
        for (String text : routs) {
            Runnable logic = () -> {
                int countR = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'R') {
                        countR++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countR)) {
                        sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                    } else {
                        sizeToFreq.put(countR, 1);
                    }
                }
            };
            Thread thread = new Thread(logic);
            thread.start();
        }
        int maxValue = 0; //максимальное количество повторений
        int freqKey = 0; //ключ с максимальным количеством повторений

        //ищем максимальное количество повторений
        for (Map.Entry<Integer, Integer> entry: sizeToFreq.entrySet()){
            int value = entry.getValue();
            if (value > maxValue) {
                maxValue = value;
                freqKey = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений: " + freqKey + " (встретилось " + maxValue + " раз(а)).");
        int finalFreqKey = freqKey;
        System.out.println("Другие значения:");
        sizeToFreq.forEach((key, value) -> {
            if (key != finalFreqKey) {
                System.out.println(" - " + key + " (" + value + " раз(а))");
            }
        });
        System.out.println("Количество уникальных повторений: " + sizeToFreq.size());
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}