package ma.enset.comptecqrses.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountRequestDTO {
    private String AccountId;
    private double amount;
    private String currency;
}