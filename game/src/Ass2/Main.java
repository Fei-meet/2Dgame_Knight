package Ass2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


//      -----------------------------------Main.java----------------------------------------
//      |                  This is Assignment2 By group 8                                  |
//      |              Group member: Kobe Liu (19029765)                                   |
//      |                            Jane Liu（20008036)                                   |
//      |                            Alex Liu (20008038)     *Leader                       |
//      |                                                                                  |
//      |          This is a 2D game and you will control a KNIGHT to kill the monster!    |
//      |                W                                                                 |
//      |              A S D                                   F                           |
//      |           control movement                     pick up items                     |
//      |                                                                                  |
//      |         "Main.java"Responsible for the main functions of the game                |
//      |    You can get more information in Help(in the game) and Document(a PDF in zip)  |
//      |                                                                                  |
//      |                             HAVE A GOOD GAME!                                    |
//      |                                                                                  |
//       ------------------------------------------------------------------------------------


public class Main extends GameEngine {
    int bullet_maxnumber = 2;
    int weapon_type = 5;
    int weapon_num;
    public static void main() {
        Main game = new Main();
        createGame(game, 45);
    }

// The parameters we use:
    Image knight;
    Image knightImage;
    Image knightLeft;
    Image knightRight;
    Image knightUp;
    Image knightDown;
    Image items;
    Image item;
    Image HpMedic;
    Image EhpMedic;

    Image borad;
    Image EHP2 ;
    Image HP2 ;

    Image[]knight_walk_down_Images = new Image[4];
    Image[]knight_walk_left_Images = new Image[4];
    Image[]knight_walk_right_Images = new Image[4];
    Image[]knight_walk_up_Images = new Image[4];
    Image[]Weapon_type = new Image[weapon_type-1];

    double knightPositionX;
    double knightPositionY;
    double itemX ;
    double itemY ;
    double itemVelocityX;
    double itemVelocityY;
    double itemAngle;

    boolean itemGet = false;
    boolean itemActive = false;
    boolean discover_HpMedic;
    boolean discover_EhpMedic;
    boolean discover_Bow;
    boolean discover_Axe;
    boolean aroundweapon = false;
    boolean left, right, up, down, stop;
    boolean gameOver;

    int score = 0;
    double weaponX;
    double weaponY;
    boolean weaponGet = false;

    int a = 0;
    int b = 0;
    int state_index;
    int direction;
    int HP;
    int Max_HP;
    int axe_Size; //This is also the bullet size
    int knightSpeed;
    int Random_Dice = 999;
    int Random_quality = 999;
    String quality ;

    AudioClip backgroundMusic;
    AudioClip GetAttacked;
    AudioClip monsterDead;

    //init the knight
    public void initKnight(){
        knightImage=subImage(knight,0,0,32,48);
        knightLeft=subImage(knight,0,48,32,48);
        knightRight=subImage(knight,0,96,32,48);
        knightUp=subImage(knight,0,144,32,48);
        knightDown=subImage(knight,32,0,32,48);

        //AXE
        Weapon_type[0] = subImage(items, 0, 20, 20, 20);
        //BOW
        Weapon_type[1] = subImage(items, 119, 21, 22, 23);
        Weapon_type[2] = subImage(items, 40, 20, 20, 20);
        Weapon_type[3] = subImage(items, 60, 20, 20, 20);

        int d = 0;
        for(int y = 0; y < 48; y += 48) {
            for(int x = 0; x < 128; x += 32) {
                knight_walk_down_Images[d] = subImage(knight, x, y, 32, 48);
                d++;
            }
        }

        int l = 0;
        for(int y = 48; y < 96; y += 48) {
            for(int x = 0; x < 128; x += 32) {
                knight_walk_left_Images[l] = subImage(knight, x, y, 32, 48);
                l++;
            }
        }

        int r = 0;
        for(int y = 96; y < 144; y += 48) {
            for(int x = 0; x < 128; x += 32) {
                knight_walk_right_Images[r] = subImage(knight, x, y, 32, 48);
                r++;
            }
        }

        int u = 0;
        for(int y = 144; y < 192; y += 48) {
            for(int x = 0; x < 128; x += 32) {
                knight_walk_up_Images[u] = subImage(knight, x, y, 32, 48);
                u++;
            }
        }

        knightPositionX = width()/2;
        knightPositionY = height()/2;
    }

//init the item
    public void initItem(){
        quality ="None";//qualitt of the weapon
        borad = loadImage("Images/borad.png");
        EHP2 = loadImage("Images/EHP2.png");
        HP2 = loadImage("Images/HP2.png");
        item = subImage(items,194,75,20,20);
        HpMedic = subImage(items,100,120,20,25);
        EhpMedic = subImage(items,0,100,20,20);
        borad = subImage(borad,0,0,306,153);

        discover_HpMedic = false;
        discover_EhpMedic = false;
        discover_Bow = false;
        discover_Axe = false;
    }

