import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.List;

/**
 * Created by co17 on 28/03/2017.
 */
public class jenaj2 {

    public static void main (String[] args) {

        try {
            OntModel mod_DocStruct = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            mod_DocStruct.read("http://repositori.com/sw/onto/DocStruct.owl");

            OntModel mod_gate = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            mod_gate.read("http://repositori.com/sw/onto/gate.owl");

            List<OntClass> oc = mod_DocStruct.listClasses().toList();
            for(int i = 0 ; i<oc.size() ; i++)
            {
                System.out.println(oc.get(i).getLocalName());
            }

            oc = mod_gate.listClasses().toList();
            for(int i = 0 ; i<oc.size() ; i++)
            {
                System.out.println(oc.get(i).getLocalName());
            }


            OntModel mod_new = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);

            OntClass Doc  = mod_DocStruct.getOntClass("Doc");
            OntClass Sentence  = mod_gate.getOntClass("Sentence");

            Individual i1 = mod_new.createIndividual(Doc);

            System.out.println(mod_new.toString());


        } catch (Exception exc) {
            System.out.println(exc.getStackTrace());
        }

    }

}
