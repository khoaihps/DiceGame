package dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {
    private int id;

    private static final int[] FACES = {1, 2, 3, 4, 5, 6 };
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int roll() {
        Random random = new Random();
        List<Integer> weight = new ArrayList<>();

        for (int i = 0; i < FACES.length; i++)
            for (int j = 1; j <= 16; j++) {
                weight.add(FACES[i]);
            }

        for (int i = 0; i < 4; i++) {
            weight.add(FACES[id - 1]);
        }

        int randomIndex = random.nextInt(weight.size());
        return weight.get(randomIndex);
    }
}
