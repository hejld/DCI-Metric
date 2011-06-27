package cz.vse.metric.dci;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Class DCIMetric
 * This is core class for computing the Data Coupling Index
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class DCIMetric {

    private List<ServiceInterface> serviceInterfaces = new ArrayList<ServiceInterface>();
    private List<ServiceInterfaceCombination> serviceInterfaceCombinations;

    /**
     * Data Coupling Index (DCI)
     */
    private float dataCouplingIndex;

    /**
     * Normalized Data Coupling Index (NDCI)
     */
    private float normalizedDataCouplingIndex;

    private float totalNumberOfSchemaElements;

    private float averageNumberOfComplexTypesPerInterface;

/*************************************************************************/
/**  Private methods
/*************************************************************************/

    /**
     * Prepares all the possible interface combinations to be used for measurement
     */
    private void prepareInterfaceCombinations() {
        serviceInterfaceCombinations = new ArrayList<ServiceInterfaceCombination>();
        for (ServiceInterface interfaceA : serviceInterfaces) {
            for (ServiceInterface interfaceB : serviceInterfaces) {
                if(interfaceA != interfaceB) {
                    serviceInterfaceCombinations.add(new ServiceInterfaceCombination(interfaceA, interfaceB));
                }
            }
            totalNumberOfSchemaElements += interfaceA.getNumberOfComplexTypes();
        }
    }

    /**
     * Computes the DCI and NDCI values for all the currently loaded Service Interfaces
     */
    private void computeDCI() {
        // Reset the counters
        float totalNumberOfSharedSchemaElementsCounter = 0f;
        totalNumberOfSchemaElements = 0f;

        prepareInterfaceCombinations();

        // count the total number of shared schema elements
        for (ServiceInterfaceCombination combination : serviceInterfaceCombinations) {
            totalNumberOfSharedSchemaElementsCounter += combination.getNumberOfSharedElements();
        }

        // compute the DCI and NDCI according to the formula
        float r = serviceInterfaceCombinations.size() / 2;
        dataCouplingIndex = totalNumberOfSharedSchemaElementsCounter / (r * 2);
        averageNumberOfComplexTypesPerInterface = (totalNumberOfSchemaElements / serviceInterfaces.size());
        normalizedDataCouplingIndex = dataCouplingIndex / averageNumberOfComplexTypesPerInterface;
    }

/*************************************************************************/
/**  Metric initialization
/*************************************************************************/

    /**
     * Method for loading data from WSDL and XSD definitions
     *
     * Can be used multiple times until all the WSDL definitions are loaded
     * @param wsdlUri
     * @throws Exception
     */
    public void loadWsdl(String wsdlUri) throws Exception {
        serviceInterfaces.addAll(WsdlUtils.getAllServiceInterfacesFromWsdl(wsdlUri));
    }

    /**
     * Method that starts the computation of the values
     */
    public void computeMetric() {
        computeDCI();
    }


/*************************************************************************/
/**  Metric results
/*************************************************************************/

    /**
     * @return DCI value
     */
    public float getDataCouplingIndex() {
        return dataCouplingIndex;
    }

    /**
     * @return NDCI value
     */
    public float getNormalizedDataCouplingIndex() {
        return normalizedDataCouplingIndex;
    }

    /**
     * @return ANCT value
     */
    public float getAverageNumberOfComplexElementsPerOperation() {
        return averageNumberOfComplexTypesPerInterface;
    }

    /**
     * @return total number of schema elements * usages used by all the analyzed operations
     */
    public float getTotalNumberOfSchemaElements() {
        return totalNumberOfSchemaElements;
    }

    /**
     * @return list of ServiceInterface objects used for measuring DCI
     */
    public List<ServiceInterface> getServiceInterfaces() {
        return serviceInterfaces;
    }

    /**
     * @return list of all possible combinations of ServiceInterfaces used for measuring DCI
     */
    public List<ServiceInterfaceCombination> getServiceInterfaceCombinations() {
        return serviceInterfaceCombinations;
    }

    /**
     * @return map of all complex types that were used more than once, with usage counts
     */
    public Map<QName, Integer> getComplexTypesWithUsageCounts() {
        Map<QName, Integer> sharedComplexTypeMap = new HashMap<QName, Integer>();

        Integer counter;
        for(ServiceInterface si : serviceInterfaces) {
            for(QName complexType : si.getSharedComplexTypeMap().keySet()) {
                counter = sharedComplexTypeMap.get(complexType);
                if (counter != null) {
                    sharedComplexTypeMap.put(complexType, counter + 1);
                } else {
                    sharedComplexTypeMap.put(complexType, 1);
                }
            }
        }
        return sharedComplexTypeMap;
    }
}
