package com.googlecode.playnbulletdemo.core.gl;

import com.googlecode.playnbulletdemo.core.gl.MeshBuilder.Mode;

public class Shapes {

  public static MeshBuilder cube(MeshBuilder t) {
    Mode mode = t.getMode();
    t.setMode(MeshBuilder.Mode.QUADS);
    t.normal3f(1f, 0f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(+1, -1, +1);
    t.texCoord2f(1, 0);
    t.vertex3f(+1, -1, -1);
    t.texCoord2f(1, 1);
    t.vertex3f(+1, +1, -1);
    t.texCoord2f(0, 1);
    t.vertex3f(+1, +1, +1);
  
    t.normal3f(0f, 1f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(+1, +1, +1);
    t.texCoord2f(1, 0);
    t.vertex3f(+1, +1, -1);
    t.texCoord2f(1, 1);
    t.vertex3f(-1, +1, -1);
    t.texCoord2f(0, 1);
    t.vertex3f(-1, +1, +1);
  
    t.normal3f(0f, 0f, 1f);
    t.texCoord2f(0, 0);
    t.vertex3f(+1, +1, +1);
    t.texCoord2f(1, 0);
    t.vertex3f(-1, +1, +1);
    t.texCoord2f(1, 1);
    t.vertex3f(-1, -1, +1);
    t.texCoord2f(0, 1);
    t.vertex3f(+1, -1, +1);
  
    t.normal3f(-1f, 0f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(-1, -1, +1);
    t.texCoord2f(1, 0);
    t.vertex3f(-1, +1, +1);
    t.texCoord2f(1, 1);
    t.vertex3f(-1, +1, -1);
    t.texCoord2f(0, 1);
    t.vertex3f(-1, -1, -1);
  
    t.normal3f(0f, -1f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(-1, -1, +1);
    t.texCoord2f(1, 0);
    t.vertex3f(-1, -1, -1);
    t.texCoord2f(1, 1);
    t.vertex3f(+1, -1, -1);
    t.texCoord2f(0, 1);
    t.vertex3f(+1, -1, +1);
  
    t.normal3f(0f, 0f, -1f);
    t.texCoord2f(0, 0);
    t.vertex3f(-1, -1, -1);
    t.texCoord2f(1, 0);
    t.vertex3f(-1, +1, -1);
    t.texCoord2f(1, 1);
    t.vertex3f(+1, +1, -1);
    t.texCoord2f(0, 1);
    t.vertex3f(+1, -1, -1);
    
    t.setMode(mode);
    return t;
  }

  public static MeshBuilder cylinder(MeshBuilder mb, float baseRadius, float topRadius, float height, int slices) {
    final float da = (float) (2.0f * Math.PI / slices);
    float x;
    float y;
  
    Mode saved = mb.getMode();
    mb.setMode(MeshBuilder.Mode.TRIANGLE_STRIP);
    for (int i = 0; i <= slices; i++) {
      if (i == slices) {
        x = (float) Math.sin(0.0f);
        y = (float) Math.cos(0.0f);
      } else {
        x = (float) Math.sin((i * da));
        y = (float) Math.cos((i * da));
      }
      mb.vertex3f(x * baseRadius, y * baseRadius, 0);
      mb.vertex3f(x * topRadius, y * topRadius, height);
    } 
    mb.setMode(saved);
    return mb;
  }

  public static MeshBuilder disk(MeshBuilder mb, float radius, int slices) {
    final float da = (float) (2.0f * Math.PI / slices);
    float x;
    float y;
  
    Mode saved = mb.getMode();
    mb.setMode(MeshBuilder.Mode.TRIANGLE_FAN);
    mb.vertex3f(0, 0, 0);
    for (int i = 0; i <= slices; i++) {
      if (i == slices) {
        x = (float) Math.sin(0.0f);
        y = (float) Math.cos(0.0f);
      } else {
        x = (float) Math.sin((i * da));
        y = (float) Math.cos((i * da));
      }
      mb.vertex3f(x * radius, y * radius, 0);
    }
    mb.setMode(saved);
    return mb;
  }
  
}
