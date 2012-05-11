package com.googlecode.playnbulletdemo.core.gl;

import com.bulletphysics.demos.opengl.IGL;

public class Cube {

  Tesselator t = new Tesselator(4 * 6);
  
  public Cube() {
    t.begin(IGL.GL_QUADS);
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
  }

  public void draw(GL11 gl, int options) {
    if (!t.vbo) {
      t.convertToVbo(gl);
    }
    t.draw(gl, options);
  }
}
