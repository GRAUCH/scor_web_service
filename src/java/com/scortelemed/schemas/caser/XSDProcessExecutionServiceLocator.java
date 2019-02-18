/**
 * XSDProcessExecutionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.scortelemed.schemas.caser;

public class XSDProcessExecutionServiceLocator extends org.apache.axis.client.Service implements com.scortelemed.schemas.caser.XSDProcessExecutionService {

    public XSDProcessExecutionServiceLocator() {
    }


    public XSDProcessExecutionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public XSDProcessExecutionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for XSDProcessExecutionPort
    private java.lang.String XSDProcessExecutionPort_address = "https://iwssgotest.caser.es:443/sgowschannel/XSDProcessExecution";

    public java.lang.String getXSDProcessExecutionPortAddress() {
        return XSDProcessExecutionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String XSDProcessExecutionPortWSDDServiceName = "XSDProcessExecutionPort";

    public java.lang.String getXSDProcessExecutionPortWSDDServiceName() {
        return XSDProcessExecutionPortWSDDServiceName;
    }

    public void setXSDProcessExecutionPortWSDDServiceName(java.lang.String name) {
        XSDProcessExecutionPortWSDDServiceName = name;
    }

    public com.scortelemed.schemas.caser.XSDProcessExecutionPort getXSDProcessExecutionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(XSDProcessExecutionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getXSDProcessExecutionPort(endpoint);
    }

    public com.scortelemed.schemas.caser.XSDProcessExecutionPort getXSDProcessExecutionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.scortelemed.schemas.caser.XSDProcessExecutionPortStub _stub = new com.scortelemed.schemas.caser.XSDProcessExecutionPortStub(portAddress, this);
            _stub.setPortName(getXSDProcessExecutionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setXSDProcessExecutionPortEndpointAddress(java.lang.String address) {
        XSDProcessExecutionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.scortelemed.schemas.caser.XSDProcessExecutionPort.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.scortelemed.schemas.caser.XSDProcessExecutionPortStub _stub = new com.scortelemed.schemas.caser.XSDProcessExecutionPortStub(new java.net.URL(XSDProcessExecutionPort_address), this);
                _stub.setPortName(getXSDProcessExecutionPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Exception t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("XSDProcessExecutionPort".equals(inputPortName)) {
            return getXSDProcessExecutionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:XSDProcessExecution", "XSDProcessExecutionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:XSDProcessExecution", "XSDProcessExecutionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("XSDProcessExecutionPort".equals(portName)) {
            setXSDProcessExecutionPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
