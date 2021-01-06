package tacos.starter_jpa.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.starter_jpa.Ingredient;
import tacos.starter_jpa.Order;
import tacos.starter_jpa.Ingredient.Type;
import tacos.starter_jpa.data.IngredientRepo;
import tacos.starter_jpa.data.TacoRepo;
import tacos.starter_jpa.data.UserRepo;
import tacos.starter_jpa.Taco;
import tacos.starter_jpa.User;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {

    private IngredientRepo ingredientRepo;

    private TacoRepo tacoRepo;

    private UserRepo userRepo;

    @Autowired
    public DesignController(IngredientRepo ingredientRepo, TacoRepo tacoRepo, UserRepo userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String designTaco(Model model, @AuthenticationPrincipal User user) {

        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Type.values();

        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }

        User currUser = userRepo.findByUsername(user.getUsername());

        model.addAttribute("user", currUser);

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoRepo.save(design);

        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
