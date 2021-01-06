package tacos.starter_jpa.data;

import org.springframework.data.repository.CrudRepository;

import tacos.starter_jpa.Taco;

public interface TacoRepo extends CrudRepository<Taco, String> {
}
