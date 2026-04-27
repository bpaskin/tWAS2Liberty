package org.apache.wsif.providers.jca;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import org.apache.wsif.WSIFCorrelationId;
import org.apache.wsif.WSIFException;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFOperation;
import org.apache.wsif.WSIFResponseHandler;
import org.apache.wsif.logging.Trc;
import org.apache.wsif.providers.WSIFDynamicTypeMap;

public class WSIFOperation_JCA implements WSIFOperation {
  private static final long serialVersionUID = 1L;
  
  protected Connection fieldConnection;
  
  protected InteractionSpec fieldInteractionSpec;
  
  protected Definition fieldDefinition;
  
  protected Binding fieldBinding;
  
  protected String fieldOperationName;
  
  protected String fieldInputName;
  
  protected String fieldOutputName;
  
  protected Operation fieldOperation;
  
  protected WSIFProviderJCAExtensions fieldFactory = null;
  
  private static final String crlf = System.getProperty("line.separator");
  
  private WSIFDynamicTypeMap fieldTypeMap;
  
  private Port fieldPort;
  
  private Service fieldService;
  
  private WSIFPort_JCA fieldJcaPort;
  
  public WSIFOperation_JCA(Definition aDefinition, Service aService, Port aPort, String aOperationName, String aInputName, String aOutputName, WSIFDynamicTypeMap typeMap, WSIFPort_JCA jcaPort, WSIFProviderJCAExtensions aFactory, Connection aConnection, InteractionSpec aInteractionSpec) {
    this.fieldDefinition = aDefinition;
    this.fieldInteractionSpec = aInteractionSpec;
    this.fieldConnection = aConnection;
    this.fieldFactory = aFactory;
    this.fieldBinding = aPort.getBinding();
    this.fieldOperationName = aOperationName;
    this.fieldInputName = aInputName;
    this.fieldOutputName = aOutputName;
    this.fieldTypeMap = typeMap;
    this.fieldPort = aPort;
    this.fieldService = aService;
    this.fieldJcaPort = jcaPort;
  }
  
