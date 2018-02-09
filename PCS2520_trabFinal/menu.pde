String menu[] = { //rendering menu:
  "NOW PLAYING ",
  "FREQUENCY ", // 
  "AMPLITUDE ", // 
  "A WEIGHTING ",// + "\n" + "INTERFACE",
  "Z OFFSET ", // 
  "DRAW ",
  "VIEW ",
  "HUE ",
  "SATURATION ",
  "BRIGHTNESS ",
  "OPACITY "
};


public class Buttons{
       String s;
       String q;
       String x;
       String b;
       String t;
       String sVal;
       String qVal;
       String xVal;
       String bVal;
       String tVal;
}

public class Song{
  String name;

  float[] cutP;

  boolean[] dispVars;
  //boolean satMap = true;
  //boolean briMap = true;
  //boolean hueMap = true;
  //boolean opaMap = true;
  //boolean constrainOn = true;

  int[] dispVals;


}

Song listaMusicas[] = new Song[12];

void curatedSongs(){
  for (int i = 0; i < listaMusicas.length; i++) {
    listaMusicas[i] = new Song();
  }

  //nome secundário
  listaMusicas[0].name = "Interstellar OST";       //"coward.wav",
  listaMusicas[1].name = "";       //"mas.mp3",
  listaMusicas[2].name = "";       //"ppl.mp3",
  listaMusicas[3].name = "";       //"gerclap.mp3",
  listaMusicas[4].name = "";       //"aero.mp3",
  listaMusicas[5].name = "";       //"dentaku.mp3",
  listaMusicas[6].name = "";       //"radio.mp3", 
  listaMusicas[7].name = "";       //"music.mp3", 
  listaMusicas[8].name = "";       //"evil.mp3", 
  listaMusicas[9].name = "";       //"grillwalker.mp3",
  listaMusicas[10].name = "NOME SECUNDARIO";      //"ziq.mp3",
  listaMusicas[11].name = "";      //"ashes.mp3"
  
  //cortes da música
  listaMusicas[0].cutP = new float [] {0.845,1};
  listaMusicas[1].cutP = new float [] {0.0,1};
  listaMusicas[2].cutP = new float [] {0.05,1};
  listaMusicas[3].cutP = new float [] {0.85,1};
  listaMusicas[4].cutP = new float [] {0.62355,1};
  listaMusicas[5].cutP = new float [] {0.32355,1};
  listaMusicas[6].cutP = new float [] {0.32355,1};
  listaMusicas[7].cutP = new float [] {0.32355,1};
  listaMusicas[8].cutP = new float [] {0.32355,1};
  listaMusicas[9].cutP = new float [] {0.32355,1};
  listaMusicas[10].cutP = new float [] {0.82355,1};
  listaMusicas[11].cutP = new float [] {0.08355,1};

  listaMusicas[0].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[1].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[2].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[3].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[4].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[5].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[6].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[7].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[8].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[9].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[10].dispVars = new boolean [] {true,true,true,true,true};
  listaMusicas[11].dispVars = new boolean [] {true,true,true,true,true};


  // startHue = 0;
  // endHue = 1000;
  //
  // startBri = 500;
  // endBri = 1000;
  //
  // lowSatMap = 40;//70;
  // highSatMap = 100;
  // startSatMap = 300;
  // endSatMap = 1000;
  // lowOpaMap = 30;//70;
  // highOpaMap = 100;
  // startOpaMap = 200;
  // endOpaMap = 1000;
  //
  // gain = 1;

  listaMusicas[0].dispVals = new int [] {
  0, /*startHue*/
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[1].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[2].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[3].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[4].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[5].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[6].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[7].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[8].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[9].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[10].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };
  listaMusicas[11].dispVals = new int [] {
  0, /*startHue*/ 
  1000, /*endHue*/ 
  500, /*startBri*/
  1000, /*endBri*/ 
  40, /*lowSatMap*/ 
  100, /*highSatMap*/ 
  300, /*startSatMap*/ 
  1000, /*endSatMap*/ 
  30, /*lowOpaMap*/ 
  100, /*highOpaMap*/ 
  200, /*startOpaMap*/ 
  1000, /*endOpaMap*/ 
  1 /*gain*/ 
  };

  
}

