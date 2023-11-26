package ma.enset.comptecqrses.commonapi.events;

import lombok.Getter;
import ma.enset.comptecqrses.commonapi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String> {
    @Getter
    private final double balance;
    @Getter private final String currency;
    @Getter private final AccountStatus status;
    public AccountCreatedEvent(String id, double balance, String currency, AccountStatus status) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }
}