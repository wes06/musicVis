import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 
import org.gamecontrolplus.gui.*; 
import org.gamecontrolplus.*; 
import net.java.games.input.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PCS2520_trabFinal07 extends PApplet {



int largura = 1400;
int altura = 600;

float fov = (PI/3.0f);
float cameraZ = (height/2.0f) / tan(fov/2.0f); 




Minim       minim;
FFT         fft;

AudioMetaData meta;

final static String[] fileNames = {
  "coward.wav",
  "mas.mp3",
  "ppl.mp3",
  "gerclap.mp3",
  "aero.mp3",
  "dentaku.mp3",
  "radio.mp3", 
  "music.mp3", 
  "evil.mp3", 
  "grillwalker.mp3",
  "ziq.mp3",
  "ashes.mp3"
};


final static boolean[] doPlay = {
 true, //"coward.wav",
 true, //"mas.mp3",
 true, //"ppl.mp3",
 true, //"gerclap.mp3",
 true, //"aero.mp3",
 true, //"dentaku.mp3",
 false, //"radio.mp3", 
 false, //"music.mp3", 
 false, //"evil.mp3", 
 false, //"grillwalker.mp3",
 true, //"ziq.mp3",
 true //"ashes.mp3"
};

final static int numSongs  = fileNames.length;

final static AudioPlayer[] songs = new AudioPlayer[numSongs];

int songPlaying = 0;

float drift;

int boot = 0;


boolean useDualshock = true;
boolean desEixos = false;

int drawSize = 1;



public void setup() {
  size(largura, altura, P3D);
  rectMode(CENTER);
  fontes();
  dualshockInit();
  minim = new Minim(this);

  for ( byte idx = 0; idx != numSongs; songs[idx] = minim.loadFile( fileNames[idx++]/* + ".mp3"*/, 4096*1) );

  //songs[10] = minim.loadFile("coward.wav", 4096*1);
  //songs[10] = minim.loadFile("mas.mp3", 4096*1);
  //songs[10] = minim.loadFile("ppl.mp3", 4096*1);
  //songs[10] = minim.loadFile("gerclap.mp3", 4096*1);
  //songs[10] = minim.loadFile("aero.mp3", 4096*1);
  //songs[10] = minim.loadFile("dentaku.mp3", 4096*1);
  //songs[10] = minim.loadFile("radio.mp3", 4096*1);
  //songs[10] = minim.loadFile("music.mp3", 4096*1);
  //songs[10] = minim.loadFile("evil.mp3", 4096*1);
  //songs[10] = minim.loadFile("grillwalker.mp3", 4096*1);
  //songs[10] = minim.loadFile("ziq.mp3", 4096*1);

  //songs[10].play(440000);
  //songs[songPlaying].play(0);
  curatedSongs();
  changeMusic(songPlaying, listaMusicas[songPlaying].cutP[0], listaMusicas[songPlaying].cutP[1]);

  fft = new FFT(songs[songPlaying].bufferSize(), songs[songPlaying].sampleRate());
  
  getAWeightingCurve(pontosX);

}



public void changeMusic(int _musicNum, float _startPercentage, float _endPercentage){
  for(int i = 0; i< fileNames.length;i++){
    //if (songs[i].isPlaying()){
        songs[i].rewind();
        songs[i].pause();
    //}
  }

    songs[_musicNum].setLoopPoints(PApplet.parseInt(_startPercentage*songs[_musicNum].length()), PApplet.parseInt(_endPercentage*songs[_musicNum].length()));
    songs[_musicNum].loop(1);
    meta = songs[_musicNum].getMetaData();
}






public void draw() {
  //println(dBa(2/(4096*2*(songs[10].sampleRate()/8)),false));
  drift+=300;
  if (drift>1000) { 
    drift = 0;
  };



  checkDualshock();

  background(0);
  graphics();//define camera, perspectiva, etc

  luzes(); //ativa as luzes
  axis(); //desenha os eixos XYZ na pos 0,0,0
  data(); //m\u00fasica + filtros para n\u00favem
  if(drawMode == 0) { pontos("SPHERE", drawSize); }// desenha a nuvem de pontos
  if(drawMode == 1) { pontos("BOX", drawSize); }
  if(drawMode == 2) { pontos("SQUARE", drawSize); }
  if(drawMode == 3) { pontos("TRIANGLE", drawSize); }
  if(drawMode == 4) { waterfall1(drawSize); }
  if(drawMode == 5) { waterfall(drawSize); }
  if(drawMode == 6) { waterfall(drawSize);waterfall1(drawSize); }
  if(drawMode == 7) { /*quads();*/ }
  //waterfall(); // 1 linha = momento
  //waterfall1(); // 1 linha = 1 frequencia
  menu(); // desenha o menu
  

  if(!songs[songPlaying].isPlaying()){
    songPlaying++;
    if(songPlaying > fileNames.length - 1) songPlaying = 0;
    if(curated){
      changeMusic(songPlaying, listaMusicas[songPlaying].cutP[0], listaMusicas[songPlaying].cutP[1]);
    }
    else{
      changeMusic(songPlaying, 0, 1);
    }
  }
  //println(dualshock.getButton("Q").pressed());
  //println(dualshock.getSlider("XPOS").getValue());
}



public void axis() {
  if (desEixos) {
    //eixos
    fill(0, 1000, 1000, 800);
    box(500, 20, 20);
    fill(300, 1000, 1000, 800);
    box(20, 20, 500);
    fill(600, 1000, 1000, 800);
    box(20, 500, 20);
  }
}

public void graphics() {
  //noStroke();
  
  if(viewMode){
    camera(0, camDist, camHeight, 0, 0, viewCenter, 0, 1, 0);
    perspective();
  }
  else{
    //camera();
    //camera(0, 0, 4000, 0, 0, 0, 0, 1, 0);
    //frustum(-100, 0, 0, 10, 10, 2000);
    //println(offsetX +" "+ offsetY);
    //ortho();
  }
  //ortho();
  //perspective(fov, float(largura)/float(altura), cameraZ/10.0, cameraZ*10.0);
  
  sphereDetail(3);
  colorMode(HSB, 1000);
  frameRate(60);
}


public void stop() {
 // for ( AudioPlayer songs[10]: musica )  songs[10].close();
  minim.stop();
  super.stop();
}
float freq;

float Ra;

float[] dBaVal;

public float dBa (float freq, boolean linear) {
  float valA = pow( 12200, 2);
  float valB = pow( 20.6f, 2);
  float valC = pow( 107.7f, 2);
  float valD = pow( 737.9f, 2);
  float valE = pow( freq, 2);

  float part1 = valA * pow(valE, 2);
  float part2 = valE + valB;
  float part3 = valE + valA;
  float part4 = sqrt(valE + valC);
  float part5 = sqrt(valE + valD);

  Ra = part1 / (part2*part3*part4*part5);
if(linear) return (Ra);
else    return (20*log10(Ra));
}

public void getAWeightingCurve(int freqRange){
  for (int i = 0; i < freqRange; i++) {
    // dBaVal[i] = dBa((map(i, 0, freqRange, 0, fft.specSize())/4096)*songs[songPlaying].sampleRate(), false);
  }
}

public float log10 (float x) {
  return (log(x) / log(10));
}

public void data() {
  fft.forward(songs[songPlaying].mix);

  //fft.specSize()
  //println(fft.getBandWidth());

  //cada coluna(i) da ultima linha(rows-1) recebe o valor de ampltide de uma faixa de frequencia do FFT
  for (int i = 0; i < fft.specSize (); i++) {
    myArray[i][rows-1] = fft.getBand(i);
  }
  /*for (int i = 0; i < fft.specSize (); i++) {
  myArray[i][rows-1] = getFreq(float freq);
  }*/
  //map(i,0,cols,0,pontosX);

  //cada coluna(i) de uma linha(j-1) recebe o valor da mesma coluna(i) na pr\u00f3xima linha(j-1)
  for (int i = cols-1; i > 0; i--) {
    for (int j = 1; j < rows; j++) {
      myArray[i][j-1] = myArray[i][j];
    }
  }
}

public void luzes() {
  lights();

  //luz do menu
  pointLight(0, 0, 800,0,camDist - 720, camHeight + 230);
  //ambientLight(255,255,255);
  for (int i = 0; i<2; i++) {
  	//pointLight(0, 0, 1000, -1000, 1000, 500);
  }
  //spotLight(250, 250, 250, 400, 400, 400, 0, 1, 0, PI/16, 600);
  //spotLight(v1, v2, v3, x, y, z, nx, ny, nz, angle, concentration)
}


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

public void curatedSongs(){
  for (int i = 0; i < listaMusicas.length; i++) {
    listaMusicas[i] = new Song();
  }

  //nome secund\u00e1rio
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
  
  //cortes da m\u00fasica
  listaMusicas[0].cutP = new float [] {0.845f,1};
  listaMusicas[1].cutP = new float [] {0.0f,1};
  listaMusicas[2].cutP = new float [] {0.05f,1};
  listaMusicas[3].cutP = new float [] {0.85f,1};
  listaMusicas[4].cutP = new float [] {0.62355f,1};
  listaMusicas[5].cutP = new float [] {0.32355f,1};
  listaMusicas[6].cutP = new float [] {0.32355f,1};
  listaMusicas[7].cutP = new float [] {0.32355f,1};
  listaMusicas[8].cutP = new float [] {0.32355f,1};
  listaMusicas[9].cutP = new float [] {0.32355f,1};
  listaMusicas[10].cutP = new float [] {0.82355f,1};
  listaMusicas[11].cutP = new float [] {0.08355f,1};

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

public void fontes() {
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


public void menu() {
    noStroke();
    //menu.length
    //decide a posi\u00e7\u00e3o final onde o menu tem de estar
    menuItemXPositionTarget = menuItemAvgLength*(selectedMenuItem) + selectedMenuItem*menuItemAvgMargin;
    
    //anima o menu para aquela posi\u00e7\u00e3o
    if(menuItemXPosition > menuItemXPositionTarget + menuSpeed){ // "+ menuSpeed" permite certa margem de histerese para que o menu n\u00e3o fique pulando caso "menuSpeed" seja um valor quebrado
      menuItemXPosition -= menuSpeed;
    }
    else if (menuItemXPosition < menuItemXPositionTarget - menuSpeed) {
      menuItemXPosition += menuSpeed;
    }
    else if(menuItemXPosition > menuItemXPositionTarget){// modo gambiarra de desacelerar, al\u00e9m de fazer com que os itens consigam ficar em intervalos menores do que menuSpeed
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
    if(menuOpa<1) menuOpa +=.5f;
  }
  else if (!menuActive) {
    if(menuOpa>0.01f) menuOpa -=.05f;
  }

  //se estiver iniciando o programa, n\u00e3o animar o menu
  if(boot==0) {menuItemXPosition = menuItemXPositionTarget; boot = 1;}

  //rotateX(radians(-70));
  //TEXT
  pushMatrix();
  
  translate(0,camDist - 800, camHeight + 230); //coloca o texto logo acima do campo de vis\u00e3o "dentro do frustrum"
  pushMatrix();
  //rotateZ(radians(3));
  rotateX(radians(-85)); //rotaciona o texto, o pushMatrix faz com que seja no pr\u00f3prio eixo
  for (int i = 0; i < menu.length; i++) {
    if (i == selectedMenuItem) {
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(32);
      textAlign(RIGHT, CENTER);
      text(menu[i], -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 0, 1);
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConLight);
      textSize(32);
      textAlign(LEFT, CENTER);
      text(menuVal(i).s, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 0, 1);
      appendMenu(i);
    } else {
      fill(0, 0, PApplet.parseInt(600*menuOpa), 1000);
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

public void appendMenu(int i){
  if(menuVal(i).x != null && menuVal(i).xVal != null){
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).x, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50, 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).xVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50, 1);
      fill(0,0,0,PApplet.parseInt(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55, 500, 36, 10);
    }
  if(menuVal(i).q != null && menuVal(i).qVal != null){
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).q, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+36, 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).qVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+36, 1);
      fill(0,0,0,PApplet.parseInt(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+36, 500, 36, 10);

    }

      if(menuVal(i).b != null && menuVal(i).bVal != null){
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).b, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+(36*2), 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).bVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+(36*2), 1);
      fill(0,0,0,PApplet.parseInt(400*menuOpa));//retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+(36*2), 500, 36, 10);
    }
      if(menuVal(i).t != null && menuVal(i).tVal != null){
      fill(0, 0, PApplet.parseInt(1000*menuOpa), 1000);
      textFont(rbtConBold);
      textSize(24);
      textAlign(RIGHT, CENTER);
      text(menuVal(i).t, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 50+(36*3), 1);
      textFont(rbtConLight);
      textSize(24);
      textAlign(LEFT, CENTER);
      text(menuVal(i).tVal, -menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i + 20, 50+(36*3), 1);
      fill(0,0,0,PApplet.parseInt(400*menuOpa)); //retangulo preto atras do texto com opacidade para melhorar legibilidade
      rect(-menuItemXPosition + menuItemAvgLength*i + menuItemAvgMargin*i, 55+(36*3), 500, 36, 10);
    }

}

