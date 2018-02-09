import org.gamecontrolplus.gui.*;  //necessario para o dualshock
import org.gamecontrolplus.*;   //necessario para o dualshock
import net.java.games.input.*;   //necessario para o dualshock

ControlIO control;
Configuration config;
ControlDevice dualshock;


ControlSlider L3x;
ControlSlider L3y;
ControlSlider R3x;
ControlSlider R3y;
ControlSlider L2a;
ControlSlider R2a;

ControlButton Q;
ControlButton X;
ControlButton O;
ControlButton T;
ControlButton L1;
ControlButton R1;
ControlButton L2;
ControlButton R2;
ControlButton Share;
ControlButton Opt;
ControlButton L3;
ControlButton R3;
ControlButton PS;
ControlButton pad;

ControlHat direcionais;



/*
L3x
L3y
R3x
R3y
L2a
R2a
Q
X
O
T
L1
R1
L2
R2
Share
Opt
L3
R3
PS
pad
dir  direcionais  2  HAT  cooliehat: pov  0  1.0  0.0
*/
int debounce = 150;
int lastDirPress = 0;

void dualshockInit() {
  if (useDualshock) {
    // Initialise the ControlIO
    control = ControlIO.getInstance(this);
    // Find a device that matches the configuration file
    dualshock = control.getMatchedDevice("dualshock");
    if (dualshock == null) {
      println("No suitable device configured");
      System.exit(-1); // End the program
    }

    direcionais = dualshock.getHat("dir");

    L3x = dualshock.getSlider("L3x");
    L3y = dualshock.getSlider("L3y");
    R3x = dualshock.getSlider("R3x");
    R3y = dualshock.getSlider("R3y");
    L2a = dualshock.getSlider("L2a");
    R2a = dualshock.getSlider("R2a");
    
    Q = dualshock.getButton("Q");
    X = dualshock.getButton("X");
    O = dualshock.getButton("O");
    T = dualshock.getButton("T");
    L1 = dualshock.getButton("L1");
    R1 = dualshock.getButton("R1");
    L2 = dualshock.getButton("L2");
    R2 = dualshock.getButton("R2");
    Share = dualshock.getButton("Share");
    Opt = dualshock.getButton("Opt");
    L3 = dualshock.getButton("L3");
    R3 = dualshock.getButton("R3");
    PS = dualshock.getButton("PS");
    pad = dualshock.getButton("pad");
    
    //histerese para os analogicos
    L3x.setTolerance(0.08);
    L3y.setTolerance(0.08);
    R3x.setTolerance(0.08);
    R3y.setTolerance(0.08);
    L2a.setTolerance(0.08);
    R2a.setTolerance(0.08);
    //slider.getTolerance();

    //if(hat.down()){println("ha");}
    
  }
}

boolean debounce(){
  return (millis() - lastPress> debounce) ? true : false;
}

