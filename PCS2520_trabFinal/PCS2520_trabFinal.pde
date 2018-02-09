//   
//   PCS2520
//   
//   Prof Ricardo Nakamura
//   Prof Romero Tori
//   
//   Wesley Lee
//   

int largura = 1400;
int altura = 600;

float fov = (PI/3.0);
float cameraZ = (height/2.0) / tan(fov/2.0); 

import ddf.minim.analysis.*;
import ddf.minim.*;

Minim       minim;
FFT         fft;

AudioMetaData meta;

final static String[] fileNames = {
  //"coward.wav",
  //"mas.mp3",
  //"ppl.mp3",
  "ziq.mp3",
  "gerclap.mp3",
  //"dentaku.mp3",
  //"radio.mp3", 
  //"music.mp3", 
  //"evil.mp3", 
  //"grillwalker.mp3",
  
  //"ashes.mp3"
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


boolean useDualshock = false;
boolean desEixos = false;

int drawSize = 1;



void setup() {
  size(1400, 600, P3D);
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



void changeMusic(int _musicNum, float _startPercentage, float _endPercentage){
  for(int i = 0; i< fileNames.length;i++){
    //if (songs[i].isPlaying()){
        songs[i].rewind();
        songs[i].pause();
    //}
  }

    songs[_musicNum].setLoopPoints(int(_startPercentage*songs[_musicNum].length()), int(_endPercentage*songs[_musicNum].length()));
    songs[_musicNum].loop(1);
    meta = songs[_musicNum].getMetaData();
}






void draw() {
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
  data(); //música + filtros para núvem
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
      changeMusic(songPlaying, 0,  1);
    }
  }
  //println(dualshock.getButton("Q").pressed());
  //println(dualshock.getSlider("XPOS").getValue());
}



void axis() {
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

void graphics() {
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


void stop() {
 // for ( AudioPlayer songs[10]: musica )  songs[10].close();
  minim.stop();
  super.stop();
}