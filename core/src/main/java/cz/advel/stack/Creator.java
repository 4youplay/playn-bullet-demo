// Copyright 2012 Google Inc. All Rights Reserved.

package cz.advel.stack;

/**
 * Creates an instance of T.
 */
public interface Creator<T> {
  public T create();
}
