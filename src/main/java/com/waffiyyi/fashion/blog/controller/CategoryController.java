package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.service.CategoryService;
import com.waffiyyi.fashion.blog.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "This controller manages Category operations")
@OpenAPIDefinition(info = @Info(title = "CATEGORY CONTROLLER", version = "1.0",
                                description = "CATEGORY SERVICE API documentation"))
@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService cacategoryService;
  private final UserService userService;

  @Operation(summary = "Create Category", description = "Used to create design category")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CategoryDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO,
                                                    @RequestHeader("Authorization")
                                                    String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    CategoryDTO createdCategory = cacategoryService.saveCategory(categoryDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  @Operation(summary = "Update Category", description = "Used to update design category")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CategoryDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                    @PathVariable Long categoryId,
                                                    @RequestHeader("Authorization")
                                                    String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    CategoryDTO createdCategory = cacategoryService.updateCategory(categoryDTO,
                                                                   categoryId, user);
    return ResponseEntity.status(HttpStatus.OK).body(createdCategory);
  }

  @Operation(summary = "Get Category",
             description = "Used to get the Category of the specified ID")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CategoryDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
    CategoryDTO categoryDTO = cacategoryService.findCategoryById(categoryId);
    return ResponseEntity.ok(categoryDTO);
  }

  @Operation(summary = "Get Category", description = "Used to get all available Category")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CategoryDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @GetMapping("/get-all")
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categoryDTO = cacategoryService.getAllCategory();
    return ResponseEntity.ok(categoryDTO);
  }

  @Operation(summary = "Delete Category", description = "Used to delete category")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema())),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId,
                                             @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    cacategoryService.deleteCategory(categoryId, user);
    return ResponseEntity.noContent().build();
  }

}
