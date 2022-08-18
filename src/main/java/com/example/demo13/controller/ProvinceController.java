package com.example.demo13.controller;

import com.example.demo13.model.Country;
import com.example.demo13.model.Province;
import com.example.demo13.service.ICRUDCountry;
import com.example.demo13.service.ICRUDProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private ICRUDProvince icrudProvince;
    @Autowired
    private ICRUDCountry icrudCountry;
    @ModelAttribute("provinces")
    public Page<Province> studentList(@PageableDefault(value = 4) @SortDefault(sort = {"gdp"}, direction = Sort.Direction.DESC) Pageable pageable){
        return icrudProvince.findAll(pageable);
    }
    @ModelAttribute("country")
    public Country country() {
        return new Country();
    }
    @ModelAttribute("countries")
    public List<Country> countryList() {
        return icrudCountry.findAll();
    }

    @GetMapping
    public ModelAndView findAllProvince() {
        return new ModelAndView("display");
    }

    @GetMapping("/create")
    public ModelAndView createStudent() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Optional<Province> province,
                         RedirectAttributes redirectAttributes) {
        if (province.isPresent()) {
            setImageOfCar(province.get());
            icrudProvince.save(province.get());
            redirectAttributes.addFlashAttribute("message", "Create successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Create fail!");
        }

        return "redirect:/province";
    }
    @GetMapping("/update/{id}")
    public ModelAndView updateStudent(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Province> province = icrudProvince.findById(id);
        if (province.isPresent()) {
            modelAndView.addObject("province", province.get());
        } else {
            modelAndView.addObject("message", "Not exist student!");
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Optional<Province> province,@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        Optional<Province> province1 = icrudProvince.findById(id);
//        if (province.isPresent() && province1.isPresent() &&  (province.get().getImage_Url() == null) ) {
//            province.get().setImage_Url(province1.get().getImage_Url());
//            icrudProvince.save(province.get());
//            redirectAttributes.addFlashAttribute("message", "Update successfully!");
//        }
//       else {
//            redirectAttributes.addFlashAttribute("message", "Update fail!");
//        }
        if (province.get().getImage_Url() == null) {
            province.get().setImage(province1.get().getImage());
        }
        setImageOfCar(province.get());
        icrudProvince.save(province.get());
        redirectAttributes.addFlashAttribute("message", "Update successfully!");
        return "redirect:/province";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        icrudProvince.delete(id);
        redirectAttributes.addFlashAttribute("message", "Delete successfully!");
        return "redirect:/province";
    }
    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Province> province = icrudProvince.findById(id);
        if (province.isPresent()) {
            modelAndView.addObject("province", province.get());
        }else {
            modelAndView.addObject("message", "Not exist student!");
        }
        return modelAndView;
    }
    @GetMapping("/search")
    public ModelAndView search(@RequestParam("search") Optional<String> name,
                               @PageableDefault(value = 2) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Province> provincePage;
        if (name.isPresent()) {
            provincePage = icrudProvince.findBySearch(name.get(),pageable);
            modelAndView.addObject("search", name.get());
        } else {
            provincePage = icrudProvince.findAll(pageable);
        }
        modelAndView.addObject("provinces", provincePage);
        return modelAndView;
    }

//    @GetMapping("/demo")
//    public ModelAndView demo(@RequestParam("minGdp") Double minGdp, @RequestParam("maxGdp") Double maxGdp,
//                             @RequestParam("minPopular") Long minPopular, @RequestParam("maxPopular") Long maxPopular,
//                             @RequestParam("countryId") Long countryId,
//                               @PageableDefault(value = 2) Pageable pageable) {
//        ModelAndView modelAndView = new ModelAndView("display");
//        Page<Province> provincePage = icrudProvince.demo(minGdp, maxGdp, minPopular, maxPopular, countryId, pageable);
//        modelAndView.addObject("minGdp", minGdp);
//        modelAndView.addObject("maxGdp", maxGdp);
//        modelAndView.addObject("minPopular", minPopular);
//        modelAndView.addObject("maxPopular", maxPopular);
//        modelAndView.addObject("countryId", countryId);
//        modelAndView.addObject("provinces", provincePage);
//        return modelAndView;
//    }

    @PostMapping("/searchByC")
    public ModelAndView searchByC(@ModelAttribute Country country,@PageableDefault(value = 4) Pageable pageable) {
        Optional<Long> id = Optional.ofNullable(country.getId());
        ModelAndView modelAndView = new ModelAndView("display");
            Page<Province> provinces = icrudProvince.findAllByCountry(id,pageable);
            modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }
    @GetMapping("/top4")
    public ModelAndView findTop4(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Province> provincePage = icrudProvince.findTop4Gdp(pageable);
        modelAndView.addObject("provinces",provincePage);
        return modelAndView;
    }
    private void setImageOfCar(Province province) {
        MultipartFile image = province.getImage_Url();
        String imageUrl = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(fileUpload + image.getOriginalFilename()));
        } catch (IOException ex) {
            System.err.println("Error");
        }
        province.setImage(imageUrl);
    }
    @GetMapping("/top4ByA")
    public ModelAndView findTop4ByA(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Province> provincePage = icrudProvince.findTop4Area(pageable);
        modelAndView.addObject("provinces",provincePage);
        return modelAndView;
    }



}
