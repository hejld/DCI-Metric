package cz.vse.metric;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterface;
import cz.vse.metric.dci.ServiceInterfaceCombination;

import javax.xml.namespace.QName;
import java.net.SocketOptions;
import java.util.Scanner;

/**
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class ConsoleApplication {

    DCIMetric dciMetric = new DCIMetric();
    Scanner in = new Scanner(System.in);

/*************************************************************************/
/**  Private methods
/*************************************************************************/

    private void welcome() {
        System.out.println("Hello and welcome!");
    }


    private void loadWsdl() {
        // Ask the user for specifying the location of the WSDL to load
        System.out.println("For evaluate the service design, please load the WSDL definition");

        while (true) {
            // Get the definition
            try {
                dciMetric.loadWsdl(in.nextLine());
                System.out.println("Do you want to load another WSDL file? Press 'y' to load.");

                if (!"y".equals(in.nextLine())) {
                    break;
                }

            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Unfortunately, the WSDL was not loaded successfully, please recheck the url");
            }
        }
    }

    private void printBasicReport() {
        System.out.println("Metric was successfully computed!");
        System.out.println("---------------------------------");
        System.out.println("DCI  = " + dciMetric.getDataCouplingIndex());
        System.out.println("NDCI = " + dciMetric.getNormalizedDataCouplingIndex());
        System.out.println("---------------------------------");
        System.out.println("");
        System.out.println("Additional information:");
        System.out.println("Total number of service operations:            " + dciMetric.getServiceInterfaces().size());
        System.out.println("Total number of combinations (r):              " + dciMetric.getServiceInterfaceCombinations().size() / 2);
        System.out.println("");
        System.out.println("Average number of complex types per operation: " + dciMetric.getAverageNumberOfComplexElementsPerOperation());
        System.out.println("Number of complex types that were reused:      " + dciMetric.getComplexTypesWithUsageCounts().size());
        System.out.println("");
    }

    private void processNextActions() {
        while (true) {
            System.out.println("You have following choices:");
            System.out.println("Press 1 for detailed analysis of operation interfaces");
            System.out.println("Press 2 for detailed analysis of complex types");
            System.out.println("Press 3 for detailed analysis of each operation interface combination");

            System.out.println("Press q to quit the program");

            String response = in.nextLine();
            System.out.println("");

            if ("q".equals(response)) {
                break;
            } else if ("1".equals(response)){
                printOperationAnalysis();
            } else if ("2".equals(response)) {
                printComplexTypeAnalysis();
            } else if ("3".equals(response)) {
                printCombinationAnalysis();
            } else {
                System.out.println("The response was not recognized.");
            }
        }
    }

    private void printOperationAnalysis() {
        System.out.println("Detailed analysis of operation interfaces:");

        for(ServiceInterface si : dciMetric.getServiceInterfaces()) {
            System.out.println("");
            System.out.println("Summary for operation: " + si.getServiceQName());
            System.out.println("This operation uses " + si.getNumberOfComplexTypes() + " distinct complex types.");
            System.out.println("They are " + si.getNonAnonymousComplexTypes());
            System.out.println(si.getSharedComplexTypeMap().size() + " of them are shared.");
        }
    }

    private void printComplexTypeAnalysis() {
        System.out.println("Complex type analysis:");
        for(QName complexType : dciMetric.getComplexTypesWithUsageCounts().keySet()) {
            System.out.println("ComplexType: " + complexType + " was used " +
                    dciMetric.getComplexTypesWithUsageCounts().get(complexType) + " times.");
        }


    }

    private void printCombinationAnalysis() {
        System.out.println("Operation interface combinations (total: " +
                dciMetric.getServiceInterfaceCombinations().size() + " combinations)");

        for(ServiceInterfaceCombination sic : dciMetric.getServiceInterfaceCombinations()) {
            System.out.println("Combination: " + sic.getInterfaceA().getServiceQName().getLocalPart()
                    + " vs " + sic.getInterfaceB().getServiceQName().getLocalPart());
            System.out.println("Shared elements: " + sic.getNumberOfSharedElements());
            for(QName sharedElement : sic.getSharedElements()) {
                System.out.print(sharedElement.getLocalPart() + " ");
            }
            System.out.println("\n");
        }
    }

    private void farewell() {
        System.out.println("Thank you for using the tool!");
    }

/*************************************************************************/
/**  Public methods
/*************************************************************************/

    public void start() {
        welcome();
        loadWsdl();
        dciMetric.computeMetric();
        printBasicReport();
        processNextActions();
        farewell();
    }

}
