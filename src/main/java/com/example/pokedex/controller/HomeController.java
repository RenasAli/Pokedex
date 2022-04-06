package com.example.pokedex.controller;
import com.example.pokedex.model.Pokedex;
import com.example.pokedex.repository.PokedexRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
   PokedexRepository pokedexRepository;

    public HomeController(PokedexRepository pokedexRepository) {
        this.pokedexRepository = pokedexRepository;
    }
@GetMapping("/")
    public String index(Model model){
       model.addAttribute("pokedexes",pokedexRepository.getAll());
      return "index";
    }
    @GetMapping("/add")
    public String showAddPokemon(){
        return "add";
    }
    @PostMapping(value = "add")
    public String addPokemon(@RequestParam("name") String Name ,
                             @RequestParam("speed") int Speed,
                             @RequestParam("special_defence") int Special_defence,
                             @RequestParam("special_attack") int Special_attack,
                             @RequestParam("defence") int Defence,
                             @RequestParam("attack") int Attack,
                             @RequestParam("hp") int Hp,
                             @RequestParam("primary_type") String Primary_type,
                             @RequestParam("secondary_type") String Secondary_type){
        Pokedex newPokedex= new Pokedex();
        newPokedex.setName(Name);
        newPokedex.setSpeed(Speed);
        newPokedex.setSpecial_defence(Special_defence);
        newPokedex.setSpecial_attack(Special_attack);
        newPokedex.setDefence(Defence);
        newPokedex.setAttack(Attack);
        newPokedex.setHp(Hp);
        newPokedex.setPrimary_type(Primary_type);
        newPokedex.setSecondary_type(Secondary_type);
        pokedexRepository.addPokemon(newPokedex);
     return "redirect:/";
    }
    @GetMapping("/update/{id}")
    public String showupdateside(@PathVariable("id") int id, Model model){
       model.addAttribute("findPokemonById", pokedexRepository.findPokemonById(id));
     return "update";
    }

    @PostMapping(value = "/update")
    public String updatePokemon(
                                @RequestParam("name") String Name ,
                                @RequestParam("speed") int Speed,
                                @RequestParam("special_defence") int Special_defence,
                                @RequestParam("special_attack") int Special_attack,
                                @RequestParam("defence") int Defence,
                                @RequestParam("attack") int Attack,
                                @RequestParam("hp") int Hp,
                                @RequestParam("primary_type") String Primary_type,
                                @RequestParam("secondary_type") String Secondary_type,
                                @ModelAttribute Pokedex pokedex){

        pokedex.setName(Name);
        pokedex.setSpeed(Speed);
        pokedex.setSpecial_defence(Special_defence);
        pokedex.setSpecial_attack(Special_attack);
        pokedex.setDefence(Defence);
        pokedex.setAttack(Attack);
        pokedex.setHp(Hp);
        pokedex.setPrimary_type(Primary_type);
        pokedex.setSecondary_type(Secondary_type);
        pokedexRepository.updateByid(pokedex);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePokemon(@PathVariable("id")int Id){
        pokedexRepository.deleteById(Id);
        return "redirect:/";
    }
}
