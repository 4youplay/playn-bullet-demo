package com.googlecode.playnbulletdemo.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import playn.core.Image;
import playn.core.PlayN;
import playn.core.gl.GL20;

import com.bulletphysics.demos.opengl.IGL;
import com.googlecode.playnbulletdemo.core.gl.Shapes;
import com.googlecode.playnbulletdemo.core.gl.MeshBuilder;
import com.googlecode.playnbulletdemo.core.gl.MeshBuilder.Mode;
import com.googlecode.playnbulletdemo.core.gl11.GL11;
import com.googlecode.playnbulletdemo.core.gl11.GL11FixedFunctionEmulation;
import com.googlecode.playnbulletdemo.core.gl11.GLU;
import com.googlecode.playnbulletdemo.core.gl11.GL11Vbo;

public class PlayNIGL implements com.bulletphysics.demos.opengl.IGL {

  GL11FixedFunctionEmulation gl;
  MeshBuilder meshBuilder = new MeshBuilder(10000, MeshBuilder.OPTION_COLOR);
  MeshBuilder textBuilder = new MeshBuilder(10000, MeshBuilder.OPTION_TEXTURE);
  boolean tesselating;
  Texture fontTexture;
  int options;
  int defaultOptions;

  boolean textPending;
  float textR;
  float textG;
  float textB;

  GL11Vbo cube;
  GL11Vbo cylinder;
  HashMap<String, Texture> textures = new HashMap<String, Texture>();

  public PlayNIGL(GL20 gl20) {
    gl = new GL11FixedFunctionEmulation(gl20);
    cube = new GL11Vbo(gl, Shapes.cube(new MeshBuilder(4 * 6)));
    
    meshBuilder.reset(Mode.TRIANGLES);
    Shapes.cylinder(meshBuilder, 1, 1, 1, 15);
    Shapes.disk(meshBuilder, 1, 15);
    meshBuilder.translate(0, 0, 1);
    Shapes.disk(meshBuilder, 1, 15);
    cylinder = new GL11Vbo(gl, meshBuilder);

    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
    byteBuffer.order(ByteOrder.nativeOrder());
    IntBuffer intBuffer = byteBuffer.asIntBuffer();
    gl.glGenTextures(intBuffer.capacity(), intBuffer);
    fontTexture = new Texture(PlayN.assets().getImage("images/font.png"),
        intBuffer.get(0));
    textures.put("Cube",
        new Texture(PlayN.assets().getImage("images/playncube.png"),
            intBuffer.get(1)));
    textures.put("Box",
        new Texture(PlayN.assets().getImage("images/BulletCube.png"),
            intBuffer.get(2)));
    textures.put("Grid",
        new Texture(PlayN.assets().getImage("images/grid1a.png"),
            intBuffer.get(3)));
    textures.put("Warning",
        new Texture(PlayN.assets().getImage("images/warning.png"),
            intBuffer.get(4)));

    textBuilder.reset(MeshBuilder.Mode.QUADS);
  }

  @Override
  public void glFrustum(double left, double right, double bottom, double top,
      double zNear, double zFar) {
    gl.glFrustumf((float) left, (float) right, (float) bottom, (float) top,
        (float) zNear, (float) zFar);

  }

  @Override
  public void gluLookAt(float eyex, float eyey, float eyez, float centerx,
      float centery, float centerz, float upx, float upy, float upz) {
    GLU.gluLookAt(gl, eyex, eyey, eyez, centerx, centery, centerz, upx,
        upy, upz);
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
      options |= MeshBuilder.OPTION_COLOR;
      meshBuilder.color3f(red, green, blue);
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
    switch (mode) {
    case IGL.GL_QUADS:
      meshBuilder.reset(MeshBuilder.Mode.QUADS);
      break;
    case IGL.GL_TRIANGLES:
      meshBuilder.reset(MeshBuilder.Mode.TRIANGLES);
      break;
    case IGL.GL_LINES:
      meshBuilder.reset(MeshBuilder.Mode.LINES);
      break;
    default:
      throw new IllegalArgumentException("Unrecognized mode: " + mode);
    }
    tesselating = true;
    options = defaultOptions;
  }

  @Override
  public void glEnd() {
    drawMesh(meshBuilder, options);
    tesselating = false;
    options = defaultOptions;
  }

  @Override
  public void glVertex3f(float x, float y, float z) {
    meshBuilder.vertex3f(x, y, z);
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
    options |= MeshBuilder.OPTION_NORMALS;
    meshBuilder.normal3f(nx, ny, nz);
  }

  @Override
  public void glMultMatrix(float[] m) {
    gl.glMultMatrixf(m, 0);
  }

  @Override
  public void drawCube(float extent) {
    extent = extent * 0.5f;
    glPushMatrix();
    glScalef(extent, extent, extent);
    cube.draw(options);
    glPopMatrix();
  }

  @Override
  public void drawSphere(float radius, int slices, int stacks) {
    // PlayN.log().info("drawSphere NYI");
    drawCube(radius);
  }

  @Override
  public void drawCylinder(float radius, float halfHeight, int upAxis) {
    glPushMatrix();
    switch (upAxis) {
    case 0:
      gl.glRotatef(-90f, 0.0f, 1.0f, 0.0f);
      gl.glTranslatef(0.0f, 0.0f, -halfHeight);
      break;
    case 1:
      gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
      gl.glTranslatef(0.0f, 0.0f, -halfHeight);
      break;
    case 2:
      gl.glTranslatef(0.0f, 0.0f, -halfHeight);
      break;
    default: 
      assert (false);
    }

    gl.glScalef(radius, radius, halfHeight * 2);

    cylinder.draw(options);

    glPopMatrix();
  }

