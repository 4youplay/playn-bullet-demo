package com.googlecode.playnbulletdemo.core;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import playn.core.Image;
import playn.core.PlayN;
import playn.core.gl.GL20;

import com.googlecode.playnbulletdemo.core.bullet.IGL;
import com.googlecode.playnbulletdemo.core.gl.GL11;
import com.googlecode.playnbulletdemo.core.gl.GL11FixedFunctionEmulation;
import com.googlecode.playnbulletdemo.core.gl.GLU;
import com.googlecode.playnbulletdemo.core.gl.Tesselator;

public class IGLImpl implements IGL {

  GL11FixedFunctionEmulation gl;
  Tesselator tesselator = new Tesselator(1000);
  boolean tesselating;
  int fontTexture;
  Image fontImage;
  boolean fontLoaded;
  
  public IGLImpl(GL20 gl20, Image font) {
    gl = new GL11FixedFunctionEmulation(gl20);
    fontImage = font;
    
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
    byteBuffer.order(ByteOrder.nativeOrder());
    IntBuffer intBuffer = byteBuffer.asIntBuffer();
    gl.glGenTextures(1, intBuffer);
    fontTexture = intBuffer.get(0);
  }
  

  @Override
  public void glFrustum(double left, double right, double bottom, double top,
      double zNear, double zFar) {
    gl.glFrustumf((float) left, (float) right, (float) bottom, (float) top, (float) zNear, (float) zFar);
    
  }

  @Override
  public void gluLookAt(float eyex, float eyey, float eyez, float centerx,
      float centery, float centerz, float upx, float upy, float upz) {
    GLU.gluLookAt(gl, eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
  }

  @Override
  public void glViewport(int x, int y, int width, int height) {
    gl.glViewport(x, y, width, height);
  }

  @Override
  public void glPushMatrix() {
    gl.glPushMatrix();
  }

  @Override
  public void glPopMatrix() {
    gl.glPopMatrix();
  }

  @Override
  public void gluOrtho2D(float left, float right, float bottom, float top) {
    GLU.gluOrtho2D(gl, left, right, bottom, top);
  }

  @Override
  public void glScalef(float x, float y, float z) {
    gl.glScalef(x, y, z);
  }

  @Override
  public void glTranslatef(float x, float y, float z) {
    gl.glTranslatef(x, y, z);
  }

  @Override
  public void glColor3f(float red, float green, float blue) {
    if (tesselating) {
      tesselator.color3f(red, green, blue);
    } else {
      gl.glColor4f(red, green, blue, 1);
    }
  }

  @Override
  public void glClear(int mask) {
    gl.glClear(mask);
  }

  @Override
  public void glBegin(int mode) {
    tesselating = true;
    tesselator.begin(mode);
  }

  @Override
  public void glEnd() {
    tesselator.draw(gl);
    tesselating = false;
  }

  @Override
  public void glVertex3f(float x, float y, float z) {
    tesselator.vertex3f(x, y, z);
  }

  @Override
  public void glLineWidth(float width) {
    gl.glLineWidth(width);
  }

  @Override
  public void glPointSize(float size) {
    gl.glPointSize(size);
  }

  @Override
  public void glNormal3f(float nx, float ny, float nz) {
    tesselator.normal3f(nx, ny, nz);
  }

  @Override
  public void glMultMatrix(float[] m) {
    gl.glMultMatrixf(m, 0);
  }

  @Override
  public void drawCube(float extent) {
    extent = extent * 0.5f;
    
    glBegin(GL_QUADS);
    glNormal3f( 1f, 0f, 0f); glVertex3f(+extent,-extent,+extent); glVertex3f(+extent,-extent,-extent); glVertex3f(+extent,+extent,-extent); glVertex3f(+extent,+extent,+extent);
    glNormal3f( 0f, 1f, 0f); glVertex3f(+extent,+extent,+extent); glVertex3f(+extent,+extent,-extent); glVertex3f(-extent,+extent,-extent); glVertex3f(-extent,+extent,+extent);
    glNormal3f( 0f, 0f, 1f); glVertex3f(+extent,+extent,+extent); glVertex3f(-extent,+extent,+extent); glVertex3f(-extent,-extent,+extent); glVertex3f(+extent,-extent,+extent);
    glNormal3f(-1f, 0f, 0f); glVertex3f(-extent,-extent,+extent); glVertex3f(-extent,+extent,+extent); glVertex3f(-extent,+extent,-extent); glVertex3f(-extent,-extent,-extent);
    glNormal3f( 0f,-1f, 0f); glVertex3f(-extent,-extent,+extent); glVertex3f(-extent,-extent,-extent); glVertex3f(+extent,-extent,-extent); glVertex3f(+extent,-extent,+extent);
    glNormal3f( 0f, 0f,-1f); glVertex3f(-extent,-extent,-extent); glVertex3f(-extent,+extent,-extent); glVertex3f(+extent,+extent,-extent); glVertex3f(+extent,-extent,-extent);
    glEnd();
  }

  @Override
  public void drawSphere(float radius, int slices, int stacks) {
  //  PlayN.log().info("drawSphere NYI");
    drawCube(radius);
  }

  @Override
  public void drawCylinder(float radius, float halfHeight, int upAxis) {
   // PlayN.log().info("drawCylinder NYI");
    drawCube(radius);
  }

  void loadFontTexture() {
    int [] argbArray = new int[fontImage.width() * fontImage.height()];
    fontImage.getRgb(0, 0, fontImage.width(), fontImage.height(), argbArray, 0, fontImage.width());
    
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Math.max(65536, fontImage.width() * fontImage.height()) * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    IntBuffer rgbaBuffer = byteBuffer.asIntBuffer();
    
    for(int i = 0; i < argbArray.length; i++) {
      int argb = argbArray[i];
      rgbaBuffer.put(i, (argb << 8) | (argb >>> 24));
    }
    
    gl.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture);
    gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
    gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    gl.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, fontImage.width(), fontImage.height(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, rgbaBuffer);
  }
  
