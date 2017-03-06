
Java port of the short id -- https://github.com/dylang/shortid

See [shortid](https://github.com/dylang/shortid) for documentation.

### How to use

Gradle
```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/snimavat/maven" 
    }
}

dependencies {
	compile "me.nimavat:shortid:1.0.1.RC1"
}


```

```java
    import me.nimavat.shortid.ShortId
    
    System.out.println(Shortid.generate())

```

Compatibility Java 1.8 + 