  @Override
  public void drawString(CharSequence text, int x, int y, float red,
      float green, float blue) {
    fontTexture.load(gl);

    if (red != textR || green != textG || blue != textB) {
      flushText();
    }
    textR = red;
    textG = green;
    textB = blue;
    textPending = true;
    // glColor4f(1, 1, 1, 1);
    // textTesselator.color3f(1, 0, 0);
    int len = text.length();
    for (int i = 0; i < len; i++) {
      char c = text.charAt(i);
      if (c != ' ') {
        if (c < 32 || c >= 128) {
          c = '?';
        }
        float s = (c % 16) / 16f;
        float t = (c / 16) / 8f;

        // System.out.println("s: " + s + "t: " + t);
        textBuilder.texCoord2f(s, t);
        textBuilder.vertex3f(x, y, 1);

        textBuilder.texCoord2f(s + 1f / 16f, t);
        textBuilder.vertex3f(x + 8, y, 1);

        textBuilder.texCoord2f(s + 1f / 16f, t + 1f / 8f);
        textBuilder.vertex3f(x + 8, y + 16, 1);

        textBuilder.texCoord2f(s, t + 1f / 8f);
        textBuilder.vertex3f(x, y + 16, 1);
      }
      x += 6;
    }
  }

  private void flushText() {
    if (!textPending) {
      return;
    }
    gl.glEnable(GL11.GL_BLEND);
    gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    gl.glColor4f(textR, textG, textB, 1);
    fontTexture.load(gl);

    drawMesh(textBuilder, MeshBuilder.OPTION_TEXTURE);
    gl.glDisable(GL11.GL_TEXTURE_2D);

    glDisable(GL11.GL_BLEND);

    textBuilder.reset(MeshBuilder.Mode.QUADS);
    textPending = false;
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
    flushText();
    gl.glMatrixMode(mode);
  }

  @Override
  public void glLoadIdentity() {
    gl.glLoadIdentity();
  }

  static class Texture {
    Image image;
    boolean loaded;
    int handle;

    public Texture(Image image, int handle) {
      this.image = image;
      this.handle = handle;
    }

    void load(GL11 gl) {
      if (!loaded) {
        if (!image.isReady()) {
          return;
        }
        loaded = true;
        int w = image.width();
        int h = image.height();

        int[] argbArray = new int[w * h];
        image.getRgb(0, 0, w, h, argbArray, 0, w);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Math
            .max(65536, w * h) * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer rgbaBuffer = byteBuffer.asIntBuffer();

        for (int i = 0; i < argbArray.length; i++) {
          int argb = argbArray[i];
          byteBuffer.put((byte) ((argb >> 16) & 255));
          byteBuffer.put((byte) ((argb >> 8) & 255));
          byteBuffer.put((byte) ((argb >> 0) & 255));
          byteBuffer.put((byte) ((argb >> 24) & 255));
        }
        byteBuffer.position(0);
        gl.glBindTexture(GL11.GL_TEXTURE_2D, handle);
        gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
            GL11.GL_LINEAR);
        gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
            GL11.GL_NEAREST);
        gl.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0,
            GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
      }
      gl.glBindTexture(GL11.GL_TEXTURE_2D, handle);
      gl.glEnable(GL11.GL_TEXTURE_2D);
    }

  }

  @Override
  public void setUserPointer(Object userPointer) {
    Texture texture = userPointer == null ? null : textures
        .get(userPointer);
    if (texture != null) {
      texture.load(gl);
      options |= MeshBuilder.OPTION_TEXTURE;
      defaultOptions = MeshBuilder.OPTION_TEXTURE;
    } else {
      gl.glDisable(GL11.GL_TEXTURE_2D);
      options &= ~MeshBuilder.OPTION_TEXTURE;
      defaultOptions = 0;
    }
  }

  void drawMesh(MeshBuilder mesh, int options) {
    options &= mesh.getOptions();
    int byteStride = mesh.getByteStride();

    ByteBuffer byteBuffer = mesh.getBuffer();
    gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    byteBuffer.position(0);
    byteBuffer.limit(mesh.getByteLimit());
    gl.glVertexPointer(3, GL11.GL_FLOAT, byteStride, byteBuffer);

    if ((options & MeshBuilder.OPTION_COLOR) != 0) {
      gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
      byteBuffer.position(mesh.getColorByteOffset());
      gl.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, byteStride, byteBuffer);
    } else {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }

    // if (hasNormal) {
    // gl.glEnableClientState(GL11.GL_NORMAL_ARRAY);
    // byteBuffer.position(NORMAL_OFFSET * FLOAT_SIZE);
    // gl.glNormalPointer(GL11.GL_FLOAT, BYTE_STRIDE, byteBuffer);
    // } else {
    // gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);
    // }

    if ((options & MeshBuilder.OPTION_TEXTURE) != 0) {
      gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
      byteBuffer.position(mesh.getTexCoordByteOffset());
      gl.glTexCoordPointer(2, GL11.GL_FLOAT, byteStride, byteBuffer);
    } else {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }

    ShortBuffer indexBuffer = mesh.getIndices();
    indexBuffer.position(0);
    indexBuffer.limit(mesh.getIndexCount());
    gl.glDrawElements(mesh.getMode() == Mode.LINES ? GL11.GL_LINES
        : GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_SHORT,
        indexBuffer);

    // gl.glDrawArrays(mode, 0, pos / FLOATS_PER_EDGE);

    if ((options & MeshBuilder.OPTION_COLOR) != 0) {
      gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
    }
    if ((options & MeshBuilder.OPTION_TEXTURE) != 0) {
      gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }

    byteBuffer.limit(byteBuffer.capacity());
    indexBuffer.limit(indexBuffer.capacity());
  }

}