void checkDualshock() {
  if (useDualshock) {
    //print(dualshock.getButton("Q").pressed());
    //dualshock.getSlider("XPOS").getValue();
    
    offsetX += L3x.getValue()*60;
    offsetY += L3y.getValue()*60;
    if(L3.pressed()){offsetX =0;offsetY =0;} //aperte o joystick esquerdo para resetar os offsets
    
    rotationZ -= R3x.getValue()*10;
    rotationX -= R3y.getValue()*10;
    if(R3.pressed()){rotationZ =0;rotationX =0;} //aperte o joystick direito para resetar a rotacao
    
    
    //if(Q.pressed()){}
   
    if(R1.pressed() || direcionais.right()){
      if(debounce() && selectedMenuItem < menu.length - 1){
        selectedMenuItem++;lastPress = millis();
      }
    }
    if(L1.pressed() || direcionais.left()){
      if(debounce() && selectedMenuItem > 0){
        selectedMenuItem--;lastPress = millis();
      }
    }

    switch(selectedMenuItem){

      case 0:

if(direcionais.down() || X.pressed()){
  if(debounce()){
      selectedMenuItem = 0;
  
      if(songPlaying != fileNames.length-1 && doPlay[songPlaying+1]){
       songPlaying ++;
       if(songPlaying > fileNames.length-1) songPlaying = 0;
      }
      else if(songPlaying == fileNames.length-1 && doPlay[0]){
        songPlaying = 0;
      }
      else{
        boolean doLoop = true; // tentei fazer com "while" e "return" mas não estava funcionando, provavelmente por erro de sintaxe..
        for(int i = songPlaying; i<fileNames.length;i++ ){
          if(doLoop){
            songPlaying++;
            if(songPlaying > fileNames.length-1) songPlaying = 1;
          }
          if(doPlay[songPlaying]) doLoop = false;
        }
        
      }
    
      if(curated){
        changeMusic(songPlaying, listaMusicas[songPlaying].cutP[0], listaMusicas[songPlaying].cutP[1]);
        //changeMusic(songPlaying, 0, 1);
      }
      else{
        changeMusic(songPlaying, 0, 1);
      }
    lastPress = millis();
    }
  }
if(O.pressed()){
        if(debounce()){
          songs[songPlaying].skip(1000);
          lastPress = millis();
        }
      }

      if(Q.pressed()){
        if(debounce()){songs[songPlaying].skip(-1000);
ampMode = !ampMode;
          lastPress = millis();
        }
      }





break;
      // FREQUENCY
      case 1:
        if(direcionais.down() || direcionais.up()){
          if(debounce()){
            freqMode = !freqMode;
            lastPress = millis();
          }
        }

        if(X.pressed()){
          if(debounce()){
            xHighPass +=50;
            if(xHighPass > 1000) xHighPass = 0;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            xLowPass +=50;
            if(xLowPass > 1000) xLowPass = 0;
            lastPress = millis();
          }
        }



        break;


      //  AMPLITUDE
      case 2:
      if(direcionais.down() || direcionais.up()){
        if(debounce()){
          ampMode = !ampMode;
          lastPress = millis();
        }
      }

        if(X.pressed()){
          if(debounce()){
            constrainOn = !constrainOn;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            compZ +=100;
            if(compZ > 1000) compZ = 100;
            lastPress = millis();
          }
        }
        if(O.pressed()){
          if(debounce()){
            expZ +=50;
            if(expZ > 700) expZ = 100;
            lastPress = millis();
          }
        }
        if(T.pressed()){
          if(debounce()){
            gain *=2;
            if(gain > 64) gain = 1/64;
            lastPress = millis();
          }
        }

      break;


      //     A WEIGHTING
      case 3:
      if(direcionais.down() || direcionais.up()){
        if(debounce()){
           weightingMode = !weightingMode;
           lastPress = millis();
        }
      }
      break;



      //    Z OFFSET
      case 4:
      if(direcionais.down() || direcionais.up()){
         if(debounce()){
           zOffset = !zOffset;
           lastPress = millis();
         }
      }
      break;


      //     DRAW MODE
      case 5:
      if(direcionais.up()){
         if(debounce()){
          drawMode++;
          lastPress = millis();
         }
      }

      if(direcionais.down()){
          if(debounce()){
            drawMode--;
            lastPress = millis();
          }
      }
      if(drawMode > 6){drawMode = 0;}
      else if(drawMode < 0){drawMode = 6;}

      if(X.pressed()){
          if(debounce()){
            drawSize *=2;
            if(drawSize > 16) drawSize = 1;
            lastPress = millis();
          }
      }
      break;

      //     VIEW MODE
      case 6:
      if(direcionais.down() || direcionais.up()){
         if(debounce()){
           viewMode = !viewMode;
           lastPress = millis();
         }
      }


      break;
      //    HUE MAP
      case 7:  
      if(direcionais.down()){
         if(debounce()){
           hueMap = !hueMap;
           lastPress = millis();
         }
      }
        if(X.pressed()){
          if(debounce()){
            startHue +=50;
            if(startHue > 1000) startHue = 0;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            endHue -=50;
            if(endHue < 50) endHue = 0;
            lastPress = millis();
          }
        }
      break;

      // SAT MAP
      case 8:  
      if(direcionais.down()){
         if(debounce()){
           satMap = !satMap;
           lastPress = millis();
         }
      }
        if(X.pressed()){
          if(debounce()){
            startSatMap +=50;
            if(startSatMap > 1000) startSatMap = 0;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            endSatMap -=50;
            if(endSatMap < 50) endSatMap = 1000;
            lastPress = millis();
          }
        }
        if(O.pressed()){
          if(debounce()){
            lowSatMap +=5;
            if(lowSatMap > 100) lowSatMap = 0;
            lastPress = millis();
          }
        }
        if(T.pressed()){
          if(debounce()){
            highSatMap -=5;
            if(highSatMap < 0) highSatMap = 100;
            lastPress = millis();
          }
        }
      break;


     //    BRI MAP
      case 9:  
      if(direcionais.down()){
         if(debounce()){
           briMap = !briMap;
           lastPress = millis();
         }
      }
        if(X.pressed()){
          if(debounce()){
            startBri +=50;
            if(startBri > 1000) startBri = 0;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            endBri -=50;
            if(endBri < 0) endBri = 1000;
            lastPress = millis();
          }
        }
      break;


      //    OPA MAP
      case 10:  
      if(direcionais.down()){
         if(debounce()){
           opaMap = !opaMap;
           lastPress = millis();
         }
      }
        if(X.pressed()){
          if(debounce()){
            startOpaMap +=50;
            if(startOpaMap > 1000) startOpaMap = 0;
            lastPress = millis();
          }
        }
        if(Q.pressed()){
          if(debounce()){
            endOpaMap -=50;
            if(endOpaMap < 0) endOpaMap = 1000;
            lastPress = millis();
          }
        }
        if(O.pressed()){
          if(debounce()){
            lowOpaMap +=5;
            if(lowSatMap > 100) lowOpaMap = 0;
            lastPress = millis();
          }
        }
        if(T.pressed()){
          if(debounce()){
            highOpaMap -=5;
            if(highOpaMap < 0) highOpaMap = 100;
            lastPress = millis();
          }
        }
      break;

      default:
      break;
      
    }

    //println(offsetY + "  " + L3y.getValue());
  }
}

