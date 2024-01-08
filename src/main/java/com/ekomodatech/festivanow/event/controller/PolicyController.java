package com.ekomodatech.festivanow.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

import com.ekomodatech.festivanow.event.entity.Policy;
import com.ekomodatech.festivanow.event.repository.PolicyRepository;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    private PolicyRepository policyRepository;

    @GetMapping("/{id}")
    
    public Policy findPolicy(@PathVariable Long id) {
        try {
            Policy policy = policyRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found"));
            return policy;
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @GetMapping("/list")
    
    public List<Policy> listPolicies() {
        try {
            List<Policy> policies = policyRepository.findAll();
            return policies;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @PostMapping("/create")
    
    public Policy createPolicy(@RequestBody Policy newPolicy) {
        try {
            return policyRepository.save(newPolicy);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @DeleteMapping("/delete/{id}")
    
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        try {
            policyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @PostMapping("/save")
    public String savePolicy(@ModelAttribute Policy policy, Model model) {
        try {
            policyRepository.save(policy);
            return "redirect:/crud/read";
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @GetMapping("/update/{id}")
    
    public String update(@PathVariable Long id, Model model) {
        try {
            Policy policy = policyRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found"));
            model.addAttribute("policy", policy);
            return "update";
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
        }
    }

    @GetMapping("/")
    String index() {
        return "index";
    }
}
