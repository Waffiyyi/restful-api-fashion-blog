package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.repositories.CategoryRepository;
import com.waffiyyi.fashion.blog.repositories.DesignRepository;
import com.waffiyyi.fashion.blog.service.DesignService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignServiceImpl implements DesignService {
  private final DesignRepository designRepository;
  public final CategoryRepository categoryRepository;

  @Override
  public DesignDTO saveDesign(DesignDTO designDTO) {
    Design design = convertToEntity(designDTO);
    Design savedDesign = designRepository.save(design);
    return convertToDTO(savedDesign);
  }

  @Override

  public DesignDTO findDesignById(Long designId) {
    Design design = designRepository.getDesignEntityById(designId);
    return convertToDTO(design);
  }

  @Override

  public void deleteDesign(Long designId) {
    designRepository.deleteById(designId);
  }

  @Override

  public void likeDesign(Long designId) {
    Design design = designRepository.getDesignEntityById(designId);
    design.setLikesCount(design.getLikesCount() + 1);
    designRepository.save(design);
  }

  @Override

  public void unlikeDesign(Long designId) {
    Design design = designRepository.getDesignEntityById(designId);
    design.setLikesCount(design.getLikesCount() - 1);
    designRepository.save(design);
  }

  private DesignDTO convertToDTO(Design design) {
    DesignDTO designDTO = new DesignDTO();
    designDTO.setName(design.getName());
    designDTO.setDescription(design.getDescription());
    designDTO.setCategory(design.getCategory().getId());
    designDTO.setLikesCount(design.getLikesCount());
    return designDTO;
  }

  private Design convertToEntity(DesignDTO designDTO) {
    Design design = new Design();
    design.setName(designDTO.getName());
    design.setDescription(designDTO.getDescription());
    Category category = categoryRepository.findById(designDTO.getCategory())
                           .orElseThrow(() -> new EntityNotFoundException(
                              "Category not found with id: " + designDTO.getCategory()));
    design.setCategory(category);
    design.setLikesCount(0);
    return design;
  }
}
