/**
 * ComprimirDocumentos_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.comprimirDocumentos;

public class ComprimirDocumentos_ServiceLocator extends org.apache.axis.client.Service implements com.ws.comprimirDocumentos.ComprimirDocumentos_Service {

    public ComprimirDocumentos_ServiceLocator() {
    }


    public ComprimirDocumentos_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ComprimirDocumentos_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ComprimirDocumentosPort
    private java.lang.String ComprimirDocumentosPort_address = "http://172.26.0.2:8888/orabpel/default/ComprimirDocumentos/3.0";

    public java.lang.String getComprimirDocumentosPortAddress() {
        return ComprimirDocumentosPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ComprimirDocumentosPortWSDDServiceName = "ComprimirDocumentosPort";

    public java.lang.String getComprimirDocumentosPortWSDDServiceName() {
        return ComprimirDocumentosPortWSDDServiceName;
    }

    public void setComprimirDocumentosPortWSDDServiceName(java.lang.String name) {
        ComprimirDocumentosPortWSDDServiceName = name;
    }

    public com.ws.comprimirDocumentos.ComprimirDocumentos_PortType getComprimirDocumentosPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ComprimirDocumentosPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getComprimirDocumentosPort(endpoint);
    }

    public com.ws.comprimirDocumentos.ComprimirDocumentos_PortType getComprimirDocumentosPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.ws.comprimirDocumentos.ComprimirDocumentosBindingStub _stub = new com.ws.comprimirDocumentos.ComprimirDocumentosBindingStub(portAddress, this);
            _stub.setPortName(getComprimirDocumentosPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setComprimirDocumentosPortEndpointAddress(java.lang.String address) {
        ComprimirDocumentosPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ws.comprimirDocumentos.ComprimirDocumentos_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.ws.comprimirDocumentos.ComprimirDocumentosBindingStub _stub = new com.ws.comprimirDocumentos.ComprimirDocumentosBindingStub(new java.net.URL(ComprimirDocumentosPort_address), this);
                _stub.setPortName(getComprimirDocumentosPortWSDDServiceName());
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
        if ("ComprimirDocumentosPort".equals(inputPortName)) {
            return getComprimirDocumentosPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.scor.com/ComprimirDocumentos", "ComprimirDocumentos");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.scor.com/ComprimirDocumentos", "ComprimirDocumentosPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ComprimirDocumentosPort".equals(portName)) {
            setComprimirDocumentosPortEndpointAddress(address);
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
