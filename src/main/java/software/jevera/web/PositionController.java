package software.jevera.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.jevera.domain.Position;
import software.jevera.domain.ProfessionalityLevel;
import software.jevera.domain.dto.PositionDto;
import software.jevera.exception.PositionAlreadyExist;
import software.jevera.service.PositionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping(value = "/getAll")
    public List<Position> getAll(){
        return positionService.getAll();
    }

//    @GetMapping("/findById")
    public Optional<Position> findById(Long id){ return Optional.ofNullable(positionService.findById(id));}

//    @GetMapping("/findByNameAndLevel")
    public Long findByNameAndLevel(String currentPosition, ProfessionalityLevel professionalityLevel){
        return positionService.getByNameAndLevel(currentPosition, professionalityLevel).getId();
    }

//    @PostMapping("/create")
    public Position create(PositionDto positionDto) throws PositionAlreadyExist {
        return positionService.create(positionDto);
    }

//    @PutMapping("/update/{id}")
    public Position update(@PathVariable("id") Position position, @RequestBody PositionDto positionDto){
        return positionService.update(position, positionDto);
    }

//    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Position position){
        positionService.delete(position);
    }
}
