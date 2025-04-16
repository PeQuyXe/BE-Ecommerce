package com.example.demo.service;

import com.example.demo.model.Attribute;
import com.example.demo.model.AttributeValue;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.AttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    public Attribute getAttributeById(Integer id) {
        return attributeRepository.findById(id).orElse(null);
    }

    public Attribute createAttribute(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public Attribute updateAttribute(Integer id, Attribute attribute) {
        if (attributeRepository.existsById(id)) {
            attribute.setId(id);
            return attributeRepository.save(attribute);
        }
        return null;
    }

    public boolean deleteAttribute(Integer id) {
        if (attributeRepository.existsById(id)) {
            attributeRepository.deleteById(id);
            attributeValueRepository.deleteByAttributeId(id);
            return true;
        }
        return false;
    }

    public List<AttributeValue> getAttributeValues(Integer attributeId) {
        return attributeValueRepository.findByAttributeId(attributeId);
    }

    public AttributeValue getAttributeValueById(Integer id) {
        return attributeValueRepository.findById(id).orElse(null);
    }

    public AttributeValue createAttributeValue(AttributeValue attributeValue) {
        return attributeValueRepository.save(attributeValue);
    }

    public boolean deleteAttributeValue(Integer id) {
        if (attributeValueRepository.existsById(id)) {
            attributeValueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
