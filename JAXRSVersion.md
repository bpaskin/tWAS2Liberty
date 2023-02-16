If an application is written for JavaEE6 or before usually require JAX-RS 1.1.  However, OpenLiberty does not support any JEE6 technologies, thus JAX-RS 2.0 needs to be used.  However, there are some [behavior changes](https://www.ibm.com/docs/en/was/9.0.5?topic=applications-jax-rs-20-behavior-changes) in JAX-RS 2.0 that may cause the application to not work properly or fail. 

One item that may fail is that when creating XML from a POJO, it must be the direct item that is returned.  The Object must have the XML annotations or an empty response will be returned without an error.
