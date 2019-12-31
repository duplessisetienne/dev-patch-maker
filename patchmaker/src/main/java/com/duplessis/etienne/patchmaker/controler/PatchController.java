package com.duplessis.etienne.patchmaker.controler;

import com.duplessis.etienne.patchmaker.model.Patch;
import com.duplessis.etienne.patchmaker.service.FreeMakerConsoleEx;
import com.duplessis.etienne.patchmaker.service.PatchService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

//*
// V1.00.00    2019-12-27     E Du Plessis Initial Version
// */

@RestController
public class PatchController {

    private static final Logger LOGGER = LogManager.getLogger(PatchController.class);
    @Autowired
    Patch patch;

    ResponseEntity eMessage = null;

    PatchService patchService = new PatchService();

    //Create a patch and write to file
    @GetMapping("/patch/write/{fileName},{filePath}")
    public ResponseEntity<Resource> createPatch(@PathVariable String fileName, @PathVariable String filePath){

        LOGGER.debug("Entering PatchController.createPatch");

        try{
            patchService.writeToFile(fileName,filePath);
            eMessage = ResponseEntity.ok().header(fileName +" written to "+filePath).body(null);
        }catch (Exception ex){

            LOGGER.error(ex.toString());
        }
        LOGGER.debug("Exiting PatchController.createPatch");
        return eMessage;
    }

//Download a file created
//Sample Call
//http://localhost:8080/patch/download?filename=InstallScript_3465.sql
    @GetMapping("/patch/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "filename") String fileName) throws IOException{


        try{

            LOGGER.debug("Entering PatchController.downloadFile");

            File file = new File(fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Disposition", "attachment; filename="+fileName);


            eMessage = ResponseEntity.ok().headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

            LOGGER.debug("Response: "+eMessage.toString());

        }catch (Exception ex){

            LOGGER.error(ex.toString());
        }


        LOGGER.debug("Exiting PatchController.downloadFile");

        return eMessage;

    }

//Create a patch based on the Template for the InstallScript
//Sample call
// http://localhost:8080/patch/write/template?patchNumber=3465&patchName=PATCH.3465.467.ZAFMVNX.VX_DataSyncFixes&patchDescription=Compile%20VX_STATUS_PROFILE&patchVersion=03-Dec-2019%20%20%20%20L%20Harland%20%20%20%20%20%20Initial%20Version&procedure=test,vx_dup,vx_interconnect&function=tets,test2&type=test,checks,vx_type&apex=f102_page_34

    @GetMapping("/patch/write/template")
    public ResponseEntity<Resource> writeWithTemplate(@RequestParam(value = "patchNumber") String patchNumber,
                                                      @RequestParam(value = "patchName") String patchName,
                                                      @RequestParam(value = "patchDescription") String patchDescription,
                                                      @RequestParam(value = "patchVersion") String patchVersion,
                                                      @RequestParam(value = "procedure") ArrayList<String> procedure,
                                                      @RequestParam(value = "function") ArrayList<String> function,
                                                      @RequestParam(value = "type") ArrayList<String> type,
                                                      @RequestParam(value = "apex") ArrayList<String> apex) throws IOException, TemplateException {

        try{

            LOGGER.debug("Entering PatchController.writeWithTemplate");

            Patch patch = new Patch(patchNumber,patchName,patchDescription,patchVersion,procedure,function,type,apex);
            FreeMakerConsoleEx template = new FreeMakerConsoleEx();
            template.writeInstallScript(patch);

            eMessage = ResponseEntity.ok().header("success").body(null);
            LOGGER.debug("Response: "+eMessage.toString());


        }catch (Exception ex)
        {
            LOGGER.error(ex.toString());
        }


        LOGGER.debug("Exiting PatchController.writeWithTemplate");
        return eMessage;
    }
}
