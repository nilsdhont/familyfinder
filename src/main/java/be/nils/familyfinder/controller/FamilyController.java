package be.nils.familyfinder.controller;

import be.nils.familyfinder.model.Family;
import be.nils.familyfinder.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/api/family")
public class FamilyController {

    @Autowired
    private FamilyRepository familyRepository;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Family> get(@PathVariable long id) {
        return familyRepository.findById(id)
                .map(family -> ResponseEntity.ok().body(family))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<Family>> getAllFamilies() {
        List<Family> families = familyRepository.findAll();
        if (families == null || families.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(families);
    }

}
