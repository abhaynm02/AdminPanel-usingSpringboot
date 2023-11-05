package com.SpingLoginApplication.LoginApplication.Controller;

import com.SpingLoginApplication.LoginApplication.Model.UserEntity;
import com.SpingLoginApplication.LoginApplication.Service.CustomUserDetail;
import com.SpingLoginApplication.LoginApplication.Service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller
@SessionAttributes("user")
public class controller {
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register-form";
    }
    @GetMapping("admin-register")
    public String adminRegister(Model model){
        model.addAttribute("user", new UserEntity());
        return "admin-register";
    }
    @PostMapping("/create-userAdmin")
    public String userRegisterByAdmin(@RequestParam("cPassword") String cPassword,    @ModelAttribute UserEntity user, RedirectAttributes redirectAttributes) {
        if (userServiceI.isEmailExists(user.getEmail())){
            redirectAttributes.addFlashAttribute("message", "The email is exist");
            return "redirect:admin-register";
        }
        if (cPassword.equals(user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoll("USER");
            userServiceI.save(user);
            redirectAttributes.addFlashAttribute("message", "User added successfully");
            return "redirect:admin";
        }
        redirectAttributes.addFlashAttribute("message", "Conform you password");
        return "redirect:admin-register";
    }
//for register user
    @PostMapping("/create-user")
    public String userRegister( @RequestParam("cPassword")String cPassword,  @ModelAttribute UserEntity user, RedirectAttributes redirectAttributes) {
        if (userServiceI.isEmailExists(user.getEmail())){
            redirectAttributes.addFlashAttribute("message", "The email is exist");
            return "redirect:register";
        }
        if (cPassword.equals(user.getPassword())) {


            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoll("USER");
            userServiceI.save(user);
            redirectAttributes.addFlashAttribute("message", "User added successfully");
            return "redirect:login";
        }
        redirectAttributes.addFlashAttribute("message", "Conform you password");
        return "redirect:register";
    }
    @GetMapping("login")
    public String login(Principal principal, HttpSession session){
        if (principal!=null){
            UserEntity user =userServiceI.findByEmail(principal.getName());
            session.setAttribute("user",user);
            return "redirect:admin";
        }

        return "login";
    }
    @GetMapping("admin")
    public String adminPage(Model model ,HttpSession session){
      UserEntity user=(UserEntity) session.getAttribute("user");
   if (user!=null){
       model.addAttribute( "name",user.getName());
       return "admin";
   }
   return "redirect:login";
    }
    @GetMapping("home")
    public String userPage( HttpSession httpSession, Model model){
        UserEntity user=(UserEntity) httpSession.getAttribute("user");

         if (user!= null){
             model.addAttribute("name",user.getName());
             return "admin";
         }
        return "redirect:login";
    }
    @PostMapping("error")
    public String error(){
        return "error";
    }
    //in this method we can see the all users in register our web
    @GetMapping("/listOfUsers")
    public String userList( @RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size, Model model){
        Pageable pageable= PageRequest.of(page,size);
        Page<UserEntity> UserList=userServiceI.userList(pageable);
        model.addAttribute("users",UserList);
        return "listOfUsers";
    }
    // in this method admin can delete a particular user
    @PostMapping("deleteUser")
    public String deleteUser( @RequestParam("userId") Long id){
        userServiceI.deleteUser(id);
        return "redirect:/listOfUsers";
    }
    @PostMapping("makeAdmin")
    public String changeToAdmin(@RequestParam ("userId") Long Id){
        userServiceI.updateRole(Id,"ADMIN");

         return "redirect:/listOfUsers";
    }
    @PostMapping("makeUser")
    public String changeToUser(@RequestParam ("userId") Long Id){
        userServiceI.updateRole(Id,"USER");

        return "redirect:/listOfUsers";
    }
    @GetMapping("/search")
    public String searchUsers(@RequestParam("name") String name ,Model model,@RequestParam (defaultValue = "0")int page,@RequestParam(defaultValue = "5") int size){
        Pageable pageable=PageRequest.of(page,size);
        Page<UserEntity>userList=userServiceI.searchByName(name,pageable);
        model.addAttribute("users",userList);
        return  "listOfUsers";
    }
  @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
  }

}