Buttons freqB = new Buttons();
Buttons ampB = new Buttons();
//Buttons weighting = new Buttons();
//Buttons freq = new Buttons();
Buttons drawB = new Buttons();
Buttons hueB = new Buttons();
Buttons satB = new Buttons();
Buttons briB = new Buttons();
Buttons opaB = new Buttons();

int _lengthMins;
int _lengthSecs;
int _lengthMillis;
String lengthMins;
String lengthSecs;
String lengthMillis;

int _cutLengthMins;
int _cutLengthSecs;
int _cutLengthMillis;
String cutLengthMins;
String cutLengthSecs;
String cutLengthMillis;

int _positionMins;
int _positionSecs;
int _positionMillis;
String positionMins;
String positionSecs;
String positionMillis;

int _startMins;
int _startSecs;
int _startMillis;
String startMins;
String startSecs;
String startMillis;

int _endMins;
int _endSecs;
int _endMillis;
String endMins;
String endSecs;
String endMillis;

boolean zOffset = true;
boolean freqMode = true;
boolean ampMode = true;
boolean weightingMode = true;
int drawMode = 0;
boolean viewMode = true;

boolean curated = true;

int menuItemAvgLength = 800;
int menuItemAvgMargin = 100;
int selectedMenuItem = 2;
int menuItemXPosition;

PFont rbtConReg;
PFont rbtConLight;
PFont rbtConBold;

int lastPress;

void fontes() {
  rbtConLight = createFont("RobotoCondensed-Light", 128);
  rbtConReg = createFont("RobotoCondensed-Regular", 128);
  rbtConBold = createFont("RobotoCondensed-Bold", 128);
  
  
  //textMode(MODEL);
}

int menuSpeed = 400;
int menuItemXPositionTarget;
float menuOpa = 0;
boolean  menuActive = true; // controla se o menu esta visivel
//boolean menuMoving = false; // diz se o menu esta se mexendo
int menuHideDelay = 5000;


void menu() {
    noStroke();
    //menu.length
    //decide a posição final onde o menu tem de estar
    menuItemXPositionTarget = menuItemAvgLength*(selectedMenuItem) + selectedMenuItem*menuItemAvgMargin;
    
    //anima o menu para aquela posição
    if(menuItemXPosition > menuItemXPositionTarget + menuSpeed){ // "+ menuSpeed" permite certa margem de histerese para que o menu não fique pulando caso "menuSpeed" seja um valor quebrado
      menuItemXPosition -= menuSpeed;
    }
    else if (menuItemXPosition < menuItemXPositionTarget - menuSpeed) {
      menuItemXPosition += menuSpeed;
    }
    else if(menuItemXPosition > menuItemXPositionTarget){// modo gambiarra de desacelerar, além de fazer com que os itens consigam ficar em intervalos menores do que menuSpeed
      menuItemXPosition -= 50;
    }
    else if (menuItemXPosition < menuItemXPositionTarget) {
      menuItemXPosition += 50;
    }

    
  if(millis() - lastPress> menuHideDelay) menuActive = false;
  else {
    menuActive = true;
  }
  if(menuActive){
    if(menuOpa<1) menuOpa +=.5;
  }
  else if (!menuActive) {
    if(menuOpa>0.01) menuOpa -=.05;
  }

  //se estiver iniciando o programa, não animar o menu
  if(boot==0) {menuItemXPosition = menuItemXPositionTarget; boot = 1;}

  //rotateX(radians(-70));
  //TEXT
  pushMatrix();
  
  translate(0,camDist - 800, camHeight + 230); //coloca o texto logo acima do campo de visão "dentro do frustrum"
  pushMatrix();
  //rotateZ(radians(3));
  rotateX(radians(-85)); //rotaciona o texto, o pushMatrix faz com que seja no próprio eixo
  for (int i = 0; i < menu.length; i++) {
    if (i == selectedMenuItem) {
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(32);
      textAlign(RIGHT, CENTER);
      text(menu[i], -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 0, 1);
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConLight);
      textSize(32);
      textAlign(LEFT, CENTER);
      text(menuVal(i).s, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 0, 1);
      appendMenu(i);
    } else {
      fill(0, 0, int(600*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menu[i], -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 0, 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).s, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 0, 1);
    }
    

    
    /*
    textAlign(RIGHT, BASELINE);
    text(menu[i], -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i - (textWidth(menu[i]) + textWidth(menuVal(i)))/2, 0, 0);
    textAlign(LEFT, BASELINE);
    text(menuVal(i), -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i - (textWidth(menu[i]) + textWidth(menuVal(i)))/2, 0, 0);
    */

  }
  popMatrix();
  popMatrix();
}

