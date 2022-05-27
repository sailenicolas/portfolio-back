package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.*;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import ar.com.saile.demojwt.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final EducationService educationService;

    private final ProjectService projectService;

    private final ExperienceService experienceService;

    private final SoftSkillService softSkillService;

    private final AboutMeService aboutMeService;

    @PostMapping("/register")
    public AppUser addUser(@RequestBody AppUser appUser) {

        return userService.saveUser(appUser);
    }

    @GetMapping("/me")
    @Transactional
    public Object getAppUser(HttpServletRequest request) {

        AppUser appUser = userService.fetchCurrentUser(request);
        Optional<AppUser> appUserUs = userService.findByUsername(appUser);
        return appUserUs.orElse(null);
    }

    @PostMapping("/role")
    public ResponseEntity<AppRole> addRole(@RequestBody AppRole appRole) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(appRole));
    }

    @GetMapping("/users")
    public ResponseEntity<Page<AppUser>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/roleToUser")
    public ResponseEntity<?> RoleToUser(@RequestBody RoleToUser form) {

        userService.addRoleToUser(form.getRoleNane(), form.getUserName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken() {

    }

    @PostMapping("/experience/{id}")
    public void editExperience(@PathVariable Integer id) {

        System.out.println("id = " + id);
    }

    @PostMapping("/education/{id}")
    public void editEducation(@PathVariable Integer id) {

        System.out.println("id = " + id);
    }

    @PostMapping("/softskills/{id}")
    public void editSoftSkills(@PathVariable Integer id) {

        System.out.println("id = " + id);
    }

    @PostMapping("/aboutMe")
    public void editAboutMe() {


    }

    @PostMapping("/projects/{id}")
    public AppProject editProjects(@PathVariable Long id) {

        return projectService.findById(id).get();
    }

    @GetMapping("/experience/{id}")
    public void viewExperience(@PathVariable Long id) {

        experienceService.findById(id);

    }

    @GetMapping("/education/{id}")
    public AppEducation viewEducation(@PathVariable Long id) {

        return educationService.findById(id).orElseThrow(() -> new RecordNotFoundException("NOT FOUND"));
    }

    @GetMapping("/softskills/{id}")
    public AppSoftSkill viewSoftSkills(@PathVariable Long id) {

        return softSkillService.findById(id).get();
    }

    @GetMapping("/projects/{id}")
    public AppProject viewProjects(@PathVariable Long id) {

        return projectService.findById(id).get();
    }

    @GetMapping("/about")
    public Optional<AppAboutMe> viewAboutMe(HttpServletRequest request) {

        AppUser details = userService.fetchCurrentUser(request);
        return aboutMeService.findById(details.getId());
    }

}

@Data
class RoleToUser {

    private String roleNane;

    private String userName;
}