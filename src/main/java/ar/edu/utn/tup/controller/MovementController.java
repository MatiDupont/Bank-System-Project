package ar.edu.utn.tup.controller;

import ar.edu.utn.tup.controller.dto.InvestDTO;
import ar.edu.utn.tup.controller.dto.ResponseDTO;
import ar.edu.utn.tup.controller.dto.TransferDTO;
import ar.edu.utn.tup.controller.dto.WithdrawDTO;
import ar.edu.utn.tup.controller.validator.MovementDTOValidator;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.service.MovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/movement")
public class MovementController {
    @Autowired
    private MovementsService movementsService;
    @Autowired
    private MovementDTOValidator movementDTOValidator;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMovementById(@PathVariable Long id) {
        Optional<Movement> movement = movementsService.findMovementById(id);

        return movement.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(movementsService.convertToDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movement not found with ID: " + id));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> makeWithdraw(@RequestBody Map<String, Object> jsonMap) {
        try {
            movementDTOValidator.validateKeysForWithdraw(jsonMap);

            WithdrawDTO withdrawDTO = movementsService.convertToWDTO(jsonMap);

            Movement createdMovement = movementsService.createWithdrawal(withdrawDTO);

            ResponseDTO responseDTO = new ResponseDTO(
                    "SUCCESSFUL",
                    "Successful withdrawal."
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch (Exception ex) {
            ResponseDTO responseDTO = new ResponseDTO(
                    "FAILED",
                    "Failed to make a withdrawal: " + ex.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> makeTransfer(@RequestBody Map<String, Object> jsonMap) {
        try {
            movementDTOValidator.validateKeysForTransfer(jsonMap);

            TransferDTO transferDTO = movementsService.convertToTDTO(jsonMap);

            Movement createdMovement = movementsService.createTransfer(transferDTO);

            ResponseDTO responseDTO = new ResponseDTO(
                    "SUCCESSFUL",
                    "Successful transfer."
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch (Exception ex) {
            ResponseDTO responseDTO = new ResponseDTO(
                    "FAILED",
                    "Failed to make a transfer: " + ex.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/invest")
    public ResponseEntity<Object> makeInvest(@RequestBody Map<String, Object> jsonMap) {
        try {
            movementDTOValidator.validateKeysForInvest(jsonMap);

            InvestDTO investDTO = movementsService.convertToIDTO(jsonMap);

            Movement createdMovement = movementsService.createInvest(investDTO);

            ResponseDTO responseDTO = new ResponseDTO(
                    "SUCCESSFUL",
                    "Successful invest."
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
        catch (Exception ex) {
            ResponseDTO responseDTO = new ResponseDTO(
                    "FAILED",
                    "Failed to make a investment: " + ex.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
}