  public boolean executeRequestResponseOperation(WSIFMessage input, WSIFMessage output, WSIFMessage fault) throws WSIFException {
    Trc.entry(this, input, output, fault);
    if (input.getParts() == null || !input.getParts().hasNext())
      input = null; 
    try {
      this.fieldFactory.updateInteractionSpec(input, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, this.fieldInteractionSpec);
      if (this.fieldConnection == null) {
        if (this.fieldFactory instanceof WSIFProviderJCAExtensions2) {
          this.fieldConnection = ((WSIFProviderJCAExtensions2)this.fieldFactory).createConnection(this.fieldJcaPort, input, this.fieldDefinition, this.fieldService, this.fieldPort, this.fieldTypeMap, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
        } else {
          this.fieldConnection = this.fieldFactory.createConnection(input, this.fieldDefinition, this.fieldService, this.fieldPort, this.fieldTypeMap, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
        } 
        this.fieldJcaPort.setConnection(this.fieldConnection);
      } 
      Interaction interaction = this.fieldConnection.createInteraction();
      interaction.execute(this.fieldInteractionSpec, (Record)input, (Record)output);
      interaction.close();
      if (output instanceof WSIFMessage_JCA)
        ((WSIFMessage_JCA)output).setInteractionSpec(this.fieldInteractionSpec); 
      this.fieldFactory.updateOutputMessage(output, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, this.fieldInteractionSpec);
    } catch (ResourceException exn1) {
      WSIFException newExn = new WSIFException(WSIFResource_JCA.get("WSIF1000E"));
      newExn.setTargetException((Throwable)exn1);
      Trc.exception((Throwable)exn1);
      throw newExn;
    } catch (Throwable exn3) {
      WSIFException newExn = new WSIFException(WSIFResource_JCA.get("WSIF1008E", exn3.getLocalizedMessage()));
      newExn.setTargetException(exn3);
      Trc.exception((Throwable)newExn);
      throw newExn;
    } 
    Trc.exit();
    return true;
  }
  
  public void executeInputOnlyOperation(WSIFMessage input) throws WSIFException {
    Trc.entry(this, input);
    if (input.getParts() == null || !input.getParts().hasNext())
      input = null; 
    try {
      this.fieldFactory.updateInteractionSpec(input, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, this.fieldInteractionSpec);
      if (this.fieldConnection == null) {
        this.fieldConnection = this.fieldFactory.createConnection(input, this.fieldDefinition, this.fieldService, this.fieldPort, this.fieldTypeMap, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
        this.fieldJcaPort.setConnection(this.fieldConnection);
      } 
      Interaction interaction = this.fieldConnection.createInteraction();
      interaction.execute(this.fieldInteractionSpec, (Record)input);
      interaction.close();
    } catch (ResourceException exn1) {
      WSIFException newExn = new WSIFException(WSIFResource_JCA.get("WSIF1000E"));
      Trc.exception((Throwable)exn1);
      newExn.setTargetException((Throwable)exn1);
      throw newExn;
    } catch (Throwable exn3) {
      WSIFException newExn = new WSIFException(WSIFResource_JCA.get("WSIF1008E", exn3.getLocalizedMessage()));
      newExn.setTargetException(exn3);
      Trc.exception((Throwable)newExn);
      throw newExn;
    } 
    Trc.exit();
  }
  
  public WSIFMessage createFaultMessage() {
    Trc.entry(this);
    WSIFMessage message = this.fieldFactory.createFaultMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null)
      return message; 
    return (WSIFMessage)new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 3);
  }
  
  public WSIFMessage createFaultMessage(String name) {
    Trc.entry(this, name);
    WSIFMessage message = this.fieldFactory.createFaultMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null) {
      message.setName(name);
      return message;
    } 
    WSIFMessage_JCAStreamable wSIFMessage_JCAStreamable = new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 3);
    wSIFMessage_JCAStreamable.setName(name);
    return (WSIFMessage)wSIFMessage_JCAStreamable;
  }
  
  public WSIFMessage createInputMessage() {
    Trc.entry(this);
    WSIFMessage message = this.fieldFactory.createInputMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null)
      return message; 
    return (WSIFMessage)new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 1);
  }
  
  public WSIFMessage createInputMessage(String name) {
    Trc.entry(this, name);
    WSIFMessage message = this.fieldFactory.createInputMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null) {
      message.setName(name);
      return message;
    } 
    WSIFMessage_JCAStreamable wSIFMessage_JCAStreamable = new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 1);
    wSIFMessage_JCAStreamable.setName(name);
    return (WSIFMessage)wSIFMessage_JCAStreamable;
  }
  
  public WSIFMessage createOutputMessage() {
    Trc.entry(this);
    WSIFMessage message = this.fieldFactory.createOutputMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null)
      return message; 
    return (WSIFMessage)new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 2);
  }
  
  public WSIFMessage createOutputMessage(String name) {
    Trc.entry(this, name);
    WSIFMessage message = this.fieldFactory.createOutputMessage(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName);
    if (message != null) {
      message.setName(name);
      return message;
    } 
    WSIFMessage_JCAStreamable wSIFMessage_JCAStreamable = new WSIFMessage_JCAStreamable(this.fieldDefinition, this.fieldBinding, this.fieldOperationName, this.fieldInputName, this.fieldOutputName, 2);
    wSIFMessage_JCAStreamable.setName(name);
    return (WSIFMessage)wSIFMessage_JCAStreamable;
  }
  
  public InteractionSpec getInteractionSpec() {
    return this.fieldInteractionSpec;
  }
  
  public void setInteractionSpec(InteractionSpec interactionSpec) {
    this.fieldInteractionSpec = interactionSpec;
  }
  
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(crlf + "[JCAOperation" + crlf);
    try {
      if (this.fieldConnection != null) {
        buffer.append("\tConnection: " + this.fieldConnection.toString() + crlf);
      } else {
        buffer.append("\tConnection: null" + crlf);
      } 
      if (this.fieldInteractionSpec != null) {
        buffer.append("\tInteractionSpec:       " + this.fieldInteractionSpec.toString() + crlf);
      } else {
        buffer.append("\tInteractionSpec:       null" + crlf);
      } 
      if (this.fieldBinding != null) {
        buffer.append("\tBinding:    " + this.fieldBinding.toString() + crlf);
      } else {
        buffer.append("\tBinding:    null" + crlf);
      } 
      if (this.fieldOperation != null) {
        buffer.append("\tOperation:    " + this.fieldOperation.toString() + crlf);
      } else {
        buffer.append("\tOperation:    null" + crlf);
      } 
      if (this.fieldFactory != null) {
        buffer.append("\tFactory:    " + this.fieldFactory.toString() + crlf);
      } else {
        buffer.append("\tFactory:    null" + crlf);
      } 
      if (this.fieldOperationName != null) {
        buffer.append("\tOperationName:    " + this.fieldOperationName + crlf);
      } else {
        buffer.append("\tOperationName:    null" + crlf);
      } 
      if (this.fieldInputName != null) {
        buffer.append("\tInputName:    " + this.fieldInputName + crlf);
      } else {
        buffer.append("\tInputName:    null" + crlf);
      } 
      if (this.fieldOutputName != null) {
        buffer.append("\tOutputName:    " + this.fieldOutputName + crlf);
      } else {
        buffer.append("\tOutputName:    null" + crlf);
      } 
      buffer.append("]" + crlf);
    } catch (Throwable exn) {}
    return buffer.toString();
  }
  
  public WSIFCorrelationId executeRequestResponseAsync(WSIFMessage input, WSIFResponseHandler handler) throws WSIFException {
    return null;
  }
  
  public WSIFCorrelationId executeRequestResponseAsync(WSIFMessage input) throws WSIFException {
    return null;
  }
  
  public void fireAsyncResponse(Object response) throws WSIFException {}
  
  public boolean processAsyncResponse(Object response, WSIFMessage output, WSIFMessage fault) throws WSIFException {
    return false;
  }
  
  public void setContext(WSIFMessage context) {}
  
  public WSIFMessage getContext() {
    return null;
  }
}
