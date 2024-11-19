package com.example.demo.controller;

import com.example.demo.model.Attribute;
import com.example.demo.model.AttributeValue;
import com.example.demo.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes(); // Trả về danh sách thuộc tính, bao gồm cả giá trị thuộc tính
    }

    @GetMapping("/{id}")
    public Attribute getAttributeById(@PathVariable Integer id) {
        return attributeService.getAttributeById(id);
    }

    @PostMapping
    public Attribute createAttribute(@RequestBody Attribute attribute) {
        return attributeService.createAttribute(attribute);
    }

    @PutMapping("/{id}")
    public Attribute updateAttribute(@PathVariable Integer id, @RequestBody Attribute attribute) {
        return attributeService.updateAttribute(id, attribute);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAttribute(@PathVariable Integer id) {
        return attributeService.deleteAttribute(id);
    }

    @GetMapping("/{id}/values")
    public List<AttributeValue> getAttributeValues(@PathVariable Integer id) {
        return attributeService.getAttributeValues(id);
    }

    @GetMapping("/value/{id}")
    public AttributeValue getAttributeValueById(@PathVariable Integer id) {
        return attributeService.getAttributeValueById(id);
    }

    @PostMapping("/value")
    public AttributeValue createAttributeValue(@RequestBody AttributeValue attributeValue) {
        return attributeService.createAttributeValue(attributeValue);
    }


    @DeleteMapping("/value/{id}")
    public boolean deleteAttributeValue(@PathVariable Integer id) {
        return attributeService.deleteAttributeValue(id);
    }
}
