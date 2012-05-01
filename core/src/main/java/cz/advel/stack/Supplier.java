// Copyright 2012 Google Inc. All Rights Reserved.

package cz.advel.stack;

/**
 * Supplies an instance of T.
 */
public interface Supplier<T> {
  public T get();
}
