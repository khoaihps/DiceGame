
import referee.Referee;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("DICE GAME!");
        Referee referee = new Referee();
        referee.setupGame();
        referee.playGame();
    }
}
