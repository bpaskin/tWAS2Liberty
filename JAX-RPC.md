The old way of using Java API for XML-based web services was with RPC.  Liberty does not support JAX-RPC, but it is not difficult to make it work.

In many cases the APIs need to be added to a shared library or the application.  The JAX-RPC APIs can be [downloaded](https://mvnrepository.com/artifact/javax.xml/jaxrpc-api/1.1) from Maven. It is suggested to place this in shared library and then added to the application in the liberty `server.xml`.  Features like JAXB can be added in the `server.xml`.

Example of the shared library:
```
<library id="jaxrpc" name="jaxrpc">
   <fileset dir="/path/to/sharedlib/jaxrpc" includes="*.jar"/>
</library>
```
Example with adding the shared library to the application:
```
<webApplication id="myApp" location="myApp.war" name="myApp">
   <classloader commonLibraryRef="jaxrpc"/>
</webApplication>
```
