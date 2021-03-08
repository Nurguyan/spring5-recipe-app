package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    UnitOfMeasureRepository uomRepository;
    RecipeRepository recipeRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public DevBootstrap(UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.uomRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Saving recipes' repository...");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>(2);

        //Spicy Grilled Chicken Tacos
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }
        Category mexicanCategory = categoryOptional.get();
        //Unit of measures
        Optional<UnitOfMeasure> tablespoonUomOptional = uomRepository.findByDescription("Tablespoon");
        if (!tablespoonUomOptional.isPresent()){
            throw new RuntimeException("Tablespoon uom not found");
        }
        Optional<UnitOfMeasure> teaspoonUomOptional = uomRepository.findByDescription("Teaspoon");
        if (!teaspoonUomOptional.isPresent()){
            throw new RuntimeException("Teaspoon uom not found");
        }
        Optional<UnitOfMeasure> cloveUomOptional = uomRepository.findByDescription("Clove");
        if (!cloveUomOptional.isPresent()){
            throw new RuntimeException("Clove uom not found");
        }
        Optional<UnitOfMeasure> cupUomOptional = uomRepository.findByDescription("Cup");
        if (!cupUomOptional.isPresent()){
            throw new RuntimeException("Cup uom not found");
        }
        Optional<UnitOfMeasure> poundsUomOptional = uomRepository.findByDescription("Pounds");
        if (!poundsUomOptional.isPresent()){
            throw new RuntimeException("Pound uom not found");
        }

        UnitOfMeasure tableSpoon = tablespoonUomOptional.get();
        UnitOfMeasure teaSpoon = teaspoonUomOptional.get();
        UnitOfMeasure cloveUom = cloveUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure poundsUom = poundsUomOptional.get();

        log.debug("Setting recipe fields...");

        Recipe chickRcp = new Recipe();
        chickRcp.setDifficulty(Difficulty.EASY);
        chickRcp.getCategories().add(mexicanCategory);
        chickRcp.setDescription("Spicy Grilled Chicken Tacos");
        chickRcp.setCookTime(15);
        Notes notes = new Notes();
        notes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        chickRcp.setNotes(notes);
        chickRcp.setPrepTime(20);
        chickRcp.setServings(4);
        chickRcp.setDirections("Prepare a gas or charcoal grill for medium-high, direct heat\n" +
                "Make the marinade and coat the chicken\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "Grill the chicken\n" +
                "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "Warm the tortillas:\n" +
                "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "Assemble the tacos\n" +
                "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
        chickRcp.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        log.debug("adding ingredients...");

        //Ingredients
        chickRcp.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoon));
        chickRcp.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoon));
        chickRcp.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoon));
        chickRcp.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoon));
        chickRcp.addIngredient(new Ingredient("salt", new BigDecimal(1), teaSpoon));
        chickRcp.addIngredient(new Ingredient("finely chopped garlic", new BigDecimal(1), cloveUom));
        chickRcp.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoon));
        chickRcp.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoon));
        chickRcp.addIngredient(new Ingredient("olive oil", new BigDecimal(3), tableSpoon, chickRcp));
        chickRcp.addIngredient(new Ingredient("skinless, boneless chicken thighs", new BigDecimal(1), poundsUom));
        chickRcp.addIngredient(new Ingredient("baby arugula", new BigDecimal(3), cupUom));

        log.debug("Grilled chicken properties are set!");

        recipes.add(chickRcp);
        return recipes;
    }
}
