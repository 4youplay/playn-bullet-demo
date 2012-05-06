package com.googlecode.playnbulletdemo.core.gl;

import com.bulletphysics.demos.opengl.IGL;

public class Cube {

  public static Tesselator buildTexturedCube() {
    Tesselator t = new Tesselator(4 * 6);
    final float extent = 1.f;
    t.begin(IGL.GL_QUADS);
    t.normal3f(1f, 0f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(+extent, -extent, +extent);
    t.texCoord2f(1, 0);
    t.vertex3f(+extent, -extent, -extent);
    t.texCoord2f(1, 1);
    t.vertex3f(+extent, +extent, -extent);
    t.texCoord2f(0, 1);
    t.vertex3f(+extent, +extent, +extent);
  
    t.normal3f(0f, 1f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(+extent, +extent, +extent);
    t.texCoord2f(1, 0);
    t.vertex3f(+extent, +extent, -extent);
    t.texCoord2f(1, 1);
    t.vertex3f(-extent, +extent, -extent);
    t.texCoord2f(0, 1);
    t.vertex3f(-extent, +extent, +extent);
  
    t.normal3f(0f, 0f, 1f);
    t.texCoord2f(0, 0);
    t.vertex3f(+extent, +extent, +extent);
    t.texCoord2f(1, 0);
    t.vertex3f(-extent, +extent, +extent);
    t.texCoord2f(1, 1);
    t.vertex3f(-extent, -extent, +extent);
    t.texCoord2f(0, 1);
    t.vertex3f(+extent, -extent, +extent);
  
    t.normal3f(-1f, 0f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(-extent, -extent, +extent);
    t.texCoord2f(1, 0);
    t.vertex3f(-extent, +extent, +extent);
    t.texCoord2f(1, 1);
    t.vertex3f(-extent, +extent, -extent);
    t.texCoord2f(0, 1);
    t.vertex3f(-extent, -extent, -extent);
  
    t.normal3f(0f, -1f, 0f);
    t.texCoord2f(0, 0);
    t.vertex3f(-extent, -extent, +extent);
    t.texCoord2f(1, 0);
    t.vertex3f(-extent, -extent, -extent);
    t.texCoord2f(1, 1);
    t.vertex3f(+extent, -extent, -extent);
    t.texCoord2f(0, 1);
    t.vertex3f(+extent, -extent, +extent);
  
    t.normal3f(0f, 0f, -1f);
    t.texCoord2f(0, 0);
    t.vertex3f(-extent, -extent, -extent);
    t.texCoord2f(1, 0);
    t.vertex3f(-extent, +extent, -extent);
    t.texCoord2f(1, 1);
    t.vertex3f(+extent, +extent, -extent);
    t.texCoord2f(0, 1);
    t.vertex3f(+extent, -extent, -extent);
    return t;
  }

}
