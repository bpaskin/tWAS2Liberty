# tWAS2Liberty

Some guides to help with common problems migrating from traditional [WebSphere Application Server](https://www.ibm.com/cloud/websphere-application-server) to [Liberty](https://www.ibm.com/cloud/websphere-liberty) or [OpenLiberty](https://openliberty.io).

Running the [Transformation Advisor](https://www.ibm.com/garage/method/practices/learn/ibm-transformation-advisor/) yields lots of helpful information, but some solutions can be worked around instead of additional coding or refactoring applications.  This includes specific tWAS enhancements.

Some problems must be recoded, like calling specific [WebSphere MBeans](https://www.ibm.com/docs/en/was-liberty/base?topic=liberty-list-provided-mbeans).  

1. [CICS Transaction Gateway with Web Services Invocation Framework](CICSTGwithWSIF.md) (WSIF)
2. [Web Services JAX-RPC](JAX-RPC.md)
3. [JNDI InitialContext](InitialContextFactory.md)
4. [Outbound Web Service (JAX-WS) that requires SSL](JAXWSwithOutboundSecurity.md)

Deploy with OCP Builds and OpenLiberty [example](https://github.com/bpaskin/WASLibertyScriptsAndStuff/tree/master/OCPLibertyBuild).