    //init dice!
    public void initdice(){
        weaponX = 999;
        weaponY = 999;
        itemX = 999;
        itemY = 999;
        Random_Dice = 999;
        Random_quality = 999;
    }

    //draw the knight
    public void drawKnight(){
        if (stop){
            if(direction==0){
                drawImage(knightImage, knightPositionX - 16, knightPositionY -20, 32, 48);
            }else if(direction==1){
                drawImage(knightUp,knightPositionX - 16, knightPositionY -20, 32, 48);
            }else if(direction==2){
                drawImage(knightRight,knightPositionX - 16, knightPositionY-20, 32, 48);
            }else if(direction==3){
                drawImage(knightLeft,knightPositionX - 16, knightPositionY-20, 32, 48);
            }
        }
        if(left) {
            if (state_index == 0){
                drawImage(knight_walk_left_Images[0], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 1){
                drawImage(knight_walk_left_Images[1], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 2){
                drawImage(knight_walk_left_Images[2], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 3){
                drawImage(knight_walk_left_Images[3], knightPositionX- 16, knightPositionY-20, 32, 48);
            }
        }
        if(right) {
            if (state_index == 0){
                drawImage(knight_walk_right_Images[0], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 1){
                drawImage(knight_walk_right_Images[1], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 2){
                drawImage(knight_walk_right_Images[2], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 3){
                drawImage(knight_walk_right_Images[3], knightPositionX- 16, knightPositionY-20, 32, 48);
            }
        }
        if(up) {
            if (state_index == 0){
                drawImage(knight_walk_up_Images[0], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 1){
                drawImage(knight_walk_up_Images[1], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 2){
                drawImage(knight_walk_up_Images[2], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 3){
                drawImage(knight_walk_up_Images[3], knightPositionX- 16, knightPositionY-20, 32, 48);
            }
        }
        if(down) {
            if (state_index == 0){
                drawImage(knight_walk_down_Images[0], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 1){
                drawImage(knight_walk_down_Images[1], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 2){
                drawImage(knight_walk_down_Images[2], knightPositionX- 16, knightPositionY-20, 32, 48);
            }else if (state_index == 3){
                drawImage(knight_walk_down_Images[3], knightPositionX- 16, knightPositionY-20, 32, 48);
            }
        }
    }

    //Animation ! let knight walk!
    public void updateKnight(){
        if(up) {
            if (state_index == 0){
                state_index = 1;
            }else if (state_index == 1){
                state_index = 2;
            }else if (state_index == 2){
                state_index = 3;
            }else if (state_index == 3){
                state_index = 0;
            }
            knightPositionY -= knightSpeed ;
        }
        if(down) {
            if (state_index == 0){
                state_index = 1;
            }else if (state_index == 1){
                state_index = 2;
            }else if (state_index == 2){
                state_index = 3;
            }else if (state_index == 3){
                state_index = 0;
            }
            knightPositionY += knightSpeed ;
        }
        if(left) {
            if (state_index == 0){
                state_index = 1;
            }else if (state_index == 1){
                state_index = 2;
            }else if (state_index == 2){
                state_index = 3;
            }else if (state_index == 3){
                state_index = 0;
            }
            knightPositionX -= knightSpeed;
        }
        if(right) {
            if (state_index == 0){
                state_index = 1;
            }else if (state_index == 1){
                state_index = 2;
            }else if (state_index == 2){
                state_index = 3;
            }else if (state_index == 3){
                state_index = 0;
            }
            knightPositionX += knightSpeed;
        }
    }

    Image bulletImage;
    Image arrowImage;
    Image axeImage;
    Image bullet = loadImage("Images/bullet.png");

    int ballSpeed;
    // bullet position
    double[] bulletPositionX;
    double[] bulletPositionY;

    // bullet velocity
    double[] bulletVelocityX;
    double[] bulletVelocityY;
    int[] axedirection;
    double knightVelocityX;
    double knightVelocityY;
    double knightAngle;
    double[] bulletAngle;

    // bullet active
    boolean[] bulletActive;

    // Initialise bullet
    public void initbullet() {
        ballSpeed = 150;

        // Allocate arrays
        bulletPositionX = new double[bullet_maxnumber];
        bulletPositionY = new double[bullet_maxnumber];
        bulletAngle = new double[bullet_maxnumber];

        bulletVelocityX = new double[bullet_maxnumber];
        bulletVelocityY = new double[bullet_maxnumber];
        axedirection = new int[bullet_maxnumber];

        bulletActive = new boolean[bullet_maxnumber];

        for(int i = 0; i < bullet_maxnumber; i++) {
            bulletActive[i] = false;
        }

        bulletImage = subImage(bullet,0,0,30,30);
        arrowImage = loadImage("Images/arrow.png");
        axeImage = subImage(bullet,825,70,50,50);
    }

    // Function to shoot a new bullet
    public void firebullet() {
        for(int i= 0; i < bullet_maxnumber; i++) {
            if(bulletActive[i] == false) {
                bulletPositionX[i] = knightPositionX;
                bulletPositionY[i] = knightPositionY;
                axedirection[i] = direction;

                if(direction==0){
                    bulletVelocityX[i] = 0;
                    bulletVelocityY[i] = 200;
                }else if(direction==1){
                    bulletVelocityX[i] = 0;
                    bulletVelocityY[i] = -200;
                }else if(direction==2){
                    bulletVelocityX[i] = 200;
                    bulletVelocityY[i] =  0;
                }else if(direction==3){
                    bulletVelocityX[i] = -200;
                    bulletVelocityY[i] =  0;
                }
                bulletActive[i] = true;
                break;
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getButton()==MouseEvent.BUTTON1){    // Check whether the obtained button is the left click of the mouse
            if(weapon_num == 2){
                a = e.getX();
                b = e.getY();
                firebullet();
            }else{
                a = e.getX();
                b = e.getY();
                firebullet1();
            }
        }
    }

    // The axe bullet
    public void firebullet1(){
        for(int i= 0; i < bullet_maxnumber; i++) {
            if(bulletActive[i] == false) {
                bulletPositionX[i] = knightPositionX;
                bulletPositionY[i] = knightPositionY;
                bulletVelocityX[i] = a - bulletPositionX[i];
                bulletVelocityY[i] = b - bulletPositionY[i];
                bulletAngle[i] = atan2(bulletVelocityX[i],-bulletVelocityY[i]);
                double l = length(bulletVelocityX[i], bulletVelocityY[i]);
                bulletVelocityX[i] = bulletVelocityX[i] * ballSpeed / l;
                bulletVelocityY[i] = bulletVelocityY[i] * ballSpeed / l;
//                System.out.println(bulletVelocityX[i]);
//                System.out.println(bulletVelocityY[i]);
                // Set it to active
                bulletActive[i] = true;
                break;
            }
        }
    }

    // Function to draw the bullet(not the axe bullet)

    public void drawbullet() {
        if(weapon_num == 0){
            for(int i = 0; i < bullet_maxnumber; i++) {
                if(bulletActive[i]) {
                    saveCurrentTransform();
                    translate(bulletPositionX[i], bulletPositionY[i]);
                    rotate(bulletAngle[i]);
                    drawImage(bulletImage, -25,-25,50,50);
                    restoreLastTransform();
                }
            }
        }else if(weapon_num == 1){
            for(int i = 0; i < bullet_maxnumber; i++) {
                if(bulletActive[i]) {
                    saveCurrentTransform();
                    translate(bulletPositionX[i]+20, bulletPositionY[i]+20);
                    rotate(bulletAngle[i]);
                    drawImage(arrowImage, -10,5,8,40);
                    restoreLastTransform();
                }
            }
        }else if(weapon_num == 2){
            for(int i = 0; i < bullet_maxnumber; i++) {
                if(bulletActive[i]) {
                    saveCurrentTransform();
                    translate(bulletPositionX[i]+20, bulletPositionY[i]+20);
                    if(axedirection[i] == 0){
                        rotate(90);
                        drawImage(axeImage, -25,-25,axe_Size,axe_Size);
                    }else if(axedirection[i] == 2){
                        drawImage(axeImage, -25,-25,axe_Size,axe_Size);
                    }else if(axedirection[i] == 3){
                        rotate(180);
                        drawImage(axeImage, -25,-25,axe_Size,axe_Size);
                    }else{
                        rotate(-90);
                        drawImage(axeImage, -25,-25,axe_Size,axe_Size);
                    }
                    restoreLastTransform();
                }
            }
        }
    }
    //if be attacked run this!
    public void getAttacked(){
        for(int i= 0; i < maxMonster; i++) {
            knightVelocityX = knightPositionX - monsterPositionX[i];
            knightVelocityY = knightPositionY - monsterPositionY[i];
        }
        knightAngle = atan2( knightVelocityX,- knightVelocityY);
        double l2 = length(knightVelocityX, knightVelocityY);
        knightVelocityX = knightVelocityX * 60 / l2;
        knightVelocityY = knightVelocityY * 60 / l2;
        knightPositionX +=  knightVelocityX;
        knightPositionY +=  knightVelocityY;
        //get attacked sound
        playAudio(GetAttacked);
    }


    // Function to update 'move' the normal bullet
    public void updatebullet(double dt) {
        for(int i = 0; i < bullet_maxnumber; i++) {
            bulletPositionX[i] += bulletVelocityX[i] * dt;
            bulletPositionY[i] += bulletVelocityY[i] * dt;
            if(bulletPositionX[i] < 0)         {bulletActive[i] = false;}
            if(bulletPositionX[i] >= width())  {bulletActive[i] = false;}
            if(bulletPositionY[i] < 0)         {bulletActive[i] = false;}
            if(bulletPositionY[i] >= height()) {bulletActive[i] = false;}
        }
    }

    // Function to update 'move' the axe bullet
    public void updateaxe(double dt) {
        for(int i = 0; i < bullet_maxnumber; i++) {
            bulletPositionX[i] += bulletVelocityX[i] * dt;
            bulletPositionY[i] += bulletVelocityY[i] * dt;
            if(bulletPositionX[i] < 0)         {bulletActive[i] = false;}
            if(bulletPositionX[i] >= width())  {bulletActive[i] = false;}
            if(bulletPositionY[i] < 0)         {bulletActive[i] = false;}
            if(bulletPositionY[i] >= height()) {bulletActive[i] = false;}
        }
    }

    //-----------------------Monster--------------------------
    int maxMonster;
    double[] monsterPositionX;
    double[] monsterPositionY;
    double[] monsterVelocityX;
    double[] monsterVelocityY;
    boolean[] monsterActive;
    Image monsterImage;
    Image monsterImage1;

    //init the monster
    public void initmonster() {
        maxMonster=5;
        monsterImage = loadImage("Images/monster.png");
        monsterImage1 = loadImage("Images/1monster.png");
        monsterPositionX = new double[maxMonster];
        monsterPositionY = new double[maxMonster];
        monsterVelocityX = new double[maxMonster];
        monsterVelocityY = new double[maxMonster];
        monsterActive = new boolean[maxMonster];
        monsterActive[0]=true;
        for(int i = 1; i < maxMonster; i++) {
            monsterActive[i] = false;
        }
    }
    public void randommonster0() {
        if(monsterActive[0]) {
        monsterPositionX[0] = rand(width());
        monsterPositionY[0] = rand(height());

        while (distance(monsterPositionX[0], monsterPositionY[0], knightPositionX, knightPositionY) < 100) {
            monsterPositionX[0] = rand(width());
            monsterPositionY[0] = rand(height());
        }
    }
}
//This is the different level
// more level more monster
    public void randommonster1() {
            if(monsterActive[1]==true) {
                monsterPositionX[1] = rand(width());
                monsterPositionY[1] = rand(height());
                while (distance(monsterPositionX[1], monsterPositionY[1], knightPositionX, knightPositionY) < 100) {
                    monsterPositionX[1] = rand(width());
                    monsterPositionY[1] = rand(height());
                }
            }
    }
    public void randommonster2() {
        if(monsterActive[2]==true) {
            monsterPositionX[2] = rand(width());
            monsterPositionY[2] = rand(height());
            while (distance(monsterPositionX[2], monsterPositionY[2], knightPositionX, knightPositionY) < 100) {
                monsterPositionX[2] = rand(width());
                monsterPositionY[2] = rand(height());
            }
        }
    }
    public void randommonster3() {
        if(monsterActive[3]==true) {
            monsterPositionX[3] = rand(width());
            monsterPositionY[3] = rand(height());
            while (distance(monsterPositionX[3], monsterPositionY[3], knightPositionX, knightPositionY) < 100) {
                monsterPositionX[3] = rand(width());
                monsterPositionY[3] = rand(height());
            }
        }
    }

    public void randommonster4() {
        if(monsterActive[4]==true) {
            monsterPositionX[4] = rand(width());
            monsterPositionY[4] = rand(height());
            while (distance(monsterPositionX[4], monsterPositionY[4], knightPositionX, knightPositionY) < 100) {
                monsterPositionX[4] = rand(width());
                monsterPositionY[4] = rand(height());
            }
        }
    }

    // Update the monster
    public void updatemonster(double dt) {
        for (int i = 0; i < maxMonster; i++) {
            if (monsterActive[i] == true) {
                double d1, d2, d3;

                d1 = abs(knightPositionX - monsterPositionX[i]);
                d2 = abs((knightPositionX - width()) - monsterPositionX[i]);
                d3 = abs((knightPositionX + width()) - monsterPositionX[i]);

                if (d1 < d2 && d1 < d3) {
                    monsterVelocityX[i] = knightPositionX - monsterPositionX[i];
                } else if (d2 < d1 && d2 < d3) {
                    monsterVelocityX[i] = (knightPositionX - width()) - monsterPositionX[i];
                } else {
                    monsterVelocityX[i] = (knightPositionX + width()) - monsterPositionX[i];
                }

                d1 = abs(knightPositionY - monsterPositionY[i]);
                d2 = abs((knightPositionY - height()) - monsterPositionY[i]);
                d3 = abs((knightPositionY + height()) - monsterPositionY[i]);

                if (d1 < d2 && d1 < d3) {
                    monsterVelocityY[i] = knightPositionY - monsterPositionY[i];
                } else if (d2 < d1 && d2 < d3) {
                    monsterVelocityY[i] = (knightPositionY - height()) - monsterPositionY[i];
                } else {
                    monsterVelocityY[i] = (knightPositionY + height()) - monsterPositionY[i];
                }

                double l = length(monsterVelocityX[i], monsterVelocityY[i]);

                monsterVelocityX[i] *= 25 / l;
                monsterVelocityY[i] *= 25 / l;

                monsterPositionX[i] += monsterVelocityX[i] * dt;
                monsterPositionY[i] += monsterVelocityY[i] * dt;

                if (monsterPositionX[i] < 0) {
                    monsterPositionX[i] += width();
                }
                if (monsterPositionX[i] >= width()) {
                    monsterPositionX[i] -= width();
                }
                if (monsterPositionY[i] < 0) {
                    monsterPositionY[i] += height();
                }
                if (monsterPositionY[i] >= height()) {
                    monsterPositionY[i] -= height();
                }
            }
        }
    }

    public void drawmonster() {
        for(int i = 0; i < maxMonster; i++) {
            // Only draw the laser if it's active
            if(monsterActive[i]) {
                saveCurrentTransform();

                translate(monsterPositionX[i], monsterPositionY[i]);

                if(knightPositionX<monsterPositionX[i]){
                    drawImage(monsterImage1, -30, -30, 40, 40);
                }else {
                    drawImage(monsterImage, -30, -30, 40, 40);
                }
                restoreLastTransform();
            }
        }
    }

//------------------------items------------
//draw the item
    public void drawitem(){
        if(itemActive==true){
            saveCurrentTransform();
            drawImage(item,itemX , itemY, 20, 20);
            restoreLastTransform();
        }
    }

    public void getitem(){
        itemVelocityX = knightPositionX - itemX;
        itemVelocityY = knightPositionY - itemY;
        itemAngle = atan2(itemVelocityX,-itemVelocityY);
        double l = length(itemVelocityX,itemVelocityY);
        itemVelocityX = itemVelocityX * ballSpeed / l;
        itemVelocityY = itemVelocityY * ballSpeed / l;
    }

//let item move to knight
    public void updateitem(double dt){
        if(itemGet == true){
            itemVelocityX = itemVelocityX * dt * 60;
            itemVelocityY = itemVelocityY * dt * 60;
            itemX += itemVelocityX *dt ;
            itemY += itemVelocityY *dt ;
        }
    }


//update the weapon
    public void updateweapon(){
        if(weaponGet == true){
            weaponX = 999;
            weaponY = 999;
        }
    }
//draw weapon
    public void drawweapon(int a){
        drawImage(Weapon_type[a], weaponX, weaponY, 40, 40);
    }

    public void drawHpMedic(){
        drawImage(HpMedic,weaponX,weaponY,40,40);
    }

    public void drawEhpMedic(){
        drawImage(EhpMedic,weaponX,weaponY,40,40);
    }

    public void drawHP(){
        int k = 0;
        for(k=0;k<Max_HP;k++){
            if(k<HP){
                drawImage(HP2,20+30*k,20,30,30);
            }else{
                drawImage(EHP2,20+30*k,20,30,30);
            }

        }
    }
// Draw the board
    public  void drawboard(int a){
        if(aroundweapon==true){
            drawImage(borad, 200, 0, 250, 125);
            if(a<=10){
                quality = "Legend";
                changeColor(255,236,82);
                if(discover_Bow ){
                    drawText(290, 25, "BOW", "Arial", 20);
                    drawText(210, 45, "OHHHHH!!!!! ", "Arial", 20);
                    drawText(210, 65, "This is a Legend bow! ", "Arial", 20);
                    drawText(210, 85, "Wish you unstoppable", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_speed+150 ", "Arial", 20);
                }else if(discover_Axe){
                    drawText(290, 25, "AXE", "Arial", 20);
                    drawText(210, 45, "OHHHHH!!!!! ", "Arial", 20);
                    drawText(210, 65, "This is a Legend axe! ", "Arial", 20);
                    drawText(210, 85, "Wish you unstoppable", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_range+200 ", "Arial", 20);
                }
                drawText(400, 25, String.valueOf(a), "Arial", 20);
            } else if(a<=30){
                quality = "Epic";
                changeColor(168,10,255);
                if(discover_Bow ){
                    drawText(290, 25, "BOW", "Arial", 20);
                    drawText(210, 45, "A well-made bow", "Arial", 20);
                    drawText(210, 65, "Every detail is perfect ", "Arial", 20);
                    drawText(210, 85, "It must can help you a lot", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_speed+75", "Arial", 20);
                }else if(discover_Axe){
                    drawText(290, 25, "AXE", "Arial", 20);
                    drawText(210, 45, "A well-made axe", "Arial", 20);
                    drawText(210, 65, "Every detail is perfect ", "Arial", 20);
                    drawText(210, 85, "It must can help you a lot", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_range+100", "Arial", 20);
                }
                drawText(400, 25, String.valueOf(a), "Arial", 20);
            }else if(a<=60){
                quality = "Rare";
                changeColor(38,183,255);
                if(discover_Bow ){
                    drawText(290, 25, "BOW", "Arial", 20);
                    drawText(210, 45, "An ordinary bow", "Arial", 20);
                    drawText(210, 65, "No disadvantages ", "Arial", 20);
                    drawText(210, 85, "Better than nothing", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_speed+30", "Arial", 20);
                }else if(discover_Axe){
                    drawText(290, 25, "AXE", "Arial", 20);
                    drawText(210, 45, "An ordinary axe", "Arial", 20);
                    drawText(210, 65, "No disadvantages ", "Arial", 20);
                    drawText(210, 85, "Better than nothing", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_range+70", "Arial", 20);
                }
                drawText(400, 25, String.valueOf(a), "Arial", 20);
                }else if( a<=100){
                quality = "Common";
                changeColor(254,255,249);
                if(discover_Bow ){
                    drawText(290, 25, "BOW", "Arial", 20);
                    drawText(210, 45, "A worn bow", "Arial", 20);
                    drawText(210, 65, "Are you sure to use it?", "Arial", 20);
                    drawText(210, 85, "hope doesn't mess up", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_speed+10", "Arial", 20);
                }else if(discover_Axe){
                    drawText(290, 25, "AXE", "Arial", 20);
                    drawText(210, 45, "A worn axe", "Arial", 20);
                    drawText(210, 65, "Are you sure to use it?", "Arial", 20);
                    drawText(210, 85, "hope doesn't mess up", "Arial", 20);
                    drawBoldText(210, 110, "Piercing attack_range+30", "Arial", 20);
                }
                drawText(400, 25, String.valueOf(a), "Arial", 20);
                }
        }
    }



    //init the hole game
    @Override
    public void init() {
        setWindowSize(640,640);
        knight = loadImage("Images/knight.png");
        items = loadImage("Images/items.png");
        left  = false;
        right = false;
        up    = false;
        down  = false;
        gameOver = false;
        stop = true;
        direction = 0;
        Random_Dice = 999;
        weapon_num = 0;
        HP = 3;
        Max_HP = 5;
        axe_Size = 50;
        score = 0;
        knightSpeed = 3;

        initItem();
        initKnight();
        initbullet();
        initmonster();
        backgroundMusic = loadAudio("Audio/ptpy.wav");
        monsterDead = loadAudio("Audio/monsterDead.mp3");
        GetAttacked = loadAudio("Audio/Hit.mp3");

        startAudioLoop(backgroundMusic);

    }
// update the data of the game
    @Override
    public void update(double dt) {
        if (gameOver) {
            return;
        }
        updateKnight();
        if(weapon_num == 2){
            updateaxe(dt);
        }else{
            updatebullet(dt);
        }
        updatemonster(dt);
        updateitem(dt);
        updateweapon();
        //GAME OVER
        if (HP <= 0) {
            gameOver = true;
        }
        //Get attacked
        for (int i = 0; i < maxMonster; i++) {
            if (distance(knightPositionX+16, knightPositionY+24, monsterPositionX[i]+20, monsterPositionY[i]+20) < 30) {
                HP -= 1;
                getAttacked();
            }
        }
        //Active the item
        if (distance(knightPositionX, knightPositionY, itemX, itemY) < 150) {
            getitem();
            itemGet = true;
        }
        //Active the weapon
        if (distance(knightPositionX, knightPositionY, weaponX, weaponY) < 100) {
            aroundweapon = true;
        }
        //Not-Active the weapon
        if (distance(knightPositionX, knightPositionY, weaponX, weaponY) > 100) {
            aroundweapon = false;
        }
        //Get the item!
        if (distance(knightPositionX, knightPositionY, itemX, itemY) < 15) {
            // Collision!
            itemGet = false;
            itemActive = false;
            if(knightSpeed<=7){
                knightSpeed += 0.5;
            }
            itemX = 999;
            itemY = 999;
        }
        if (knightPositionX <= 20 && knightPositionY <= 20) {
            knightPositionX = 20;
            knightPositionY = 20;
        } else if (knightPositionX >= mWidth && knightPositionY <= 5) {
            knightPositionX = mWidth;
            knightPositionY = 0;
        } else if (knightPositionX <= 5 && knightPositionY >= mHeight) {
            knightPositionX = 0;
            knightPositionY = mHeight;
        } else if (knightPositionX >= mWidth && knightPositionY >= mHeight) {
            knightPositionY = mWidth;
            knightPositionX = mHeight;
        }
        if (knightPositionX >= mWidth) {
            knightPositionX = mWidth;
        } else if (knightPositionX <= 0) {
            knightPositionX = 0;
        } else if (knightPositionY >= mHeight) {
            knightPositionY = mHeight;
        } else if (knightPositionY <= 0) {
            knightPositionY = 0;
        }
        for (int i = 0; i < bullet_maxnumber; i++) {
            if (bulletActive[i] == true) {
                for (int j = 0; j < maxMonster; j++) {
                    if (distance(bulletPositionX[i], bulletPositionY[i], monsterPositionX[j], monsterPositionY[j]) < (axe_Size-15)) {
                        playAudio(monsterDead);
                        if(weapon_num == 0){
                            bulletActive[i] = false;
                        }else if(weapon_num == 1){
                            bulletActive[i] = true;
                        }
                        score += 1;
                        Random_Dice = roll_dice(5);
                        Random_quality = roll_dice(100);
                        weaponX = monsterPositionX[j];
                        weaponY = monsterPositionY[j];
                        itemX = monsterPositionX[j];
                        itemY = monsterPositionY[j];

                        if(score<5){
                        }
                        if (score>=5){
                            monsterActive[1]=true;
                        }
                        if (score>=10){
                            monsterActive[2]=true;
                        }
                        if (score>=20){
                            monsterActive[3]=true;
                        }
                        if (score>=30){
                            monsterActive[4]=true;
                        }
                        if (j==0){
                            randommonster0();
                        }else if (j==1){
                            randommonster1();
                        }else if (j==2){
                            randommonster2();
                        }else if (j==3){
                            randommonster3();
                        }else if (j==4){
                            randommonster4();
                        }
                    }
                }
            }
        }
    }

// Draw everything!!!
    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(640, 640);

        if(gameOver == false) {
        Image background = loadImage("Images/grass.png");
        drawImage(background, 0, 0);
        drawHP();
        drawKnight();
        drawbullet();
        drawmonster();
        if(Random_Dice==1) {
            if(Random_quality<=60){
                discover_HpMedic = true;
                drawHpMedic();
            }else{
                discover_EhpMedic = true;
                drawEhpMedic();
            }
        }else if(Random_Dice==2){
            if(Random_quality <= 100){
                discover_Axe = false;
                discover_Bow = true;
                drawboard(Random_quality);
                drawweapon(1);
            }

        }else if(Random_Dice== 3){
            discover_Bow = false;
            discover_Axe = true;
            drawboard(Random_quality);
            drawweapon(0);
        }else if(Random_Dice ==4){
            itemActive = true;
            drawitem();
        }
        changeColor(white);
        drawText(445,50,"Score:","宋体",40);
        drawText(575,50, String.valueOf(score),"宋体",40);
        }else {
            // If the game is over
            // Display GameOver text
            changeColor(white);
            drawText(width()/2-165, height()/2, "GAME OVER!", "Arial", 50);
            drawText(width()/2-280, height()/2+80, "Press the space to restart", "Arial", 50);
        }
    }


    //dices to get random number
    public int roll_dice(){
        return rand(10);
    }
    public int roll_dice(int n){
        return rand(n);
    }
    public int roll_quality(){
        return rand(100);
    }

    //control and attack
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'A' ||e.getKeyChar() == 'a') {
            direction=3;
            stop = false;
            right = false;
            left = true;
        }

        if(e.getKeyChar() == 'D' ||e.getKeyChar() == 'd') {
            direction=2;
            stop = false;
            left = false;
            right = true;
        }
        if(e.getKeyChar() == 'W' ||e.getKeyChar() == 'w') {
            direction=1;
            stop = false;
            down = false;
            up = true;
        }
        if(e.getKeyChar() == 'S' ||e.getKeyChar() == 's') {
            direction=0;
            stop = false;
            up = false;
            down = true;
        }

//Pick up
        if(e.getKeyChar() == 'F' ||e.getKeyChar() == 'f'){
            if(distance(knightPositionX,knightPositionY,weaponX,weaponY) < 50){
                if(discover_HpMedic){
                    if(HP<Max_HP){
                        HP += 1;
                        discover_HpMedic = false;
                        initdice();
                    }else{
                        discover_HpMedic = false;
                        initdice();
                    }
                }else if(discover_EhpMedic){
                    if(Max_HP < 10){
                        Max_HP += 1;
                    }else{
                        Max_HP += 0;
                    }
                    discover_EhpMedic = false;
                    initdice();
                }else if(discover_Bow){
                    weapon_num =1;
                    discover_Bow = false;
                    initdice();
                    if(quality.equals("Legend")){
                        ballSpeed = 750;
                        knightSpeed = 16;
                        axe_Size = 50;
                        System.out.println("You get a Legend bow!!!");
                    }else if(quality.equals("Epic")){
                        ballSpeed = 450;
                        knightSpeed = 13;
                        axe_Size = 50;
                        System.out.println("You get a Epic bow");
                    }else if(quality.equals("Rare")){
                        ballSpeed = 200;
                        knightSpeed = 9;
                        axe_Size = 50;
                        System.out.println("You get a Rare bow");
                    }else if(quality.equals("Common")){
                        ballSpeed = 100;
                        knightSpeed = 6;
                        axe_Size = 50;
                        System.out.println("You get a Common bow");
                    }
                }else if(discover_Axe) {
                    weapon_num = 2;
                    discover_Axe = false;
                    initdice();
                    if (quality.equals("Legend")) {
                        axe_Size = 200;
                        System.out.println("You get a Legend axe!!!");
                    } else if (quality.equals("Epic")) {
                        axe_Size = 100;
                        System.out.println("You get a Epic axe");
                    } else if (quality.equals("Rare")) {
                        axe_Size = 75;
                        System.out.println("You get a Rare axe");
                    } else if (quality.equals("Common")) {
                        axe_Size = 50;
                        System.out.println("You get a Common axe");
                    }
                }
            }
        }
        if(gameOver == true){
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                stopAudioLoop(backgroundMusic);
                init();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'A' ||e.getKeyChar() == 'a') {
            left = false;
            stop = true;
        }
        if(e.getKeyChar() == 'D' ||e.getKeyChar() == 'd') {
            right = false;
            stop = true;
        }
        if(e.getKeyChar() == 'W' ||e.getKeyChar() == 'w') {
            up = false;
            stop = true;
        }
        if(e.getKeyChar() == 'S' ||e.getKeyChar() == 's') {
            down = false;
            stop = true;
        }
    }
}