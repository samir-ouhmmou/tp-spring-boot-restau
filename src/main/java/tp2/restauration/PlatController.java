package tp2.restauration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plats")
@CrossOrigin(origins = "http://localhost:4200")
public class PlatController {

    private final List<Plat> plats = new ArrayList<>();
    private long compteur = 4;

    public PlatController() {
        plats.add(new Plat(1L, "Tajine de poulet", "Poulet, olives et citron confit", 65.0, "Plat"));
        plats.add(new Plat(2L, "Salade marocaine", "Tomates, concombres, oignons", 25.0, "Entrée"));
        plats.add(new Plat(3L, "Pastilla au lait", "Dessert traditionnel", 30.0, "Dessert"));
    }

    // READ : tous les plats
    @GetMapping
    public List<Plat> lister() {
        return plats;
    }

    // READ : un plat
    @GetMapping("/{id}")
    public ResponseEntity<Plat> trouver(@PathVariable Long id) {
        for (Plat p : plats) {
            if (p.getId().equals(id)) {
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plat creer(@RequestBody Plat plat) {
        plat.setId(compteur++);
        plats.add(plat);
        return plat;
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Plat> modifier(@PathVariable Long id, @RequestBody Plat donnees) {
        for (Plat p : plats) {
            if (p.getId().equals(id)) {
                p.setNom(donnees.getNom());
                p.setDescription(donnees.getDescription());
                p.setPrix(donnees.getPrix());
                p.setCategorie(donnees.getCategorie());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        boolean supprime = plats.removeIf(p -> p.getId().equals(id));
        return supprime ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}