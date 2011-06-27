package cz.vse.metric.dci;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ServiceInterfaceCombination
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class ServiceInterfaceCombination {

    private ServiceInterface interfaceA;
    private ServiceInterface interfaceB;

    private List<QName> sharedElements = new ArrayList<QName>();

    public ServiceInterfaceCombination(ServiceInterface a, ServiceInterface b) {
        interfaceA = a;
        interfaceB = b;
        init();
    }

    public void init() {
        // We compare each complex type from interface A to each complex type from interface B
        for (QName complexTypeA : interfaceA.getNonAnonymousComplexTypes()) {
            for (QName complexTypeB : interfaceB.getNonAnonymousComplexTypes()) {
                if (complexTypeA.equals(complexTypeB)) {
                    sharedElements.add(complexTypeA);
                    interfaceA.processSharedComplexElement(complexTypeA);
                    break;
                }
            }
        }
    }

/*************************************************************************/
/**  Public methods
/*************************************************************************/

    public int getNumberOfSharedElements() {
        return sharedElements.size();
    }

    public List<QName> getSharedElements() {
        return sharedElements;
    }

    public ServiceInterface getInterfaceA() {
        return interfaceA;
    }

    public void setInterfaceA(ServiceInterface interfaceA) {
        this.interfaceA = interfaceA;
    }

    public ServiceInterface getInterfaceB() {
        return interfaceB;
    }

    public void setInterfaceB(ServiceInterface interfaceB) {
        this.interfaceB = interfaceB;
    }
}
