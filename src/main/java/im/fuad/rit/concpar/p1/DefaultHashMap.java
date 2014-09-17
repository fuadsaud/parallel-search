package im.fuad.rit.concpar.p1;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Extension for java.util.HashMap that supports a default value to be returned when asked for a
 * key that it doens't contain. The default value is specified by a "lambda-like" object, an object
 * implementing java.concurrent.Callable that returns an object of the same type of the keys of
 * this map when #call is called. This is in order to provide the ability to return default values
 * created at runtime, instead of specifying a single default instance of the time of creation of
 * this map. Though the implementation may seem overcomplicated, that's because of the lack of
 * lambda support in Java (the implementation of this pattern is trivial in languages that support
 * it).
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class DefaultHashMap<K, V> extends HashMap<K, V> {
  protected Callable<V> defaultValueLambda;

  public DefaultHashMap(Callable<V> defaultValueLambda) {
    this.defaultValueLambda = defaultValueLambda;
  }

  @Override
  public V get(Object k) {
      if (containsKey(k)) {
          return super.get(k);
      } else {
          try {
            return defaultValueLambda.call();
          } catch(Exception e) {
              throw new RuntimeException();
          }
      }
  }
}
