void luzes() {
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


