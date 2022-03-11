# CICS Transaction Gateway with Web Service Innovation Framework (WSIF)

This was the old method of calling the CICS Transaction Gateway, and it is still supported in tWAS today.  However, the framework, which originally is from the Apache Group, is not support by Liberty.  CICS TG calls were generated with this code when using the old [WebSphere Studio Application Developer](https://www.ibm.com/common/ssi/ShowDoc.wss?docURL=/common/ssi/rep_ca/0/897/ENUS202-330/index.html) Integration Edition.  

The following jars can be added to a shared library that is referenced by the application, which is preferred than including the jars in the application.
1. copy the jars from `<WAS_HOME>/plugins/com.ibm.ws.wsadie` (`marshall.jar` \, `wsatlib.jar`, `physicalrep.jar`) to the shared library
2. [download](https://archive.apache.org/dist/ws/wsif/2_0/) the last version of the WSIF binaries and extract the files
3. copy the files `wsif.jar` and `wsif-j2c.jar` from `wsif-2.0/build/lib` to the shared library
4. setup liberty with a shared library
```
<library id="ctg" name="ctg">
   <fileset dir="/path/to/sharedlib/ctg" includes="*.jar"/>
</library>
```
5. Add the shared library to the application
```
<webApplication id="myApp" location="myApp.war" name="myApp">
   <classloader classProviderRef="ctgRA" commonLibraryRef="ctg"/>
</webApplication>
```
The `classProviderRef` above is the reference to the resource adapter for CICS TG.
```
<resourceAdapter autoStart="true" id="ctgRA" location="/path/to/cicseci.rar"/>
```
