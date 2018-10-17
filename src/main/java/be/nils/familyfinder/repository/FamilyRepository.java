package be.nils.familyfinder.repository;

import be.nils.familyfinder.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    Family findByAdditionalInfoStartsWithIgnoreCase(String filterText);
}
