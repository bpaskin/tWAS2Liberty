### WARNING: Unknown module: jdk. ###

---

The errors or similar may occur and show up in the `console.log` of Liberty
```
WARNING: Unknown module: jdk.management.agent specified to --add-exports
WARNING: Unknown module: jdk.attach specified to --add-exports
```

This error occurs whem using a Java Runtime Environment (JRE), whether on a local, virtual or in containers and is due to the JRE not having those modules. The solution is to either use the Java Development Kit (JDK) instead of the JRE or remove the features trying to access those packages, like `localConnector-1.0`.  For developers this can cause issues debugging, so it is advised to use a JDK during development and then remove the feature and use the JRE in other environments
