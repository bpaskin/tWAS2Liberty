### Note: 
This issue should be fixed in 23.0.0.4 and thus the below is not necessary if at that version of higher.

---
An issue that can arise is if a web services call using JAX-WS needs to check if a certificate is in its keystore and/or to pass a client certificate to the remote host.  When utilising the feature to go outbound, it does not use the IBM SSLContext, and uses the base JDK's SSLContext, which can result in errors in not find the certificate, because it is not in the base JDK's cacerts file, and not passing the client certificate to the server, since there is no keystore.  To get arround this the `appSecurity-2.0` or `appSecurity-3.0` feature must also be enabled.
