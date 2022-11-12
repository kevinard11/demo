package com.yukiii.demo.cache;

public interface Cache<K, V> {
  final String PATTERN = "*";

  void set(K key, V value);

  V get(K key);

  boolean exist(K key);

  long bust();

}
