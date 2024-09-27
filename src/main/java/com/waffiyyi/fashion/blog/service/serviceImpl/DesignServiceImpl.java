package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.exception.UnAuthorizedException;
import com.waffiyyi.fashion.blog.exception.UserNotFoundException;
import com.waffiyyi.fashion.blog.repositories.CategoryRepository;
import com.waffiyyi.fashion.blog.repositories.DesignRepository;
import com.waffiyyi.fashion.blog.repositories.UserRepository;
import com.waffiyyi.fashion.blog.service.DesignService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesignServiceImpl implements DesignService {
  private final DesignRepository designRepository;
  public final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  @Override
  public DesignDTO saveDesign(DesignDTO designDTO, User user) {
    Design design = convertToEntity(designDTO, user);
    Design savedDesign = designRepository.save(design);
    return convertToDTO(savedDesign);
  }

  @Override
  public DesignDTO updateDesign(DesignDTO designDTO, Long designId, User user) {
    Design design = designRepository.findById(designId)
                       .orElseThrow(
                          () -> new ResourceNotFoundException("Design not found",
                                                              HttpStatus.NOT_FOUND));
    if (!Objects.equals(user.getId(), design.getDesignOwner().getId())) {
      throw new UnAuthorizedException("You are not authorized to delete a design you " +
                                         "don't own", HttpStatus.UNAUTHORIZED);
    }
    if (designDTO.getCategoryId() != null) {
      Category category =
         categoryRepository.findById(designDTO.getCategoryId()).orElseThrow(
            () -> new BadRequestException(
               "Category does not exist", HttpStatus.BAD_REQUEST));

      design.setCategory(category);
    }
    if (designDTO.getName() != null) {
      design.setName(designDTO.getName());
    }
    if (designDTO.getImages() != null) {
      design.setImages(designDTO.getImages());
    }
    if (design.getDescription() != null) {
      design.setDescription(designDTO.getDescription());
    }
    designRepository.save(design);
    return convertToDTO(design);
  }

  @Override
  public DesignDTO findDesignById(Long designId) {
    Design design =
       designRepository.findById(designId).orElseThrow(
          () -> new ResourceNotFoundException("Design not found", HttpStatus.NOT_FOUND));
    return convertToDTO(design);
  }

  @Override
  public void deleteDesign(Long designId, User user) {
    Design design =
       designRepository.findById(designId).orElseThrow(
          () -> new ResourceNotFoundException("Design not found", HttpStatus.NOT_FOUND));
    if (!Objects.equals(user.getId(), design.getDesignOwner().getId())) {
      throw new UnAuthorizedException("You are not authorized to delete a design you " +
                                         "don't own", HttpStatus.UNAUTHORIZED);
    }
    designRepository.deleteById(designId);
  }

  @Override
  @Transactional
  public String toggleLikeDesign(Long designId, User user) {
    Design design = designRepository.findById(designId)
                       .orElseThrow(
                          () -> new ResourceNotFoundException("Design not found",
                                                              HttpStatus.NOT_FOUND));
    if (design.getLikedByUsers().contains(user)) {
      design.setLikesCount(design.getLikesCount() - 1);
      design.getLikedByUsers().remove(user);
      designRepository.save(design);
      user.getLikedDesigns().remove(design);
      userRepository.save(user);
      return "Successfully unLiked";
    } else {
      design.setLikesCount(design.getLikesCount() + 1);
      design.getLikedByUsers().add(user);
      design.setLastActivity(LocalDateTime.now());
      designRepository.save(design);
      user.getLikedDesigns().add(design);
      userRepository.save(user);
      return "Successfully Liked";
    }
  }

  private DesignDTO convertToDTO(Design design) {
    DesignDTO designDTO = new DesignDTO();
    designDTO.setId(design.getId());
    designDTO.setName(design.getName());
    designDTO.setImages(design.getImages());
    designDTO.setDescription(design.getDescription());
    if (design.getCategory() != null) {
      designDTO.setCategoryId(design.getCategory().getId());
    } else {
      designDTO.setCategoryId(null);

    }
    designDTO.setLikesCount(design.getLikesCount());
    designDTO.setOwnerId(design.getDesignOwner().getId());
    return designDTO;
  }

  private Design convertToEntity(DesignDTO designDTO, User user) {
    Design design = new Design();
    design.setName(designDTO.getName());
    design.setImages(designDTO.getImages());
    design.setDescription(designDTO.getDescription());
    Category category = categoryRepository.findById(designDTO.getCategoryId())
                           .orElseThrow(() -> new ResourceNotFoundException(
                              "Category not found with id: " + designDTO.getCategoryId(),
                              HttpStatus.NOT_FOUND));
    design.setCategory(category);
    design.setLikesCount(0L);
    design.setDesignOwner(user);
    return design;
  }
}
