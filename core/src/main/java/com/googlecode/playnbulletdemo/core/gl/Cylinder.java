package com.googlecode.playnbulletdemo.core.gl;

public class Cylinder {
  
  Tesselator tesselator;

  public Cylinder(float baseRadius, float topRadius, float height, int slices) {
    tesselator = new Tesselator(slices * 2 + 2);
    final float da = (float) (2.0f * Math.PI / slices);
    float x;
    float y;

    tesselator.begin(GL11.GL_TRIANGLE_STRIP);
    for (int i = 0; i <= slices; i++) {
      if (i == slices) {
        x = (float) Math.sin(0.0f);
        y = (float) Math.cos(0.0f);
      } else {
        x = (float) Math.sin((i * da));
        y = (float) Math.cos((i * da));
      }
      tesselator.vertex3f(x * baseRadius, y * baseRadius, 0);
      tesselator.vertex3f(x * topRadius, y * topRadius, height);
    } // for stacks
  }

  
  public void draw(GL11 gl, int options) {
    tesselator.draw(gl, options);
  }
}
