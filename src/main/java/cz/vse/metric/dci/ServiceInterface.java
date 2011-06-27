package cz.vse.metric.dci;

import org.ow2.easywsdl.schema.api.*;
import org.ow2.easywsdl.schema.impl.AllImpl;
import org.ow2.easywsdl.schema.impl.ChoiceImpl;
import org.ow2.easywsdl.schema.impl.SequenceImpl;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.ExplicitGroup;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ServiceInterface
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class ServiceInterface {

    private QName serviceQName;

    private List<Element> messages = new ArrayList<Element>();

    /**
     * When the ServiceInterface is initialized, this list contains
     * all the non-anonymous complex types used by the interface
     */
    private List<QName> nonAnonymousComplexTypes = new ArrayList<QName>();

    /**
     * Map containing list of complex types that are used in the
     * other interfaces as well, with usage counts
     */
    private Map<QName, Integer> sharedComplexTypeMap = new HashMap<QName, Integer>();

    /**
     * When the ServiceInterface is initialized, this attribute equals
     * to the total number of distinct complex types used by the interface
     * (including anonymous)
     */
    private int numberOfComplexTypes;


    /**
     * Extracts all XSComplexType definitions form given Message definition
     * @param element to be processed
     */
    private void getComplexTypesFromElement(Element element) {

        if (element != null) {

            Type type = element.getType();

            // we are interested only in ComplexTypes
            if (type instanceof ComplexType) {
                ComplexType complexType = (ComplexType) type;

                // we have to check whether we have discovered the ComplexType for the first time
                // => either it is anonymous or it is named, but it is not yet in the collection
                // if no, there is no need for putting it to the map again nor to go deeper in the structure
                if (complexType.getQName() == null || !nonAnonymousComplexTypes.contains(type.getQName())) {

                    numberOfComplexTypes++;

                    // We are not interested in anonymous ComplexTypes, as they
                    // cannot be reused anywhere else
                    if (complexType.getQName() != null) {
                        nonAnonymousComplexTypes.add(type.getQName());
                    }

                    // Dig deeper in the structure
                    if (complexType.hasSequence()) {
                        processSequence(complexType.getSequence());
                    } else if (complexType.hasChoice()) {
                        processChoice(complexType.getChoice());
                    } else if (complexType.hasAll()) {
                        All all = complexType.getAll();
                        for (Element innerElement : all.getElements()) {
                            getComplexTypesFromElement(innerElement);
                        }
                    }
                }
            }
        }
    }

/*************************************************************************/
/** Workaround for the case that choice or sequence element contains
/** another choice or sequence elements.
/*************************************************************************/

    private void processChoice(Choice choice) {
        for (Element innerElement : choice.getElements()) {
            getComplexTypesFromElement(innerElement);
        }

        ChoiceImpl impl = (ChoiceImpl) choice;
        for (Object object : impl.getModel().getParticle()) {
            if (object instanceof JAXBElement) {
                JAXBElement element = (JAXBElement) object;
                if (ExplicitGroup.class.equals(element.getDeclaredType())) {
                    if ("choice".equals(element.getName().getLocalPart())) {
                        processChoice(new ChoiceImpl(((ExplicitGroup) element.getValue()), impl.getParent()));
                    } else if ("sequence".equals(element.getName().getLocalPart())) {
                        processSequence(new SequenceImpl(((ExplicitGroup) element.getValue()), impl.getParent()));
                    }
                }
            }
        }
    }

    private void processSequence(Sequence sequence) {
        for (Element innerElement : sequence.getElements()) {
            getComplexTypesFromElement(innerElement);
        }

        SequenceImpl impl = (SequenceImpl) sequence;
        for (Object object : impl.getModel().getParticle()) {
            if (object instanceof JAXBElement) {
                JAXBElement element = (JAXBElement) object;
                if (ExplicitGroup.class.equals(element.getDeclaredType())) {
                    if ("choice".equals(element.getName().getLocalPart())) {
                        processChoice(new ChoiceImpl(((ExplicitGroup) element.getValue()), impl.getParent()));
                    } else if ("sequence".equals(element.getName().getLocalPart())) {
                        processSequence(new SequenceImpl(((ExplicitGroup) element.getValue()), impl.getParent()));
                    }
                }
            }
        }
    }

/*************************************************************************/
/**  Public methods
/*************************************************************************/


    public void processComplexElements() {
        for(Element message : messages) {
            getComplexTypesFromElement(message);
        }
    }

    public void processSharedComplexElement(QName complexElement) {
        Integer counter = this.getSharedComplexTypeMap().get(complexElement);
        if (counter != null) {
            this.getSharedComplexTypeMap().put(complexElement, counter + 1);
        } else {
            this.getSharedComplexTypeMap().put(complexElement, 1);
        }
    }

/*************************************************************************/
/**  Getters / Setters
/*************************************************************************/

    public List<QName> getNonAnonymousComplexTypes() {
        return nonAnonymousComplexTypes;
    }

    public int getNumberOfComplexTypes() {
        return numberOfComplexTypes;
    }

    public List<Element> getMessages() {
        return messages;
    }

    public QName getServiceQName() {
        return serviceQName;
    }

    public void setServiceQName(QName serviceQName) {
        this.serviceQName = serviceQName;
    }

    public Map<QName, Integer> getSharedComplexTypeMap() {
        return sharedComplexTypeMap;
    }

}
