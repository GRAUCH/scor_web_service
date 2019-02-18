package com.scortelemed.schemas.caser;

public class XSDProcessExecutionPortProxy implements com.scortelemed.schemas.caser.XSDProcessExecutionPort {
  private String _endpoint = null;
  private com.scortelemed.schemas.caser.XSDProcessExecutionPort xSDProcessExecutionPort = null;
  
  public XSDProcessExecutionPortProxy() {
    _initXSDProcessExecutionPortProxy();
  }
  
  public XSDProcessExecutionPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initXSDProcessExecutionPortProxy();
  }
  
  private void _initXSDProcessExecutionPortProxy() {
    try {
      xSDProcessExecutionPort = (new com.scortelemed.schemas.caser.XSDProcessExecutionServiceLocator()).getXSDProcessExecutionPort();
      if (xSDProcessExecutionPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)xSDProcessExecutionPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)xSDProcessExecutionPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (xSDProcessExecutionPort != null)
      ((javax.xml.rpc.Stub)xSDProcessExecutionPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.scortelemed.schemas.caser.XSDProcessExecutionPort getXSDProcessExecutionPort() {
    if (xSDProcessExecutionPort == null)
      _initXSDProcessExecutionPortProxy();
    return xSDProcessExecutionPort;
  }
  
  public void doProcessExecution(java.lang.String input, javax.xml.rpc.holders.StringHolder output) throws java.rmi.RemoteException{
    if (xSDProcessExecutionPort == null)
      _initXSDProcessExecutionPortProxy();
    xSDProcessExecutionPort.doProcessExecution(input, output);
  }
  
  
}