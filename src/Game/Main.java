package Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("John"); Player player2 = new Player("Tony");

        player1.fillPlayerField();  player2.fillPlayerField();
        while (true){
            player1.makeTurn(player2.getBattlefield());
            if (player1.isWin()==true){break;}
            player2.makeTurn(player1.getBattlefield());
            if (player2.isWin()==true){break;}
        }
    }
}

