In traditional WebSphere the initial context factory that is used is `com.ibm.websphere.naming.WsnInitialContextFactory`. This should have only been used for remote lookups, but there are lots of code that used this for local lookups too.  This is not supported in Liberty, and the context factory is not needed at all.

For example:
```
Properties env = new Properties();
env.put(Context.PROVIDER_URL,"iiop://<server IP address>:<server bootstrap address port>");
env.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");
InitialContext ctx = new InitialContext(env);
```
Can be changed to:
```
InitialContext ctx = new InitialContext();
```
No need to set thee provider or initial context factory at all.   Afterwards:
```
DataSource ds = (DataSource) ctx.lookup("jdbc/myDS");
```
