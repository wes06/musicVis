float freq;

float Ra;

float[] dBaVal;

float dBa (float freq, boolean linear) {
  float valA = pow( 12200, 2);
  float valB = pow( 20.6, 2);
  float valC = pow( 107.7, 2);
  float valD = pow( 737.9, 2);
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

void getAWeightingCurve(int freqRange){
  for (int i = 0; i < freqRange; i++) {
    // dBaVal[i] = dBa((map(i, 0, freqRange, 0, fft.specSize())/4096)*songs[songPlaying].sampleRate(), false);
  }
}

float log10 (float x) {
  return (log(x) / log(10));
}

