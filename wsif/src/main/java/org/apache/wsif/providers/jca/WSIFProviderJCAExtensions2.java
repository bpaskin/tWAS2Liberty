package org.apache.wsif.providers.jca;

import javax.resource.cci.Connection;
import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import org.apache.wsif.WSIFException;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.providers.WSIFDynamicTypeMap;

public interface WSIFProviderJCAExtensions2 extends WSIFProviderJCAExtensions {
  Connection createConnection(WSIFPort_JCA paramWSIFPort_JCA, WSIFMessage paramWSIFMessage, Definition paramDefinition, Service paramService, Port paramPort, WSIFDynamicTypeMap paramWSIFDynamicTypeMap, Binding paramBinding, String paramString1, String paramString2, String paramString3) throws WSIFException;
}

