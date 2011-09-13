package cz.vse.metric;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterface;
import cz.vse.metric.dci.ServiceInterfaceCombination;

import javax.xml.namespace.QName;
import java.util.Scanner;

/**
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class ConsoleApplication implements UserInterface {

    DCIMetric dciMetric = new DCIMetric();
    Scanner in = new Scanner(System.in);

/*************************************************************************/
/**  Private methods
/*************************************************************************/

    private void welcome() {
        System.out.println("Hello and welcome!");
        System.out.println("This tool serves for automatic DCI metric computation. \n");
    }


    private void loadWsdl() {
        // Ask the user for specifying the location of the WSDL to load
        System.out.println("For evaluate the service design, please load the WSDL definition");
        System.out.println("Insert the URI for your WSDL definition, use 'file:' prefix for local files");

        // Get the definition
        try {
            dciMetric.loadWsdl(in.nextLine());

            while (true) {
                System.out.println("Insert URI for loading another WSDL or leave the line blank to continue");

                if ("".equals(in.nextLine())) {
                    break;
                }
                
                dciMetric.loadWsdl(in.nextLine());
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unfortunately, the WSDL was not loaded successfully, please insert the url again");
        }

    }

    private void printBasicReport() {
        System.out.println("Metric was successfully computed!");
        System.out.println("---------------------------------");
        System.out.println("DCI  = " + dciMetric.getDataCouplingIndex());
        System.out.println("NDCI = " + dciMetric.getNormalizedDataCouplingIndex());
        System.out.println("---------------------------------\n");
        System.out.println("Additional information:");
        System.out.println("Total number of service operations:            " + dciMetric.getServiceInterfaces().size());
        System.out.println("Total number of combinations (r):              " + dciMetric.getServiceInterfaceCombinations().size() / 2);
        System.out.println("");
        System.out.println("Average number of complex types per operation: " + dciMetric.getAverageNumberOfComplexElementsPerOperation());
        System.out.println("Number of complex types that were reused:      " + dciMetric.getComplexTypesWithUsageCounts().size());
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
