**Overview**
Working on different projects we have created a bunch of useful 
utilities/helper classes/wrappers and of course duplicates of some 
existing libraries. So this is a repo to rule them all.

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