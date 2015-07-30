package dkd;

import gate.Annotation;
import gate.AnnotationSet;
import gate.GateConstants;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.ProcessingResource;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.InvalidOffsetException;

@CreoleResource(name = "RDFa Lite Importer", comment = "Import Annotations from RDFa Lite", tool = true, autoinstances = @AutoInstance, icon="")
public class RdfaImporter extends AbstractLanguageAnalyser implements ProcessingResource {
    private static final long serialVersionUID = -2874636782869415162L;

    /**
     * Run the resource.
     * @throws ExecutionException
     */
    public void execute() throws ExecutionException {
        AnnotationSet input = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
        AnnotationSet output = document.getAnnotations();

        try
        {
            for (Annotation annotation : input)
            {
                if (annotation.getFeatures().get("resource") == null)
                    continue;

                FeatureMap params = Factory.newFeatureMap();
                params.put("inst", annotation.getFeatures().get("resource").toString());



                if (annotation.getFeatures().get("typeof") != null)
                    params.put("dbpSpecificClass", annotation.getFeatures().get("typeof").toString());

                Long start = annotation.getStartNode().getOffset();
                Long end = annotation.getEndNode().getOffset();
                output.add(start, end, "Mention", params);
            }
        }
        catch(InvalidOffsetException e) {
            e.printStackTrace();
        }
    }
}
