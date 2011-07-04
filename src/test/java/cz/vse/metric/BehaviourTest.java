package cz.vse.metric;


import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterface;
import cz.vse.metric.dci.WsdlUtils;
import org.junit.Test;
import org.ow2.easywsdl.wsdl.api.Description;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Class BehaviourTest
 * General test for the tool
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class BehaviourTest {

    public static String WSDL_URL = "http://ws.xwebservices.com/XWebBlog/V2/XWebBlog.wsdl";

    /**
     * Based on WSDL file provided, the application should be able
     * to parse the file and get all the Message definitions from it
     */
    @Test
    public void testLoadAllMessageDefinitionsFromWsdl() throws Exception {
        List<ServiceInterface> serviceInterfaces = WsdlUtils.getAllServiceInterfacesFromWsdl(WSDL_URL);
        assertNotNull(serviceInterfaces.size());
    }

    @Test
    public void testExtractComplexTypesFormMessages() throws Exception {
        List<ServiceInterface> serviceInterfaces = WsdlUtils.getAllServiceInterfacesFromWsdl(WSDL_URL);

        for (ServiceInterface serviceInterface : serviceInterfaces) {
            serviceInterface.processComplexElements();
        }
    }

    /**
     * Based on the Message definitions, the application should be
     * able to compute index based on the DCI metric
     */
    @Test
    public void testComputeDCIIndex() throws Exception {
        DCIMetric dciMetric = new DCIMetric();
        dciMetric.loadWsdl(WSDL_URL);
        dciMetric.computeMetric();

        assertTrue(dciMetric.getDataCouplingIndex() != 0);
        System.out.println(dciMetric.getDataCouplingIndex());

        assertTrue(dciMetric.getNormalizedDataCouplingIndex() != 0);
        System.out.println(dciMetric.getNormalizedDataCouplingIndex());

    }
}
