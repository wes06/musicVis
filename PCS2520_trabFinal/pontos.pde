//tamanho da area da nuvem de pontos
int compX = 2000;
int compY = 2000;
int compZ = 500;

//posicao da camera
int camDist = int(compY/2) + 1000;
int camHeight = compZ + 100;

//altura para a qual a camera vai olhar (compZ/2 = meio da altura máxima da nuvem)
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

//filtros, highpass = abaixo disso é escondido, lowpass = acima disso é escondido 
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


void pontos(String _modo, int _tamanho) {

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre dá 0 e acaba ficando uma linha inativa)
  for (int i = 1+xHighPass; i < pontosX-xLowPass; i++) {
    for (int j = 0; j < pontosY; j++) {

      pushMatrix();
      offsetAndRotate();
      applyTransformations(i,j);
      translate(
      x, //a largura total é fixa(compX), os pontos são distribuidos a cada 1*i, de 0 a 100% de compX(i/pontosX)
      (j-pontosY)*int(compY/pontosY) + compY/2,
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





void waterfall(int _tamanho) { // 1 linha = momento

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre dá 0 e acaba ficando uma linha inativa  
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

      line(x, (j-pontosY)*int(compY/pontosY) + compY/2, z, x1, (j-pontosY)*int(compY/pontosY) + compY/2, z1);
      //x = x1;z = z1;
      //x1 = x; 
      //z1 = z;

      //box(8);
      //sphere(8);
      popMatrix();
    }
  }
}

void waterfall1(int _tamanho) { // 1 linha = frequencia

  //"i=1" para ignorar a primeira coluna(o FFT quase sempre dá 0 e acaba ficando uma linha inativa  
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
      
      line(x, (j-pontosY+1)*int(compY/pontosY) + compY/2, z, x1, (j-pontosY)*int(compY/pontosY) + compY/2, z1);
      plane(20, 3);
      //box(8);
      //sphere(8);
      popMatrix();
    }
  }
}


/*

não é usado


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

void plane(int _larg, int _lados){
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

void applyTransformations(int _ci, int _cj){
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
        if(ampMode) z = log10(myArray[_ci][_cj]*int(compZ/expZ))*300 - compZ/2 + offsetZ;
        else {
          z = myArray[_ci][_cj]*int(compZ/expZ) - compZ/2 + offsetZ;
          
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

void defineColor(int _ci, int _cj){
      hue = int(map(_ci, 0, pontosX, startHue, endHue)); // cor proporcional a posicao horizontal
      bri = int(map(_cj, 0, pontosY, startBri, endBri)); 
      //sat = int(map(myArray[ci][cj], 7, 100, 300, 1000));
      //opa = int(map(myArray[ci][cj], 7, 50, 100, 1000));
      sat = int(map(myArray[_ci][_cj], lowSatMap, highSatMap, startSatMap, endSatMap));
      opa = int(map(myArray[_ci][_cj], lowOpaMap, highOpaMap, startOpaMap, endOpaMap));

      if(!satMap) sat = 0;
      if(!hueMap) hue = 0;
      if(!briMap) bri = 1000;
      if(!opaMap) opa = 1000;
      constrain(hue, 0, 1000); //map() de numeros positivos pode gerar numeros negativos, por ex: map(5,10,100,50,1000) = -2.777...
      constrain(sat, 0, 1000); //não testei fill() com valores negativos, mas achei saudável o constrain()
      constrain(bri, 0, 1000); 
      constrain(opa, 0, 1000);
}

void offsetAndRotate(){
  translate(offsetX, offsetY,0);
  rotateX(radians(rotationX));
  //rotateY(radians(rotationY));
  rotateZ(radians(rotationZ));
}

