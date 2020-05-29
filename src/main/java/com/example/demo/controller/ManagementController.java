package com.example.demo.controller;

import java.io.File;
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
@RequestMapping("management")
public class ManagementController {

    @GetMapping("index")
    public String index(){
        return "management/index";
    }
    
    private static String UPLOADED_FOLDER = "C:\\files\\";
	public static String sLoggedUser;
	//private static String UPLOADED_FOLDER = "\\\\10.30.1.10\\Coram-St-Storage\\Technology\\08. SAP - BIM integration\\files\\";
	//private static String UPLOADED_FOLDER = "Classpath:config/";
	@Autowired
	private InsertMaterialMaster insertMM;
	@Autowired
	private InsertAltUoM altUoM;
	@Autowired
	InsertPreviousBoM pBoM;
	@Autowired
	private SetBoMVersionService bDetails;
	
	@PostMapping("uploadprevBoM")
	public  String uploadPrevBoM(@RequestParam("file") MultipartFile file, @RequestParam("module") String module, MultipartHttpServletRequest request, HttpServletResponse response) {

			String status = "";
	        String bModule = module;
	        System.out.println(UPLOADED_FOLDER);
	        bDetails.getBoMBasicDetails(bModule);
	        int nBoMv = bDetails.getnBoMVersion();
	        System.out.println(nBoMv);
	        
	        if(file.isEmpty()) {
	        	System.out.println("File not present");
	        }
	            try {
	            	boolean bSuccess = false;
	                byte[] bytes = file.getBytes();
	                String final_path = UPLOADED_FOLDER; //-- Hard coded path
	                //final_path = final_path + "\\SAP\\";
	                final_path = "SAP\\";
	                File dir = new File(final_path);
	                if (!dir.exists())
	                    dir.mkdirs();
	    			Path path = Paths.get(final_path + file.getOriginalFilename());
	    			Files.write(path, bytes);
		            status = status + "You successfully uploaded file=" + file.getOriginalFilename();
		            System.out.println(path.toString());
		            bSuccess = pBoM.insertBoMs(path.toString(), module, sLoggedUser, nBoMv);
		          /*  if(bSuccess) {
		            	
		            	request.setAttribute("errora", null);	
		            	request.setAttribute("successa", "BoM has been successfully uploaded");	
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            	
		            }else {
		            	
		            	request.setAttribute("errora", "Please validate BOM file, it is incorrect. Please ask System Administrator for more details");	
		            	request.setAttribute("successa", null);
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            }*/
	                
	                
	    			
	            } catch (Exception e) {
	                status = status + "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
	                System.out.println(e.getMessage());
	                //request.setAttribute("mode", "MODE_ERR_MSG");
	                return "management/index";
	            }
	        
	            return "management/index";

	    }
    
    @PostMapping("uploadMm")
	public  String uploadMm(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) {

			String status = "";
			boolean bSuccess = false;
	        System.out.println(UPLOADED_FOLDER);
	        
	        if(file.isEmpty()) {
	        	System.out.println("File not present");
	        }

	            try {
	                byte[] bytes = file.getBytes();
	                String final_path = UPLOADED_FOLDER; //-- Hard coded path
	                //final_path = final_path + "\\MM\\";
	                final_path =  "MM\\";
	                File dir = new File(final_path);
	                if (!dir.exists())
	                    dir.mkdirs();
	    			Path path = Paths.get(final_path + file.getOriginalFilename());
	    			Files.write(path, bytes);
		            status = status + "You successfully uploaded file=" + file.getOriginalFilename();
		            System.out.println(path.toString());
		            bSuccess = insertMM.insertMaterials(path.toString());
		            /*if(bSuccess) {
		            	
		            	request.setAttribute("errorb", null);	
		            	request.setAttribute("successb", "Material Master has been successfully uploaded");	
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            	
		            }else {
		            	
		            	request.setAttribute("errorb", "Please validate Material Master file, it is incorrect. Please ask System Administrator for more details");	
		            	request.setAttribute("successb", null);
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            }*/
	                
	                
	    			
	            } catch (Exception e) {
	                status = status + "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
	                System.out.println(e.getMessage());
	               /* request.setAttribute("mode", "MODE_ERR_MSG");*/
	                return "management/index";
	            }

	        
	        return "management/index";
	    }

	@PostMapping("uploadAltUoM")
	public  String uploadAltUoM(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) {

		String status = "";
		boolean bSuccess = false;
        System.out.println(UPLOADED_FOLDER);
        
        if(file.isEmpty()) {
        	System.out.println("File not present");
        }
	            try {
	            	byte[] bytes = file.getBytes();
	                String final_path = UPLOADED_FOLDER; //-- Hard coded path
	                //final_path = final_path + "\\auUoM\\";
	                final_path =  "auUoM\\";
	                File dir = new File(final_path);
	                if (!dir.exists())
	                    dir.mkdirs();
	    			Path path = Paths.get(final_path + file.getOriginalFilename());
	    			Files.write(path, bytes);
		            status = status + "You successfully uploaded file=" + file.getOriginalFilename();
		            System.out.println(path.toString());
		            bSuccess = altUoM.insertAltUoMMaterials(path.toString());
		            /*if(bSuccess) {
		            	
		            	request.setAttribute("errorc", null);	
		            	request.setAttribute("successc", "Material Master has been successfully uploaded");	
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            	System.out.println("Success");
		            	
		            }else {
		            	
		            	request.setAttribute("errorc", "Please validate Alternative UOM file, it is incorrect. Please ask System Administrator for more details");	
		            	request.setAttribute("successc", null);
		            	request.setAttribute("mode", "MODE_REF_DATA");
		            	System.out.println("Error");
		            }*/
	    			
	            } catch (Exception e) {
	                status = status + "Failed to upload " + file.getOriginalFilename()+ " " + e.getMessage();
	                System.out.println(e.getMessage());
	                //request.setAttribute("mode", "MODE_ERR_MSG");
	                return "management/index";
	            }
	            
	        return "management/index";

	    }
}
