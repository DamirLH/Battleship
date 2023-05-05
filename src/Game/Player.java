package Game;

import java.util.Scanner;

class Player{
    static final int FIELD_LENGTH=10;
    static final String FIRE = "\uD83D\uDD25";
    static final String WAVE = "\uD83C\uDF0A";
    static final String SHIP = "\uD83D\uDEA2";

    private String name;
    private int[][] battlefield;
    private int[][] monitor;
    private boolean win;

    public Player(String name){
        this.name=name; this.battlefield=createField(); this.monitor=createField(); this.win=false;
    }

    public void makeTurn(int[][] enemyField){
        while (true){
            System.out.println(name+", сделайте Ваш ход");
            showMonitor();
            System.out.println("Укажите координату ОХ:");
            int x = new Scanner(System.in).nextInt();
            System.out.println("Укажите координату ОY:");
            int y = new Scanner(System.in).nextInt();
            if (enemyField[y][x]==0){
                System.out.println("Вы промахнулись");
                monitor[y][x]=3; break; // 0 wave **||1 ship ||2 fire ||3 .
            }else if (enemyField[y][x]==1){
                enemyField[y][x]=2; monitor[y][x]=2; // 0 wave ||1 ship ** ||2 fire ||3 .
                if (winChecker()){
                    this.win=true;
                    break;
                }
                if (!this.win)System.out.println("Вы попали! Сделайте ход ещё раз!");
            }else if (enemyField[y][x]==2){ // 2 fire
                System.out.println("Вы уже попадали по этой палубе");
                monitor[y][x]=2; break;
            } else if (enemyField[y][x]==3){
                System.out.println("Вы сюда уже стреляли. Тут нет корабля");
                monitor[y][x]=3; break;
            }
        }
    }

    public static boolean exceptionChecker(int x, int y, int deck, int rotation, int[][] battlefield){

        if (rotation==1){
           if (x+deck>FIELD_LENGTH){return false;}
        }
        else if (rotation == 2) {
           if (y+deck>FIELD_LENGTH){return false;}
        }
        while (deck!=0){
            for (int i = 0; i < deck; i++) {
                int tempX = 0, tempY = 0;
                if (rotation == 1) {
                    tempX = i;
                } else {
                    tempY = i;
                }
                if (x + tempX + 1 < FIELD_LENGTH && x + tempX + 1 > 0) {
                    if (battlefield[x + 1 + tempX][y + tempY] != 0) {//1=SHIP
                        return false;
                    }
                }
                if (x + tempX - 1 < FIELD_LENGTH && x + tempX - 1 > 0) {
                    if (battlefield[x - 1 + tempX][y + tempY] != 0) {//1=SHIP
                        return false;
                    }
                }
                if (y + tempY + 1 < FIELD_LENGTH && y + tempY + 1 > 0) {
                    if (battlefield[x + tempX][y + 1 + tempY] != 0) {//1=SHIP
                        return false;
                    }
                }
                if (y - tempY - 1 < FIELD_LENGTH && y + tempX - 1 > 0) {
                    if (battlefield[x + tempX][y - 1 + tempY] != 0) {//1=SHIP
                        return false;
                    }
                }
            }
            deck--;
        }
        return true;

    }

    public boolean winChecker(){
        int counter=0;
        for (int i = 0; i <this.getMonitor().length ; i++) {
            for (int j = 0; j <this.getMonitor()[i].length ; j++) {
                if (this.getMonitor()[i][j]==2){
                    counter++;
                }
            }
        }
        if (counter==10){
            System.out.println(this.name+" ,поздравляем с победой!!!\nИгра окончена "); return true;
        }
        else return false;
    }

    public void fillPlayerField(){
        int deck=4;
        System.out.println(name+", заполните Ваше поле");
        while (deck>=1){
            System.out.println("Ваше поле: ");
            showField();
            System.out.println("Укажите координаты по оси OX для "+deck+" палубного корабля");
            int x = new Scanner(System.in).nextInt(); // из-за инверсии матрицы, Х будет: [Y][X]
            System.out.println("Укажите координаты по оси OY для "+deck+" палубного корабля");
            int y = new Scanner(System.in).nextInt(); // так-же из-за инверсии [Y][X]
            System.out.println("Выьерите направление коробля. 1-горизонтально, 2-вертикально");
            int ans = new Scanner(System.in).nextInt();
            if (!exceptionChecker(x,y,deck,ans,this.battlefield)){
                System.out.println("Возникла ошибка расстановки");
                continue;
            }
            for (int i = 0; i <deck ; i++) {
                if (ans==1){
                    battlefield[y][x+i]=1;
                } else if (ans==2){
                    battlefield[y+i][x]=1;
                }
            } deck--;
        }
    }

    public void drawField(int[][] field){
        System.out.print(" ");
        for (int i = 0; i <field.length ; i++) {
            System.out.print(" "+i);
        }
        System.out.println();
        for (int i = 0; i <field.length ; i++) {
            System.out.print(i+" ");
            for (int j = 0; j <field[i].length ; j++) {
                if (field[i][j]==0){
                    System.out.print(WAVE);
                } else if (field[i][j]==1){
                    System.out.print(SHIP);
                } else if (field[i][j]==2){
                    System.out.print(FIRE);
                } else if (field[i][j]==3){
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
    public int[][] createField(){
        int[][] newField = new int[FIELD_LENGTH][FIELD_LENGTH]; // создаем новое поле
        for (int i = 0; i <FIELD_LENGTH ; i++) {
            for (int j = 0; j <FIELD_LENGTH ; j++) {
                newField[i][j]=0; // выставляем все поля под 0, что значит WAVE
            }
        }
        return newField;
    }
    public void showField(){
        drawField(battlefield);
    }
    public void showMonitor(){
        drawField(monitor);
    }
    public int[][] getBattlefield() {
        return battlefield;
    }

    public boolean isWin() {
        return win;
    }

    public int[][] getMonitor() {
        return monitor;
    }
}
