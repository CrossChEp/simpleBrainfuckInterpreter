package src;

import java.time.Duration;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        LocalTime startTime = LocalTime.now();
        LineBrainFuck bl = new LineBrainFuck(",+[-.,+]");
        System.out.println(bl.process("Codewars"));
        LocalTime endTime = LocalTime.now();
        System.out.println(Duration.between(startTime, endTime).getNano() / 1000000 + " ms");
    }
}
