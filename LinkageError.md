### java.lang.LinkageError ###

---

A LinkageError occurs when the same class is loaded from two differnt sources.  This usually occurs when a Liberty feature is enabled, and the application has their own version of the same class as one of its libraries.  To solve this either disabling the Liberty feature, and allowing the application to run using its own internal libraries, or removing the application libraries causing the issue usually works.  This occurs with JAXB, CXF (JAX-WS), JPA and others.
