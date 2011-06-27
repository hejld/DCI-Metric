package cz.vse.metric.dci;

import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class WsdlUtils
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class WsdlUtils {

    /**
     * This method loads the WSDL definition
     *
     * The uri needs to be either in form of "http://url.of.the.wsdl" to load the WSDL
     * definition from web or "file://local/path/to/wsdl" to load the WSDL definition
     * from local drive.
     *
     * @param uri pointing to given WSDL
     * @return wsdl description
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     * @throws org.ow2.easywsdl.wsdl.api.WSDLException
     */
    private static Description loadWsdlDefinition(String uri) throws WSDLException, IOException, URISyntaxException {
        WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
        return reader.read(new URL(uri));
    }

    /**
     * This method extract ServiceInterface objects from the WSDL definition
     *
     * @param description to be used
     * @return list of ServiceInterfaces representing all the operation interfaces from given WSDL
     */
    private static List<ServiceInterface> getAllServiceInterfacesFromWsdlDescription(Description description) {
        List<ServiceInterface> serviceInterfaces = new ArrayList<ServiceInterface>();

        for(InterfaceType interfaceType : description.getInterfaces()) {
            for(Operation operation : interfaceType.getOperations()) {
                ServiceInterface serviceInterface = new ServiceInterface();
                serviceInterface.setServiceQName(operation.getQName());

                for (Part part : operation.getInput().getParts()) {
                    serviceInterface.getMessages().add(part.getElement());
                }

                for (Part part : operation.getOutput().getParts()) {
                    serviceInterface.getMessages().add(part.getElement());
                }

                serviceInterface.processComplexElements();
                serviceInterfaces.add(serviceInterface);
            }
        }

        return serviceInterfaces;
    }

/*************************************************************************/
/**  Public methods
/*************************************************************************/


    public static List<ServiceInterface> getAllServiceInterfacesFromWsdl(String wsdlUri) throws Exception {
        Description wsdlDescription = loadWsdlDefinition(wsdlUri);
        return getAllServiceInterfacesFromWsdlDescription(wsdlDescription);
    }


}
