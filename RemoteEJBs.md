### Accessing Remote EJBs

---

In tWAS and other containers stubs and ties needed to be created to connect to remote EJBs.  Under Liberty is this no longer the case.  Make sure the remote EJBs are needed.  Invoking them when running in the same JVM is a waste of resources and a very happy process.  It is better to slightly refactor the code to use the `LocalHome` interface.  When truly remote then the `corbaloc` needs to be set.   Example:
```
corbaloc:iiop:<host>:<port>/<namespace>
```

How to create the namespace can be found on the Liberty [documentation page](https://www.ibm.com/docs/it/was-liberty/nd?topic=liberty-using-enterprise-javabeans-remote-interfaces).  The namespace is also printed in the `messages.log` file upon server start of the target server.  Full example:
```
Properties p = new Properties();
p.put(Context.PROVIDER_URL, "corbaloc::test.ibm.com:2809");
InitialContext c = new InitialContext(p);
Object found = c.lookup("ejb/global/ExampleApp/ExampleModule/ExampleBean!com.ibm.example.ExampleRemoteInterface");
ExampleRemoteInterface bean = (ExampleRemoteInterface)PortableRemoteObject.narrow(found, ExampleRemoteInterface.class);
```

---

Standalone EJB clients are completely different to setup.  It requires a [Liberty Client](https://www.ibm.com/docs/en/was-liberty/core?topic=liberty-preparing-running-application-client) setup.  There is an [example](https://github.com/bpaskin/JakartaEEClientExample) of the program and setup in another one of my repos.  
