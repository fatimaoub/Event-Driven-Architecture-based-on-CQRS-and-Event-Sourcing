package ma.enset.comptecqrses.commonapi.commands;

import lombok.Getter;

import java.math.BigDecimal;

public class DebitAccountCommand extends BaseCommand<String> {
    @Getter
    private double amount;
    @Getter private String currency;
    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
