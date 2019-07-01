package software.jevera.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.jevera.domain.Position;
import software.jevera.domain.ProfessionalityLevel;
import software.jevera.domain.dto.PositionDto;
import software.jevera.exception.PositionAlreadyExist;
import software.jevera.exception.UncorrectGrant;
import software.jevera.repository.PositionRepository;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ModelMapper modelMapper;

    public Position save(Position position){
        return positionRepository.save(position);
    }

    public List<Position> getAll(){
        return positionRepository.findAll();
    }

    public Position getByNameAndLevel(String name, ProfessionalityLevel professionalityLevel){
        return positionRepository.findByNameAndProfessionalLevel(name, professionalityLevel);
    }

    public Position create(PositionDto positionDto) throws PositionAlreadyExist {
        if(isPositionExist(positionDto.getName(), positionDto.getProfessionalLevel())) {
            throw new PositionAlreadyExist(positionDto.getName());
        }
        Position position = modelMapper.map(positionDto, Position.class);
        return save(position);
    }

    public Position findById(Long id){
        return (positionRepository.findById(id).orElseThrow(UncorrectGrant::new));
    }

    public Position update(Position position, PositionDto positionDto){
        modelMapper.map(positionDto, position);
        return save(position);
    }

    public void delete(Position position){ positionRepository.delete(position);}

    private boolean isPositionExist(String position,ProfessionalityLevel professionalLevel) { return false; }
}
