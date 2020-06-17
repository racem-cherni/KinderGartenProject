package tn.esprit.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UploadController {
	
	@Autowired
	ParentRepository parentRepository;
	@Autowired
	UserRepository UserRepository;
	@Autowired
	UserServices userServices;
	@Autowired
	ChildRepository childRepository;
	@Autowired
	KinderGartenRepository kinderGartenRepository;
//	File dir = new File( "ads" + File.separator + type);
	
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = System.getProperty("user.dir")+"/src/main/webapp/resources/uploads/userProfileImage";

    @GetMapping("/")
    public void index() {
       // return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file
                                 ) {

        if (file.isEmpty()) {
        	System.out.println("ssssssssssssssssssssssssssssss");
        	
        	
        
        	
           // redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
           // return "redirect:uploadStatus";
        }

        try {
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // Get the file and save it somewhere
        	File dir = new File( UPLOADED_FOLDER);
			
        	if (!dir.exists())
				dir.mkdirs();
            byte[] bytes = file.getBytes();
      
            Path path = Paths.get(UPLOADED_FOLDER +File.separator+ file.getOriginalFilename());
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
      Parent p=userServices.currentUserJsf().getParent();
      p.setImage(file.getOriginalFilename());
      parentRepository.save(p);
      
           
            return "redirect:/pages/parent/parentprofil.jsf";

        } catch (IOException e) {
        	
            e.printStackTrace();
            return "redirect:/uploadStatus";
        }

      //  return "redirect:/uploadStatus";
    }
    @PostMapping("/uploadChild/{id}") // //new annotation since 4.3
    public String singleFileUploadChild(@RequestParam("file") MultipartFile file ,@PathVariable Long id     ) {

        if (file.isEmpty()) {
        	System.out.println("ssssssssssssssssssssssssssssss");
        	
        	
        
        	
           // redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
           // return "redirect:uploadStatus";
        }

        try {
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // Get the file and save it somewhere
        	File dir = new File( UPLOADED_FOLDER);
			
        	if (!dir.exists())
				dir.mkdirs();
            byte[] bytes = file.getBytes();
      
            Path path = Paths.get(UPLOADED_FOLDER +File.separator+ file.getOriginalFilename());
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
           Child c=childRepository.findById(id).get();
           c.setImage(file.getOriginalFilename()); 
            
            
     
           childRepository.save(c);
      
           
            return "redirect:/pages/parent/parentprofil.jsf";

        } catch (IOException e) {
        	
            e.printStackTrace();
            return "redirect:/pages/parent/parentprofil.jsf";
        }

      //  return "redirect:/uploadStatus";
    }
    
    @PostMapping("/logoutJsf") 
    public String LOGOUT() throws IOException {
		//
		// HttpServletRequest response =response. ;
		//
		// response.logout();
		//
		ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ex.getRequest();

		HttpServletResponse response = (HttpServletResponse) ex.getResponse();

		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			System.err.println("true");
		Optional<Cookie> token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token")).findFirst();

		token.get().setValue(null);

		response.addCookie(token.get());

		// response.sendRedirect("http://localhost:8081/login");

		return "/login/NewFile.xhtml?faces-redirect=true";
	}  
    @GetMapping("/uploadStatus")
    public void uploadStatus() {
      //  return "uploadStatus";
    }
    @PostMapping("/uploadK") // //new annotation since 4.3
    public String singleFileUploadKinder(@RequestParam("file") MultipartFile file
                                 ) {

        if (file.isEmpty()) {
        	System.out.println("ssssssssssssssssssssssssssssss");
        	
        	
        
        	
           // redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
           // return "redirect:uploadStatus";
        }

        try {
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // Get the file and save it somewhere
        	File dir = new File( UPLOADED_FOLDER);
			
        	if (!dir.exists())
				dir.mkdirs();
            byte[] bytes = file.getBytes();
      
            Path path = Paths.get(UPLOADED_FOLDER +File.separator+ file.getOriginalFilename());
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
      KinderGarten k=userServices.currentUserJsf().getKindergarten();
      k.setImage(file.getOriginalFilename());
      kinderGartenRepository.save(k);
      
           
            return "redirect:/pages/kindergarten/showProfilKinder.jsf";

        } catch (IOException e) {
        	
            e.printStackTrace();
            return "redirect:/uploadStatus";
        }

      //  return "redirect:/uploadStatus";
    }
}
