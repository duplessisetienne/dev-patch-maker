package com.duplessis.etienne.patchmaker.service;

import com.duplessis.etienne.patchmaker.model.Patch;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//*
// V1.00.00    2019-12-27     E Du Plessis Initial Version
// */

public class FreeMakerConsoleEx {

    private static  final Logger LOGGER = LogManager.getLogger(FreeMakerConsoleEx.class);

    public void writeInstallScript(Patch patch) throws IOException, TemplateException {

        LOGGER.debug("Entered FreeMakerConsoleEx");
       //Setup the FreeMarker Config
        Configuration cfg = new Configuration(new Version("2.3.23"));

        cfg.setClassForTemplateLoading(FreeMakerConsoleEx.class,"/");

        Template template = cfg.getTemplate("InstallScript.ftl");

        Map<String, Object> templateData = new HashMap<>();

        //Propulate the Template values
        templateData.put("patchname", patch.getPatchName());
        templateData.put("versioncontrol", patch.getPatchVersion());
        templateData.put("patchnumber",patch.getPatchNumber());
        templateData.put("pacthdescription",patch.getPatchDescription());
        ArrayList<String> procedures = patch.getProcedure();
        templateData.put("procedures",procedures);
        ArrayList<String> function = patch.getFunction();
        templateData.put("functions",function);
        ArrayList<String> types = patch.getType();
        templateData.put("types",types);
        ArrayList<String> apex = patch.getApex();
        templateData.put("apexpages",apex);


         try {

             StringWriter out = new StringWriter();

             template.process(templateData,out);


             FileWriter fileWriter = new FileWriter("InstallScript_"+patch.getPatchNumber()+".sql");
             fileWriter.write(out.getBuffer().toString());
             fileWriter.close();
             out.flush();

         }catch (Exception ex)
         {
            LOGGER.error(ex.toString());
         }


    }

}