  @Override
  public void drawString(CharSequence text, int x, int y, float red, float green,
      float blue) {
    if (!fontLoaded) {
      if (!fontImage.isReady()) {
        return;
      }
      loadFontTexture();
      fontLoaded = true;
    }
    
    gl.glEnable(GL11.GL_BLEND);
    gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    
    gl.glPushMatrix();
    gl.glTranslatef(x, y, 0);
    
    gl.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture);
    gl.glEnable(GL11.GL_TEXTURE_2D);
    gl.glColor4f(red, green, blue, 1);
    //glColor4f(1, 1, 1, 1);
    tesselator.begin(GL_QUADS);
   // tesselator.color3f(1, 0, 0);
    for (int i=0, n=text.length(); i<n; i++) {
        char c = text.charAt(i);
        if (c < 32 || c >= 128) c = '?';
        float s = (c % 16) / 16.f;
        float t = (c / 16) / 8.f;
        
       // System.out.println("s: " + s + "t: " + t);
        
        tesselator.texCoord2f(s, t);
     //   tesselator.texCoord2f(0, 0);
        tesselator.vertex3f(i * 8, 0, 1);

        tesselator.texCoord2f(s + 1.f/16, t);
      //  tesselator.texCoord2f(0, 1);
        tesselator.vertex3f(i * 8 + 8, 0, 1);

        tesselator.texCoord2f(s + 1.f/16, t + 1.f/8);
     //   tesselator.texCoord2f(1, 1);
        tesselator.vertex3f(i * 8 + 8, 16, 1);

        tesselator.texCoord2f(s, t + 1.f/8);
     //   tesselator.texCoord2f(1, 0);
        tesselator.vertex3f(i * 8, 16, 1);
    }
    tesselator.draw(gl);
    gl.glDisable(GL11.GL_TEXTURE_2D);
    gl.glPopMatrix();

    glDisable(GL11.GL_BLEND);
  }

  @Override
  public void glLight(int light, int pname, float[] params) {
    gl.glLightfv(light, pname, params, 0);
  }

  @Override
  public void glEnable(int cap) {
    gl.glEnable(cap);
  }

  @Override
  public void glDisable(int cap) {
    gl.glDisable(cap);
  }

  @Override
  public void glShadeModel(int mode) {
    gl.glShadeModel(mode);
  }

  @Override
  public void glDepthFunc(int func) {
    gl.glDepthFunc(func);
  }

  @Override
  public void glClearColor(float red, float green, float blue, float alpha) {
    gl.glClearColor(red, green, blue, alpha);
  }

  @Override
  public void glMatrixMode(int mode) {
    gl.glMatrixMode(mode);
  }

  @Override
  public void glLoadIdentity() {
    gl.glLoadIdentity();
  }

}
