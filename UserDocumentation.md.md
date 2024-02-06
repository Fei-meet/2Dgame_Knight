# Userdocumentation.md

##   Design decisions

Our team decided to make a game which the player take on the role of a knight and increase power and get scores by fighting monsters. As game goes on, the player can get better weapons and the number of monsters will increase.

##  Game Engine Architecture

Our game design is based on Game Engine, so our game architecture is similar to it:

![image-20240206224638951](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224638951.png)

At the start of the game, initialization takes place. Set some basic values and functions to their initial values. As the game progresses, loop the update and paintComponent functions, which update the game data in real time, and paintComponent which paints the game

##  Features of the game 

###  Sprites and animation

Most of our images come from sprites, such as:

![image-20240206224717222](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224717222.png)

 At the same time, we have animations of characters moving and animations of monsters moving. You can see animations of knight moving and monsters turning around.

### Sound effects and Scoring system:

 Wehavebackground music, enemy death music, injury music, etc. In the top right corner of the game, there's a scoring system that gives extra points for each kill.

### Start and help menu

![image-20240206224757543](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224757543.png)

### Different item/weapon/medicine/level

 We have different weapons, different weapons have different qualities (legendary, epic, good, average), and different attack modes and bullet effects

![image-20240206224816745](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224816745.png)

![image-20240206224825344](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224825344.png)

![image-20240206224836972](C:\Users\97263\AppData\Roaming\Typora\typora-user-images\image-20240206224836972.png)