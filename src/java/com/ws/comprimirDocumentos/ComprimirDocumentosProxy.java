package com.ws.comprimirDocumentos;

public class ComprimirDocumentosProxy implements com.ws.comprimirDocumentos.ComprimirDocumentos_PortType {
  private String _endpoint = null;
  private com.ws.comprimirDocumentos.ComprimirDocumentos_PortType comprimirDocumentos_PortType = null;
  
  public ComprimirDocumentosProxy() {
    _initComprimirDocumentosProxy();
  }
  
  public ComprimirDocumentosProxy(String endpoint) {
    _endpoint = endpoint;
    _initComprimirDocumentosProxy();
  }
  
  private void _initComprimirDocumentosProxy() {
    try {
      comprimirDocumentos_PortType = (new com.ws.comprimirDocumentos.ComprimirDocumentos_ServiceLocator()).getComprimirDocumentosPort();
      if (comprimirDocumentos_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)comprimirDocumentos_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)comprimirDocumentos_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (comprimirDocumentos_PortType != null)
      ((javax.xml.rpc.Stub)comprimirDocumentos_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ws.comprimirDocumentos.ComprimirDocumentos_PortType getComprimirDocumentos_PortType() {
    if (comprimirDocumentos_PortType == null)
      _initComprimirDocumentosProxy();
    return comprimirDocumentos_PortType;
  }
  
  public com.ws.comprimirDocumentos.ParametrosSalida process(com.ws.comprimirDocumentos.ParametrosEntrada payload) throws java.rmi.RemoteException{
    if (comprimirDocumentos_PortType == null)
      _initComprimirDocumentosProxy();
    return comprimirDocumentos_PortType.process(payload);
  }
  
  
}