package com.googlecode.playnbulletdemo.java;

import com.googlecode.playnbulletdemo.core.BulletDemo;

import playn.core.PlayN;
import playn.java.JavaPlatform;

public class BulletDemoJava {
  
  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("com/googlecode/playnbulletdemo/resources");
    PlayN.run(new BulletDemo());
  }
}
