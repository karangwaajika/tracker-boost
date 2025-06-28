package com.lab.trackerboost.controller;

import com.lab.trackerboost.dto.project.ProjectRequestDto;
import com.lab.trackerboost.model.ProjectEntity;
import com.lab.trackerboost.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/projects")
@Tag(name = "Project Controller", description = "Manage all the Project's urls")
public class ProjectController {
    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping(name = "add_project", path = "/add")
    @Operation(summary = "Create project",
            description = "This request inserts a project to the database and returns " +
                          "the inserted project ")
    public ResponseEntity<ProjectEntity> addProject(@RequestBody ProjectRequestDto project){
        ProjectEntity savedProject = this.projectService.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @GetMapping(name = "view_project_by_id", path = "/view/{id}")
    @Operation(summary = "View Project",
            description = "Search and view only one project using project ID")
    public ResponseEntity<ProjectEntity> viewProject(@PathVariable Long id){
        Optional<ProjectEntity> project = this.projectService.findProjectById(id);

        return ResponseEntity.status(HttpStatus.OK).body(project.get());
    }

    @GetMapping(name = "view_projects", path = "/view")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public Page<ProjectEntity> viewProjects(Pageable pageable){
        return this.projectService.findAll(pageable);
    }

    @PatchMapping(name = "update_project", path = "/update/{id}")
    @Operation(summary = "Update Project",
            description = "The project can be updated partially, " +
                          "it's doesn't necessary required " +
                          "all the fields to be updated")
    public ResponseEntity<ProjectEntity> updateProject(@RequestBody ProjectEntity project,
                                                            @PathVariable Long id){

        ProjectEntity updatedProject = this.projectService.partialUpdate(project, id);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
    }

    @DeleteMapping(name = "delete_project", path = "/delete")
    @Operation(summary = "Delete Project",
            description = "The project is delete using its id that is retrieved " +
                          "as a query parameter from the url")
    public ResponseEntity<?> deleteProject(@RequestParam Long id){
        this.projectService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Project deleted successfully"));
    }

    @GetMapping(name = "view_projects", path = "/viewAll")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public ResponseEntity<List<ProjectEntity>> viewAllProjects(){
        List<ProjectEntity> projects = this.projectService.findAllProject();
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping(name = "view_projects_no_task", path = "/viewNoTask")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public List<ProjectEntity> viewProjectsWithNoTask(Pageable pageable){
        return this.projectService.findProjectsWithoutTasks();
    }

}