void keyReleased() {
  lastPress = millis();
  if (keyCode == UP) {
    offsetY-=50;
  }

  if (keyCode == DOWN) {
    offsetY+=50;
  }
    if (keyCode == LEFT) {
    offsetX-=50;
  }

  if (keyCode == RIGHT) {
    offsetX+=50;
  }


  //###################################
  if (key == '1') {
    hueMap = !hueMap;
    selectedMenuItem = 7;
  }
    if (key == '2') {
    satMap = !satMap;
    selectedMenuItem = 8;
  }
    if (key == '3') {
    briMap = !briMap;
    selectedMenuItem = 9;
  }
    if (key == '4') {
    opaMap = !opaMap;
    selectedMenuItem = 10;
  }

  if (key == 'o') {
    zOffset = !zOffset;
    selectedMenuItem = 4;
  }
  if (key == 'f') {
    freqMode = !freqMode;
    selectedMenuItem = 1;
  }
  if (key == 't') {
    ampMode = !ampMode;
    selectedMenuItem = 2;
  }
  if (key == 'y') {
    weightingMode = !weightingMode;
    selectedMenuItem = 3;
  }
  if (key == 'u') {
    if (drawMode < 8){
      drawMode ++;
    }
    if(drawMode == 8){drawMode = 0;}
    selectedMenuItem = 5;
  }
  if (key == 'p') {
    viewMode = !viewMode;
    selectedMenuItem = 6;
  }

  if (key == 'a') {
    rotationZ-=5;
  }
  if (key == 'd') {
    rotationZ+=5;
  }
  if (key == 's') {rotationX-=5;}
  if (key == 'w') {rotationX+=5;}
  if (keyCode == BACKSPACE) {
    offsetX=0;offsetX=0;
    rotationX=0;rotationZ=0;
  }
  if (key == 'z') {
    if (xLowPass < 1000) xLowPass+=20;
    println(xLowPass);
  }
  if (key == 'x') {
    if (xLowPass > 0) xLowPass-=20;
    println(xLowPass);
  }
  if (key == 'c') {
    if (xHighPass < 1000) xHighPass+=20;
    println(xHighPass);
  }
  if (key == 'v') {
    if (xHighPass > 0) xHighPass-=20;
    println(xHighPass);
  }
  if (key == 'b') {
    if (selectedMenuItem > 0) selectedMenuItem-=1;
    println(selectedMenuItem);
  }
  if (key == 'n') {
    if (selectedMenuItem < menu.length-1) selectedMenuItem+=1;
    println(selectedMenuItem);
  }

  if (key == 'l'){songs[songPlaying].skip(-1000);}
  if (key == ';'){songs[songPlaying].skip(1000);}

  if (key == 'k'){
      selectedMenuItem = 0;
  
      if(songPlaying != fileNames.length-1 && doPlay[songPlaying+1]){
       songPlaying ++;
       if(songPlaying > fileNames.length-1) songPlaying = 0;
      }
      else if(songPlaying == fileNames.length-1 && doPlay[0]){
        songPlaying = 0;
      }
      else{
        boolean doLoop = true; // tentei fazer com "while" e "return" mas não estava funcionando, provavelmente por erro de sintaxe..
        for(int i = songPlaying; i<fileNames.length;i++ ){
          if(doLoop){
            songPlaying++;
            if(songPlaying > fileNames.length-1) songPlaying = 1;
          }
          if(doPlay[songPlaying]) doLoop = false;
        }
        
      }
    
      if(curated){
        changeMusic(songPlaying, listaMusicas[songPlaying].cutP[0], listaMusicas[songPlaying].cutP[1]);
        //changeMusic(songPlaying, 0, 1);
      }
      else{
        changeMusic(songPlaying, 0, 1);
      }
    }

}