public Buttons menuVal(int menuItem){
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
                        PApplet.parseFloat(round(listaMusicas[songPlaying].cutP[0]*10000))/100 //manter 2 casas decimais
                         +"%";
      
      menuText.bVal = endMins + ":" + endSecs + ":" + endMillis + "  |  " + 
                        PApplet.parseFloat(round(listaMusicas[songPlaying].cutP[1]*10000))/100 //manter 2 casas decimais
                         +"%";
      menuText.tVal = "IT'S " + PApplet.parseFloat(round((
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
//tamanho da area da nuvem de pontos
int compX = 2000;
int compY = 2000;
int compZ = 500;

//posicao da camera
int camDist = PApplet.parseInt(compY/2) + 1000;
int camHeight = compZ + 100;

//altura para a qual a camera vai olhar (compZ/2 = meio da altura m\u00e1xima da nuvem)
int viewCenter = compZ/2;

float x;
float x1;
float y;
float z;
float z1;

//variaveis das posicoes de offset da nuvem
int offsetX = 0;
int offsetY = 0;
int offsetZ = 0;

//variaveis da rotacao da nuvem
int rotationY = 0;
int rotationX = 0;
int rotationZ = 0;

//qtd de pontos a serem desenhados 
int pontosX = 500;//2049;
int pontosY = 30;

//colunas de dados a serem usados
int cols = 2048*1+1;//1029;// = 256;
int rows = pontosY;
int expZ = 300; //dynamic range esperado

//filtros, highpass = abaixo disso \u00e9 escondido, lowpass = acima disso \u00e9 escondido 
int xHighPass = 0;
int xLowPass = 0;//800; // 0~100% = 0~1029 




boolean satMap = true;
boolean briMap = true;
boolean hueMap = true;
boolean opaMap = true;
boolean constrainOn = true;

int startHue = 0;
int endHue = 1000;

int startBri = 500;
int endBri = 1000;

int lowSatMap = 40;//70;
int highSatMap = 100;
int startSatMap = 300;
int endSatMap = 1000;
int lowOpaMap = 30;//70;
int highOpaMap = 100;
int startOpaMap = 200;
int endOpaMap = 1000;

int gain = 1;


float[][] myArray = new float[cols][rows];

//cor
int hue, bri, sat, opa;


public void pontos(String _modo, int _tamanho) {

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre d\u00e1 0 e acaba ficando uma linha inativa)
  for (int i = 1+xHighPass; i < pontosX-xLowPass; i++) {
    for (int j = 0; j < pontosY; j++) {

      pushMatrix();
      offsetAndRotate();
      applyTransformations(i,j);
      translate(
      x, //a largura total \u00e9 fixa(compX), os pontos s\u00e3o distribuidos a cada 1*i, de 0 a 100% de compX(i/pontosX)
      (j-pontosY)*PApplet.parseInt(compY/pontosY) + compY/2,
      z
      );
      //println(dBa(i/(4096*2*(songs[10].sampleRate()/8)),false)*myArray[i][j]*int(compZ/expZ));
      //println(log10(compX*(i-xHighPass))*1/(pontosX-xLowPass-xHighPass) -compX/2); 
      defineColor(i,j);
      //stroke(hue, sat, bri, opa);
      //strokeWeight(1);
      noStroke();
      fill(hue, sat, bri, opa);
      //noFill();
      if( _modo == "SPHERE"){sphere(_tamanho*4);}
      else if( _modo == "BOX"){box(_tamanho*4);}
      else if( _modo == "SQUARE"){plane(_tamanho*8, 4);}
      else if( _modo == "TRIANGLE"){plane(_tamanho*8, 3);}
      
      //
      //
      popMatrix();
    }
  }
}





public void waterfall(int _tamanho) { // 1 linha = momento

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre d\u00e1 0 e acaba ficando uma linha inativa  
  for (int i = 1+xHighPass; i < pontosX-xLowPass-1; i++) {
    for (int j = 0; j < pontosY; j++) {

      pushMatrix();
      offsetAndRotate();
      applyTransformations(i,j);
      x1 = x; 
      z1 = z;
      applyTransformations(i+1,j);
      defineColor(i,j);
      //noStroke();
      stroke(hue, sat, bri, opa);
      //if(j > pontosY-3) strokeWeight(5);
      //else
       strokeWeight(_tamanho);
      //strokeColor(1000);
      //noFill();
      //fill(hue, sat, bri, opa);

      line(x, (j-pontosY)*PApplet.parseInt(compY/pontosY) + compY/2, z, x1, (j-pontosY)*PApplet.parseInt(compY/pontosY) + compY/2, z1);
      //x = x1;z = z1;
      //x1 = x; 
      //z1 = z;

      //box(8);
      //sphere(8);
      popMatrix();
    }
  }
}

public void waterfall1(int _tamanho) { // 1 linha = frequencia

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre d\u00e1 0 e acaba ficando uma linha inativa  
  for (int i = 1+xHighPass; i < pontosX-xLowPass; i++) {
    for (int j = 0; j < pontosY-1; j++) {

      pushMatrix();
      offsetAndRotate();
      applyTransformations(i,j);
      x1 = x; 
      z1 = z;
      applyTransformations(i,j+1);

      defineColor(i,j);
      stroke(hue, sat, bri, opa);
      strokeWeight(_tamanho);
      //fill(hue, sat, bri, opa);
      noFill();
      
      line(x, (j-pontosY+1)*PApplet.parseInt(compY/pontosY) + compY/2, z, x1, (j-pontosY)*PApplet.parseInt(compY/pontosY) + compY/2, z1);
      plane(20, 3);
      //box(8);
      //sphere(8);
      popMatrix();
    }
  }
}


/*

n\u00e3o \u00e9 usado


void quads() { 
  for (int i = 1+xHighPass; i < pontosX-xLowPass-1; i++) {
    for (int j = 0; j < pontosY-1; j++) {
      defineColor(i,j);
      noFill();
      strokeWeight(2);
      fill(hue, sat, bri, opa);

      pushMatrix();
      offsetAndRotate();

      beginShape();
      vertex(compX*(i-xHighPass)*1/(pontosX-xLowPass-xHighPass) -compX/2, (j-pontosY)*int(compY/pontosY) + compY/2, constrain(int(myArray[i][j]*int(compZ/expZ) - compZ/2 + offsetZ), 0, compZ));
      vertex(compX*(i-xHighPass+1)*1/(pontosX-xLowPass-xHighPass) -compX/2, (j-pontosY)*int(compY/pontosY) + compY/2, constrain(int(myArray[i+1][j]*int(compZ/expZ) - compZ/2 + offsetZ), 0, compZ));
      vertex(compX*(i-xHighPass+1)*1/(pontosX-xLowPass-xHighPass) -compX/2, (j-pontosY+1)*int(compY/pontosY) + compY/2, constrain(int(myArray[i+1][j+1]*int(compZ/expZ) - compZ/2 + offsetZ), 0, compZ));
      vertex(compX*(i-xHighPass)*1/(pontosX-xLowPass-xHighPass) -compX/2, (j-pontosY+1)*int(compY/pontosY) + compY/2, constrain(int(myArray[i][j+1]*int(compZ/expZ) - compZ/2 + offsetZ), 0, compZ));
      endShape(CLOSE);
      
      popMatrix();
    }
  }
}*/

public void plane(int _larg, int _lados){
  if(_lados == 4){
    beginShape();
      vertex(-_larg/2,_larg/2,0);
      vertex(_larg/2,_larg/2,0);
      vertex(_larg/2,-_larg/2,0);
      vertex(-_larg/2,-_larg/2,0);
    endShape(CLOSE);
  }
  
  if(_lados == 3){
    beginShape();
      vertex(-_larg/2,_larg/2,0);
      vertex(_larg/2,_larg/2,0);
      vertex(_larg/2,-_larg/2,0);
    endShape(CLOSE);
  }
}

public void applyTransformations(int _ci, int _cj){
      if(weightingMode){
        if(ampMode){
          //z = (/*dBaVal[ci] +*/ 20*log10(myArray[ci][cj]*compZ/expZ))*20;// - compZ/2 + offsetZ;

          z = (dBa((map(_ci, 0, pontosX, 0, fft.specSize())*2/4096)*songs[songPlaying].sampleRate(), false)*(-1) + 20*log10(myArray[_ci][_cj]*compZ/expZ))*10 ;
          //println(dBa((map(ci, 0, fft.specSize(), 0, pontosX)/4096)*songs[10].sampleRate(), false)+ 20*log10(myArray[ci][cj]*compZ/expZ)*10 + 100);
          //println("weighting=true e ampMode=true");
         }
        else z = dBa((map(_ci, 0, pontosX, 0, fft.specSize())*2/4096)*songs[songPlaying].sampleRate(), true)*myArray[_ci][_cj]*compZ/expZ - compZ/2 + offsetZ;
      //println(dBa((map(_ci, 0, pontosX, 0, fft.specSize())*2/4096)*songs[songPlaying].sampleRate(), true));
      }

      else{
        if(ampMode) z = log10(myArray[_ci][_cj]*PApplet.parseInt(compZ/expZ))*300 - compZ/2 + offsetZ;
        else {
          z = myArray[_ci][_cj]*PApplet.parseInt(compZ/expZ) - compZ/2 + offsetZ;
          
        }
      }

      if(ampMode) z = z + 20*log10(gain);
      else z = z*gain;

      if(freqMode) x = log10((map(_ci, 0, pontosX, 0, fft.specSize())/4096)*songs[songPlaying].sampleRate())*800 - compX/2 - 1400;
      else x = compX*(_ci-xHighPass)*1/(pontosX-xLowPass-xHighPass) - compX/2;
      
      if(zOffset) {
        if(constrainOn) z = constrain(z,0,compZ);
      }
      else z = 0;

}

public void defineColor(int _ci, int _cj){
      hue = PApplet.parseInt(map(_ci, 0, pontosX, startHue, endHue)); // cor proporcional a posicao horizontal
      bri = PApplet.parseInt(map(_cj, 0, pontosY, startBri, endBri)); 
      //sat = int(map(myArray[ci][cj], 7, 100, 300, 1000));
      //opa = int(map(myArray[ci][cj], 7, 50, 100, 1000));
      sat = PApplet.parseInt(map(myArray[_ci][_cj], lowSatMap, highSatMap, startSatMap, endSatMap));
      opa = PApplet.parseInt(map(myArray[_ci][_cj], lowOpaMap, highOpaMap, startOpaMap, endOpaMap));

      if(!satMap) sat = 0;
      if(!hueMap) hue = 0;
      if(!briMap) bri = 1000;
      if(!opaMap) opa = 1000;
      constrain(hue, 0, 1000); //map() de numeros positivos pode gerar numeros negativos, por ex: map(5,10,100,50,1000) = -2.777...
      constrain(sat, 0, 1000); //n\u00e3o testei fill() com valores negativos, mas achei saud\u00e1vel o constrain()
      constrain(bri, 0, 1000); 
      constrain(opa, 0, 1000);
}

public void offsetAndRotate(){
  translate(offsetX, offsetY,0);
  rotateX(radians(rotationX));
  //rotateY(radians(rotationY));
  rotateZ(radians(rotationZ));
}

  //necessario para o dualshock
   //necessario para o dualshock
   //necessario para o dualshock

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

public void dualshockInit() {
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
    L3x.setTolerance(0.08f);
    L3y.setTolerance(0.08f);
    R3x.setTolerance(0.08f);
    R3y.setTolerance(0.08f);
    L2a.setTolerance(0.08f);
    R2a.setTolerance(0.08f);
    //slider.getTolerance();

    //if(hat.down()){println("ha");}
    
  }
}

public boolean debounce(){
  return (millis() - lastPress> debounce) ? true : false;
}

public void checkDualshock() {
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
        boolean doLoop = true; // tentei fazer com "while" e "return" mas n\u00e3o estava funcionando, provavelmente por erro de sintaxe..
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

public void keyReleased() {
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
        boolean doLoop = true; // tentei fazer com "while" e "return" mas n\u00e3o estava funcionando, provavelmente por erro de sintaxe..
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




  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PCS2520_trabFinal07" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
