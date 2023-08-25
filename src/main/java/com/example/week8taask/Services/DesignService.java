package com.example.week8taask.Services;

import com.example.week8taask.DTOs.DesignDTO;
import com.example.week8taask.Entities.Category;
import com.example.week8taask.Entities.Design;
import com.example.week8taask.Repositories.CategoryRepo;
import com.example.week8taask.Repositories.DesignRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignService {
    private DesignRepo designRepo;
    public CategoryRepo categoryRepo;
    @Autowired
    public DesignService(DesignRepo designRepo, CategoryRepo categoryRepo){
        this.designRepo = designRepo;
        this.categoryRepo = categoryRepo;
    }
    public DesignDTO saveDesign(DesignDTO designDTO){
     Design design = convertToEntity(designDTO);
     Design savedDesign = designRepo.save(design);
     return convertToDTO(savedDesign);
    }

    public DesignDTO findDesignById(Long designId){
        Design design = designRepo.getDesignEntityById(designId);
        return convertToDTO(design);
    }

    public void deleteDesign(Long designId){
        designRepo.deleteById(designId);
    }

    public void likeDesign(Long designId){
        Design design = designRepo.getDesignEntityById(designId);
        design.setLikesCount(design.getLikesCount() + 1);
        designRepo.save(design);
    }
    public void unlikeDesign(Long designId){
        Design design = designRepo.getDesignEntityById(designId);
        design.setLikesCount(design.getLikesCount()-1);
        designRepo.save(design);
    }

    private DesignDTO convertToDTO(Design design) {
        DesignDTO designDTO = new DesignDTO();
        designDTO.setId(design.getId());
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
        design.setId(designDTO.getId());
        Category category = categoryRepo.findById(designDTO.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + designDTO.getCategory()));
        design.setCategory(category);
        design.setLikesCount(0);
        return design;
    }
}
