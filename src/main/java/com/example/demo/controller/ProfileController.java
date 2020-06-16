package com.example.demo.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.services.BoMt1Service;
import com.example.demo.services.ExportBoM;
import com.example.demo.services.InsertAltUoM;
import com.example.demo.services.InsertMaterialMaster;
import com.example.demo.services.InsertPreviousBoM;
import com.example.demo.services.SetBoMVersionService;
import com.example.demo.services.UserService;

@Controller
@RequestMapping("bomGeneration")
public class ProfileController {
	

	@Autowired
	private BoMt1Service bomt1;
	@Autowired
	private SetBoMVersionService bDetails;
	@Autowired 
	private ExportBoM exportBoMRec;
	@Autowired
	InsertPreviousBoM pBoM;
	
	public static String UPLOADED_FOLDER = System.getProperty("user.dir")+File.separator+"uploads";// = "C:\\files\\";
	public static String sLoggedUser;
	
    @GetMapping("index")
    public String index(){
        return "bomGeneration/index";
    }
    
    @PostMapping("uploadMultipleFiles")
	public  String uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam("module") String module, @RequestParam("reasonForChange") String reasonForChange,MultipartHttpServletRequest request, HttpServletResponse response) {

	    	
    	
    		String CAD= "";
	        String status = "";
	        String bModule = module;
	        System.out.println(UPLOADED_FOLDER);
	        bDetails.getBoMBasicDetails(bModule);
	        int nBoMv = bDetails.getnBoMVersion();
	        int nMaxI = bDetails.getnMaxItem();
	        for (int i = 0; i < files.length; i++) {
	        	 System.out.println(i);
	            MultipartFile file = files[i];
	            try {
	                byte[] bytes = file.getBytes();
	                String final_path= UPLOADED_FOLDER; //-- Hard coded path
	                if(i==0) {
	                	final_path = final_path+ File.separator+"R"+File.separator;
	                	CAD = "REVIT";
	                }else if(i==1){
	                	final_path = final_path + File.separator+"H"+File.separator;
	                	CAD = "HSB";
	                }
	                File dir = new File(final_path);
	    			if (!dir.exists())
	                    dir.mkdirs();
	    			Path path= Paths.get(final_path + file.getOriginalFilename());
	    			System.out.println(path.toString());
	    			Files.write(path, bytes);
	    			
		            status = status + "You successfully uploaded file=" + file.getOriginalFilename();
		            System.out.println(nMaxI);
		            nMaxI = bomt1.saveBoM1tData(path.toString(), CAD, bModule, nBoMv, nMaxI, sLoggedUser, reasonForChange);
		            System.out.println(nMaxI);
	                //request.setAttribute("mode", "MODE_UPLOAD");
	                
	    			
	            } catch (Exception e) {
	                status = status + "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
	                System.out.println(e.getMessage());
	                //request.setAttribute("errord", status);	
	            	//request.setAttribute("successd", null);
	            	//request.setAttribute("mode", "MODE_UPLOAD");
		        	//return "generateBoM";
	            }
	            
	            
	        }
		
	        try {
		        //String sap_path = "SAP\\";
		        String sap_path = UPLOADED_FOLDER+ File.separator+"SAP"+File.separator;//"\\src\\main\\resources\\SAP\\";
	            File dir = new File(sap_path);
	            if (!dir.exists())
	                dir.mkdirs();
	            String sapBoMFile = exportBoMRec.exportBoMRecords(module, nBoMv, sap_path);
	            String sapFileName = module+"_v"+nBoMv+".csv";
	            
	    		response.setContentType("text/html");
	    		PrintWriter out = response.getWriter();

	    		response.setContentType("APPLICATION/OCTET-STREAM");
	    		response.setHeader("Content-Disposition", "attachment; filename=\""	+ sapFileName + "\"");
	     
	    		FileInputStream fileInputStream = new FileInputStream(sapBoMFile);
	     
	    		int i;
	    		while ((i = fileInputStream.read()) != -1) {
	    			out.write(i);
	    		}
	    		fileInputStream.close();
	    		out.close();
	    		//request.setAttribute("errord", null);
            	//request.setAttribute("successd", "BoM has been successfully generated");
            	//request.setAttribute("mode", "MODE_UPLOAD");
		        //return "generateBoM";
	        }catch (Exception e) {

	        	//request.setAttribute("errord", e.getMessage());	
            	//request.setAttribute("successd", null);
            	//request.setAttribute("mode", "MODE_UPLOAD");
	        	//return "generateBoM";
            }
	        
	        return "index";
	        //return "file uploaded";
	    }
}
