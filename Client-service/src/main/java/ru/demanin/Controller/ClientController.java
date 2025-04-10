package ru.demanin.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demanin.service.ClientService;

//@RestController
//@RequestMapping("/api")
//@Api(value = "Client Controller", description = "Операции с клиентами")
public class ClientController {
//    private final ClientService clientService;
//
////    @Autowired
//    public ClientController(ClientService clientService) {
//        this.clientService = clientService;
//    }
////    @ApiOperation(
//            value = "Регистрация клиента"
//    )
//    @PostMapping("/add/client")
//    public ResponseEntity<String> addClient(@ApiParam(value = "Введите имя")@RequestParam String name,
//                                            @ApiParam(value = "Введите фамилию")@RequestParam String fullName,
//                                            @ApiParam(value = "Введите отчество")@RequestParam String surname,
//                                            @ApiParam(value = "Введите логин")@RequestParam String login,
//                                            @ApiParam(value = "Введите пароль")@RequestParam String password){
//        clientService.saveClient(name,fullName,surname,login,password);
//        return ResponseEntity.ok("Вы успешно зарегистрировались");
//    }
}
