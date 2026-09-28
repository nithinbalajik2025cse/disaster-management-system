package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Resource;
import com.example.demo.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    public Resource createResource(Resource resource) {
        if (resource.getName() == null || resource.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Resource name is required");
        }
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, Resource updatedResource) {
        Resource existing = getResourceById(id);
        existing.setName(updatedResource.getName());
        existing.setCategory(updatedResource.getCategory());
        existing.setQuantity(updatedResource.getQuantity());
        existing.setUnit(updatedResource.getUnit());
        existing.setMinimumStock(updatedResource.getMinimumStock());
        existing.setLocation(updatedResource.getLocation());
        return resourceRepository.save(existing);
    }

    public void deleteResource(Long id) {
        Resource existing = getResourceById(id);
        resourceRepository.delete(existing);
    }

    public List<Resource> getLowStockResources() {
        return resourceRepository.findLowStockResources();
    }
}
