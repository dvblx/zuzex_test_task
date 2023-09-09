package com.zuzex.testTask.rest;

import com.zuzex.testTask.models.AddResidentsRequestBody;
import com.zuzex.testTask.models.House;
import com.zuzex.testTask.models.User;
import com.zuzex.testTask.repositories.HouseRepository;
import com.zuzex.testTask.repositories.UserRepository;
import com.zuzex.testTask.security.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/house")
public class HouseController {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public HouseController(HouseRepository houseRepository, UserRepository userRepository, JWTUtil jwtUtil) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping()
    public ResponseEntity<List<House>> getHouseList(){
        return new ResponseEntity<>(houseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{house_id}")
    public ResponseEntity<House> getOneHouse(@PathVariable long house_id){
        Optional <House> house = houseRepository.findById(house_id);
        return house.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{house_id}")
    public ResponseEntity<House> updateHouse(@PathVariable long house_id, @RequestBody House house){
        Optional<House> houseOld = houseRepository.findById(house_id);
        if (houseOld.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        House housePatched = houseOld.get();
        if (house.getStreetName() != null){housePatched.setStreetName(house.getStreetName());}
        if (house.getHouseNumber() != null){housePatched.setHouseNumber(house.getHouseNumber());}
        if (house.getApartmentNumber() != null){housePatched.setApartmentNumber(house.getApartmentNumber());}
        if (house.getHouseholder() != null){housePatched.setHouseholder(house.getHouseholder());}
        houseRepository.save(housePatched);
        return new ResponseEntity<>(housePatched, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Void> createHouse(@RequestBody House house){
        houseRepository.save(house);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{house_id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable long house_id){
        houseRepository.deleteById(house_id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{house_id}/add_residents")
    public ResponseEntity<House> addResidents(@PathVariable long house_id, @RequestBody AddResidentsRequestBody body,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){

        Optional<House> house = houseRepository.findById(house_id);
        if (house.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        House houseEntity = house.get();
        String jwt = auth.split(" ")[1];
        String username = jwtUtil.extractUsername(jwt);
        if (!houseEntity.getHouseholder().getLogin().equals(username))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Set<User> residents = houseEntity.getResidents();
        for (Long residentId: body.getResidentIdList()){
            Optional<User> resident = userRepository.findById(residentId);
            resident.ifPresent(residents::add);
        }
        houseRepository.save(houseEntity);
        return new ResponseEntity<>(houseEntity, HttpStatus.OK);
    }
    @PostMapping("/{house_id}/remove_residents")
    public ResponseEntity<House> removeResidents(@PathVariable long house_id, @RequestBody AddResidentsRequestBody body,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        Optional<House> house = houseRepository.findById(house_id);
        if (house.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        House houseEntity = house.get();
        String jwt = auth.split(" ")[1];
        String username = jwtUtil.extractUsername(jwt);
        if (!houseEntity.getHouseholder().getLogin().equals(username))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Set<User> residents = houseEntity.getResidents();
        for (Long residentId: body.getResidentIdList()){
            Optional<User> resident = userRepository.findById(residentId);
            resident.ifPresent(residents::remove);
        }
        houseRepository.save(houseEntity);
        return new ResponseEntity<>(houseEntity, HttpStatus.OK);
    }

}
