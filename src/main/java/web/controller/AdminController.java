package web.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.dao.RoleDAO;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleDAO roleDAO;


    public AdminController(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;

    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("allUsersList", userService.allUsers());
        return "admin";
    }

    @GetMapping(value = "/new")
    public ModelAndView newUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("rolesList", roleDAO.getRoleSet());
        return modelAndView;
    }

    @PostMapping(value = "")
    public String newUserPost(@ModelAttribute("user") User user,
                              @RequestParam("selectedRoles") String[] selectedRoles) {
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("rolesList", roleDAO.getRoleSet());
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    public String editUserPatch(@ModelAttribute("user") User user, HttpServletRequest req) {
        String[] selectedRoles = req.getParameterValues("selectedRoles");
        userService.update(user, selectedRoles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.getById(id));
        return "redirect:/admin";
    }


}