package tacos.starter_jpa.data;

import org.springframework.data.repository.CrudRepository;
import tacos.starter_jpa.Ingredient;

public interface IngredientRepo extends CrudRepository<Ingredient, String> {   
}