void appendMenu(int i){
  if(menuVal(i).x != null && menuVal(i).xVal != null){
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).x, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50, 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).xVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50, 1);
      fill(0,0,0,int(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55, 500, 36, 10);
    }
  if(menuVal(i).q != null && menuVal(i).qVal != null){
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).q, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+36, 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).qVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+36, 1);
      fill(0,0,0,int(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+36, 500, 36, 10);

    }

      if(menuVal(i).b != null && menuVal(i).bVal != null){
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).b, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+(36*2), 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).bVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+(36*2), 1);
      fill(0,0,0,int(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+(36*2), 500, 36, 10);
    }
      if(menuVal(i).t != null && menuVal(i).tVal != null){
      fill(0, 0, int(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).t, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+(36*3), 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).tVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+(36*3), 1);
      fill(0,0,0,int(400*menuOpa)); //retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+(36*3), 500, 36, 10);
    }

}

Buttons menuVal(int menuItem){
  //String s = "";
  Buttons menuText = new Buttons();
  switch(menuItem){

    case 0:
    if(listaMusicas[songPlaying].name == ""){
      menuText.s = meta.title() + " - " + meta.author();//name.listaMusicas[songPlaying];//fileNames[songPlaying];
    }
    else{
      menuText.s = listaMusicas[songPlaying].name;
    }

      menuText.x = "POSITION ";       //      songs[songPlaying].position()
      menuText.q = "CUT BEGINS AT ";
      
      menuText.b = "ENDS AT ";
      menuText.t = "";

      _cutLengthMins = floor((listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())/60000);
      _cutLengthSecs = floor(((listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())%60000)/1000);
      _cutLengthMillis = floor((listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())%1000);

      if(curated){
        _positionMins = floor(((songs[songPlaying].position() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length()))/60000);
        _positionSecs = floor(((songs[songPlaying].position() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())%60000)/1000);
        _positionMillis = floor((songs[songPlaying].position() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())%1000);
      }
      else{
        _positionMins = floor(songs[songPlaying].position()/60000);
        _positionSecs = floor((songs[songPlaying].position()%60000)/1000);
        _positionMillis = floor(songs[songPlaying].position()%1000);
      }

      _lengthMins = floor(songs[songPlaying].length()/60000);
      _lengthSecs = floor((songs[songPlaying].length()%60000)/1000);
      _lengthMillis = floor(songs[songPlaying].length()%1000);  

      _startMins = floor(listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length()/60000);
      _startSecs = floor((listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length()%60000)/1000);
      _startMillis = floor(listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length()%1000);

      _endMins = floor(listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length()/60000);
      _endSecs = floor((listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length()%60000)/1000);
      _endMillis = floor(listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length()%1000);

      positionMins = str(_positionMins);
      positionSecs = str(_positionSecs);
      positionMillis = str(_positionMillis); 

      lengthMins = str(_lengthMins);
      lengthSecs = str(_lengthSecs);
      lengthMillis = str(_lengthMillis); 

      cutLengthMins = str(_cutLengthMins);
      cutLengthSecs = str(_cutLengthSecs);
      cutLengthMillis = str(_cutLengthMillis); 

      startMins = str(_startMins);
      startSecs = str(_startSecs);
      startMillis = str(_startMillis);

      endMins = str(_endMins);
      endSecs = str(_endSecs);
      endMillis = str(_endMillis);

      if(_lengthMins < 10) lengthMins = "0" + lengthMins;
      if(_lengthSecs < 10) lengthSecs = "0" + lengthSecs;
      if(_lengthMillis < 10) lengthMillis = "0" + lengthMillis;
      if(_lengthMillis < 100) lengthMillis = "0" + lengthMillis;

      if(_cutLengthMins < 10) cutLengthMins = "0" + cutLengthMins;
      if(_cutLengthSecs < 10) cutLengthSecs = "0" + cutLengthSecs;
      if(_cutLengthMillis < 10) cutLengthMillis = "0" + cutLengthMillis;
      if(_cutLengthMillis < 100) cutLengthMillis = "0" + cutLengthMillis;

      if(_positionMins < 10) positionMins = "0" + positionMins;
      if(_positionSecs < 10) positionSecs = "0" + positionSecs;
      if(_positionMillis < 10) positionMillis = "0" + positionMillis;
      if(_positionMillis < 100) positionMillis = "0" + positionMillis;

      if(_startMins < 10) startMins = "0" + startMins;
      if(_startSecs < 10) startSecs = "0" + startSecs;
      if(_startMillis < 10) startMillis = "0" + startMillis;
      if(_startMillis < 100) startMillis = "0" + startMillis;

      if(_endMins < 10) endMins = "0" + endMins;
      if(_endSecs < 10) endSecs = "0" + endSecs;
      if(_endMillis < 10) endMillis = "0" + endMillis;
      if(_endMillis < 100) endMillis = "0" + endMillis;

      if(curated){
        menuText.xVal = positionMins + ":" + positionSecs + " out of " + 
                          cutLengthMins + ":" + cutLengthSecs;
      }
      else{
        menuText.xVal = positionMins + ":" + positionSecs + " out of " + 
                          lengthMins + ":" + lengthSecs;
      }


      menuText.qVal = startMins + ":" + startSecs + ":" + startMillis + "  |  " + 
                        float(round(listaMusicas[songPlaying].cutP[0]*10000))/100 //manter 2 casas decimais
                         +"%";
      
      menuText.bVal = endMins + ":" + endSecs + ":" + endMillis + "  |  " + 
                        float(round(listaMusicas[songPlaying].cutP[1]*10000))/100 //manter 2 casas decimais
                         +"%";
      menuText.tVal = "IT'S " + float(round((
        (listaMusicas[songPlaying].cutP[1]*songs[songPlaying].length() - listaMusicas[songPlaying].cutP[0]*songs[songPlaying].length())
        /songs[songPlaying].length())*10000))/100 + 
        "% OF THE ORIGINAL LENGTH.";
        break;

    case 1:
      if(freqMode){
        menuText.s = "IS LOGARIHMIC"; }
      else{
        menuText.s = "IS LINEAR";}

      menuText.x = "HIGH PASS ";
      menuText.q = "LOW PASS ";
      
      menuText.b = "";
      menuText.t = "";

      menuText.xVal = str(xHighPass);
      menuText.qVal = str(xLowPass);
      
      menuText.bVal = "";
      menuText.tVal = "";


        break;
    case 2:
      if(ampMode){
        menuText.s = "IS IN dB"; }
      else{
        menuText.s = "IS NOW LINEAR";}
      menuText.x = "CONSTRAINTS ";
      menuText.q = "CONSTRAINT LEVEL";
      
      menuText.b = "EXPECTED DINAMIC RANGE ";
      menuText.t = "GAIN ";

      menuText.xVal = constrainOn ? "ON" : "OFF";
      menuText.qVal = str(compZ);
      
      menuText.bVal = str(expZ);
      menuText.tVal = str(gain);

      break;
    case 3:
      if(weightingMode){
        menuText.s = "ON"; }
      else{
        menuText.s = "OFF";}
      break;
    case 4:
      if(zOffset){
        menuText.s = "ON"; }
      else{
        menuText.s = "OFF";}
      break;
    case 5:
      if(drawMode == 0){
        menuText.s = "SPHERE CLOUD"; }
      else if(drawMode == 1){
        menuText.s = "BOXES";}
      else if(drawMode == 2){
        menuText.s = "QUADS";}
      else if(drawMode == 3){
        menuText.s = "TRIANGLES";}
      else if(drawMode == 4){
        menuText.s = "F MODE";}
      else if(drawMode == 5){
        menuText.s = "T MODE";}
      else if(drawMode == 6){
        menuText.s = "MESH";}
      else{
        menuText.s = "P";
      }

      menuText.x = "SIZE ";
      menuText.q = "";
      
      menuText.b = "";
      menuText.t = "";

      menuText.xVal = str(drawSize*10);
      if(drawSize >16) menuText.xVal = "MAX";
      menuText.qVal = "";
      
      menuText.bVal = "";
      menuText.tVal = "";
      break;
    case 6:
      if(viewMode){
        menuText.s = "PERSPECTIVE"; }
      else{
        menuText.s = "ORTHO";}
      break;
    case 7:
      if(hueMap){
         menuText.s = "AS FUNCTION OF FREQUENCY"; 
         menuText.x = "START HUE ";
         menuText.q = "END HUE ";
         
         menuText.b = "SIGNAL LOW ";
         menuText.t = "SIGNAL HIGH ";
    
         menuText.xVal = str(startHue);
         menuText.qVal = str(endHue);
         
         menuText.bVal = "";//str(lowHueMap);
         menuText.tVal = "";//str(highHueMap);
       }
      else{
         menuText.s = "DOESNT CHANGE";}
       break;
    case 8:
       if(satMap){
         menuText.s = "PROPORTIONAL TO INTENSITY"; 
         menuText.x = "START SAT ";
         menuText.q = "END SAT ";
         
         menuText.b = "SIGNAL LOW";
         menuText.t = "SIGNAL HIGH";

         menuText.xVal = str(startSatMap);
         menuText.qVal = str(endSatMap);
         
         menuText.bVal = str(lowSatMap);
         menuText.tVal = str(highSatMap);
       }
       else{
         menuText.s = "ZEROED";}
       break;
    case 9:
       if(briMap){
         menuText.s = "DECAYS WITH DEPTH"; 
         menuText.x = "START BRIGHTNESS ";
         menuText.q = "END BRIGHTNESS ";
         
         menuText.b = "SIGNAL LOW ";
         menuText.t = "SIGNAL HIGH ";
   
         menuText.xVal = str(startBri);
         menuText.qVal = str(endBri);
         
         menuText.bVal = "";//str(lowBriMap);
         menuText.tVal = "";//str(highBriMap);
       }
       else{
         menuText.s = "DOESNT CHANGE";}
       break;
   case 10:
       if(opaMap){
         menuText.s = "FADES WITH LOWER SIGNALS"; 
         menuText.x = "START OPACITY ";
         menuText.q = "END OPACITY ";
         
         menuText.b = "SIGNAL LOW ";
         menuText.t = "SIGNAL HIGH ";
   
         menuText.xVal = str(startOpaMap);
         menuText.qVal = str(endOpaMap);
         
         menuText.bVal = str(lowOpaMap);
         menuText.tVal = str(highOpaMap);
       }
       else{
         menuText.s = "IS 100%";}
       break;

    default:
      break;
  }
//return s;
return menuText;

}
