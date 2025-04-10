package ru.demanin.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demanin.response.ResponseCarrier;
import ru.demanin.service.CarrierService;

@RestController
@RequestMapping("/api/carrier")
@Api(value = "Carrier Controller", description = "Операции с перевозчиками")
public class CarrierController {
    private final CarrierService carrierService;

    @Autowired
    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @PostMapping("/createCarrier")
    @ApiOperation("Создание перевозчика")
    public ResponseEntity<ResponseCarrier> createCarrier(@ApiParam(value = "Введите название фирмы перевозчика") @RequestParam String name,
                                                         @ApiParam(value = "Введите номер телефона") @RequestParam String phoneNumber) {
        carrierService.createCarrier(name, phoneNumber);
        return ResponseEntity.ok(new ResponseCarrier("Фирма зарегестрировага"));
    }
    @PostMapping("/deleteCarrier")
    @ApiOperation("Удаление перевозчика")
    public ResponseEntity<ResponseCarrier>deleteCarrier(@ApiParam("Укажите id перевозчика")
                                                        @RequestParam long carrierId){
        carrierService.deleteCarrier(carrierId);
        return ResponseEntity.ok(new ResponseCarrier("Перевозчик удален"));
    }

}
