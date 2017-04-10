import org.apache.jena.atlas.iterator.Iter;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.StmtIteratorImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;
import org.apache.jena.vocabulary.RDF;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by co17 on 28/03/2017.
 */
public class jenaj2 {

    static String now;

    public static void main (String[] args) {

        try {
            now = getNow();

            //String outputFile = "C:\\Users\\co17\\LocalStuff\\MyStuff\\Projects\\jenaj2";
            String outputFile = "G:\\ShareOne\\Cliff\\Dev\\jenaj2";


            String ns_DocStruct = "http://repositori.com/sw/onto/DocStruct.owl";
            OntModel mod_DocStruct = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            mod_DocStruct.read(ns_DocStruct);

            String ns_gate = "http://repositori.com/sw/onto/gate.owl";
            OntModel mod_gate = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            mod_gate.read(ns_gate);

            String ns_LassoingRhetoric = "http://repositori.com/sw/onto/LassoingRhetoric.owl";
            OntModel mod_LassoingRhetoric = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            mod_LassoingRhetoric.read(ns_LassoingRhetoric);


            String ns = "http://repositori.com/sw/onto/jj_" + now + ".owl";
            OntModel mod_new = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            //Resource resource = mod_new.createResource(ns);

            OntClass Doc  = mod_DocStruct.getOntClass(ns_DocStruct + "#Doc");
            OntClass Sentence  = mod_gate.getOntClass(ns_gate + "#Sentence");
            OntClass word  = mod_gate.getOntClass(ns_gate + "#word");

            OntClass t1 = mod_new.createClass(ns + "#test1");
            OntClass t2 = mod_new.createClass(ns + "#test2");
            Individual i1 = mod_new.createIndividual(ns + "#i1", t1);
            Individual i2 = mod_new.createIndividual(ns + "#d1", Doc);
            //Individual i3 = mod_new.createIndividual(ns + "#s1", Sentence);
            //Individual i4 = mod_new.createIndividual(ns + "#t1", t1);

            String outputformat = "RDF/XML-ABBREV"; //Turtle RDF/XML RDF/XML-ABBREV

            String rules = "[rule1: (?x rdf:type " + ns_DocStruct + "#Doc) -> (?x rdf:type " + ns_gate + "#word)]";
            //String rules = "[rule1: (?x rdf:type " + ns + "#test1) -> (?x rdf:type " + ns + "#test2)]";
            printIt(rules);

            Reasoner reasoner = new GenericRuleReasoner((Rule.parseRules(rules)));
            reasoner.setDerivationLogging(true);

            InfModel inf1 = ModelFactory.createInfModel(reasoner, mod_new);
            List<Statement> infs = new ArrayList<Statement>();

            StmtIterator iter = inf1.listStatements();
            while(iter.hasNext()){
                Statement stmt = iter.nextStatement();
                //printIt(stmt.getSubject().getLocalName() + " --- " + stmt.getPredicate().getLocalName() + " --- " + stmt.getObject().asResource().getLocalName());
                if(!mod_new.contains(stmt))
                {
                    infs.add(stmt);
                }
            }

            for(Statement s:infs)
            {
                mod_new.add(s);
            }

            //printIt("----- Doc Struct ----------------------------------------");
            //mod_DocStruct.write(System.out, outputformat);
            //printIt("");
            //printIt("----- gate ----------------------------------------");
            //mod_gate.write(System.out, outputformat);
            //printIt("");
            //printIt("");
            //mod_LassoingRhetoric.write(System.out, outputformat);
            //printIt("");

            printIt("----- new ----------------------------------------");
            mod_new.write(System.out, outputformat);

            FileWriter out = new FileWriter( outputFile + "\\jj_" + now + ".owl" );
            mod_new.write(out, outputformat);
            out.close();

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

    }

    public static void printIt(String text)
    {
        System.out.println(text);
    }

    public static String getNow()
    {
        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyyMMddHHmmsss").format(date);
        return modifiedDate;
    }

}
