void data() {
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

  //cada coluna(i) de uma linha(j-1) recebe o valor da mesma coluna(i) na próxima linha(j-1)
  for (int i = cols-1; i > 0; i--) {
    for (int j = 1; j < rows; j++) {
      myArray[i][j-1] = myArray[i][j];
    }
  }
}

