**Overview**

Working on different projects we have created a bunch of useful 
utilities/helper classes/wrappers and of course duplicates of some 
existing libraries. So this is a repo to rule them all.

**Build status**

[![Build Status](https://travis-ci.org/nginate/commons-lang.svg?branch=master)](https://travis-ci.org/nginate/commons-lang)
[ ![Download](https://api.bintray.com/packages/nginate/maven/commons-lang/images/download.svg) ](https://bintray.com/nginate/maven/commons-lang/_latestVersion)

**What's there in this bundle?**

* String utility

Adds an ability to use string templates, similar to those, used in slf4j

```java
    String result = NStrings.format("abc {} asd", "test");
    System.out.println(result);
    
    > abc test asd
    
    String namedResult = NStrings.formatNamed("abc {arg1} asd", "test");
    System.out.println(result);
    
    > abc test asd
```

* Collections utility

Functional stuff from java 8 to simplify boilerplate around collections

```java
    // get single existing element
    List<Integer> list = Collections.singletonList(10);
    Integer element = NCollections.requireSingle(list);
    
    int[] array = new int[100];
    // fill first 10 elements
    List<Integer> someList = ...
    int[] result = NCollections.mapToArray(someList, value -> array, identity());
    
    //filter elements
    Set<String> strings = ...
    Set<String> nonEmptyStrings = NCollections.filter(strings, s -> !"".equals(s));
```

* Await

Sometimes it's worth to just sit and wait for the body of your enemy to
float by.

```java
    // wait for 30 sec
    Await.waitUntil(30000, () -> isEnemyBodyFloatingBy());
    // wait for 30 sec and check each sec
    Await.waitUntil(30000, 1000, () -> isEnemyBodyFloatingBy());
    // wait and notify tyourself with a message
    Await.waitUntil(30000, 1000, "all your base are belong to us", () -> isEnemyBodyFloatingBy());
```

* Unchecked

Default stream API can't handle checked exceptions in functional call, 
because those interfaces do not throw checked exceptions. This one 
allows you to say hello to functional programming with old-fashioned 
checked exceptions.

Let's assume you have method with lambda parameter:

```java
    //vanilla style
    public void wrap(Runnable callback) {
        // do nasty things
        callback.run();
        // do nasty things again
    }

    ...
    wrap(() -> {
        try {
            // do something that throws exception
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });
```

So you will usually call it this way:

```java
    wrap(() -> {
        try {
            // do something that throws exception
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });
```

With unchecked functional interfaces you can avoid boilerplate in your code.
By default all exceptions will be wrapped with RuntimeException:

```java
    wrap(NFunctions.unchecked(() -> throw new Exception(""));
```

Of course you want to control how to wrap your exception.
This call will throw RuntimeIOException with initial cause:

```java
    wrap(NFunctions.unchecked(() -> throw new Exception(""), RuntimeIOException::new);
```

* Memoizers

Some heavy operations may be wrapped with implicit cache functions

```java
    //Before
    list.stream().forEach(element -> {
        Object result = heavyFunction.apply(element); // will perform computing even for duplicate elements
        System.out.println("Result :" + result);
    });
    
    //After
    Function<T, U> optimizedFunction = NFunctions.memoize(heavyFunction);
    list.stream().forEach(element -> {
        Object result = heavyFunction.apply(element); // results are cached
        System.out.println("Result :" + result);
    });
```

**License**

<a href="http://www.wtfpl.net/"><img
       src="http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png"
       width="80" height="15" alt="WTFPL" /></a>