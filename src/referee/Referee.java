package referee;

import player.Player;
import player.VirtualPlayer;
import dice.Dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Referee {
    private List<Player> players;
    private Dice dice;
    private Scanner scanner;

    public Referee() {
        this.players = new ArrayList<>();
        this.dice = new Dice();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setupGame() {
        scanner = new Scanner(System.in);
        int numberOfPlayers;

        while (true) {
            try {
                System.out.print("Nhập số người chơi (0-4): ");
                numberOfPlayers = Integer.parseInt(scanner.nextLine());

                if (numberOfPlayers < 0 || numberOfPlayers > 4) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Số người chơi không hợp lệ.");
            }
        }

        // Nhập thông tin người chơi
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Nhập tên người chơi " + (i+1) + ": ");
            addPlayer(new Player(scanner.nextLine()));
        }

        // Thêm người chơi ảo nếu cần
        for (int i = 1; i <= 4 - numberOfPlayers; i++) {
            String virtualPlayerName = "Người chơi ảo " + Integer.toString(i);
            VirtualPlayer virtualPlayer = new VirtualPlayer(virtualPlayerName);
            virtualPlayer.setFailureExpression(i - 1);
            addPlayer(virtualPlayer);
            System.out.println(virtualPlayerName + " được thêm vào.");
        }
        // scanner.close();
    }

    public void playGame() {
        while (true) {
            int playerId = 0;
            while (true) {
                try {
                    System.out.print("\nTrọng tài chỉ đinh người chơi (1-4): ");
                    playerId = Integer.parseInt(scanner.nextLine());

                    if (playerId < 1 || playerId > 4) {
                        throw new IllegalArgumentException();
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Chỉ định người không hợp lệ. Vui lòng chỉ định lại.");
                }
            }

            Player player = players.get(playerId - 1);

            // Set random dice
            Random random = new Random();
            int randomDice = random.nextInt(4) + 1;
            dice.setId(randomDice);
            System.out.println("\nNgười chơi " + player.getName() + " nhận xúc xắc " + randomDice + ".");

            // Nếu là bot sẽ delay 500ms
            if (player instanceof VirtualPlayer) {
                try {
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Nếu là người chơi thật tương tác để gieo
            if (!(player instanceof VirtualPlayer)) {
                System.out.print("\nEnter để GIEO...");
                scanner.nextLine();
            }

            // Roll dice
            int diceValue = dice.roll();
            System.out.println("\nNgười chơi " + player.getName() + " gieo được " + diceValue + " điểm.");
            System.out.println("Điểm cũ cộng điểm mới: " + player.getScore() + " + " + diceValue + " = " + (player.getScore() + diceValue));

            // Update player's score
            if (player.getScore() + diceValue == 21) {
                player.setScore(21);
                scanner.close();
                announceWinner(player);
                return;
            } else if (player.getScore() + diceValue > 21) {
                System.out.println("Tổng điểm người chơi " + player.getName() + " đặt về 0.");
                player.setScore(0);
            } else {
                player.setScore(player.getScore() + diceValue);
            }

        }
    }

    private void announceWinner(Player winner) {
        System.out.println("--------------------------------------------");
        System.out.println("Người thắng cuộc: " + winner.getName());
        for (Player player : players) {
            if (player != winner && player instanceof VirtualPlayer) {
                VirtualPlayer virtualPlayer = (VirtualPlayer) player;
                System.out.println(virtualPlayer.getName() + " biểu cảm: " + virtualPlayer.getFailureExpression());
            }
        }
    }

    public static void main(String[] args) {
        Referee referee = new Referee();
        referee.setupGame();
        referee.playGame();
    }
}
