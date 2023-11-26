package ma.enset.comptecqrses.commands.aggregates;
import ma.enset.comptecqrses.commonapi.commands.CreateAccountCommand;
import ma.enset.comptecqrses.commonapi.commands.CreditAccountCommand;
import ma.enset.comptecqrses.commonapi.commands.DebitAccountCommand;
import ma.enset.comptecqrses.commonapi.enums.AccountStatus;

import ma.enset.comptecqrses.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountCreatedEvent;

import ma.enset.comptecqrses.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrses.commonapi.exceptions.AmountNegativeException;
import ma.enset.comptecqrses.commonapi.exceptions.AmountNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand accountCommand) {
        if (accountCommand.getBalance() < 0) throw new RuntimeException("invalid");
        //bien
        AggregateLifecycle.apply(new AccountCreatedEvent(
                accountCommand.getId(),
                accountCommand.getBalance(),
                accountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getBalance();
        this.status = AccountStatus.CREATED;
        this.currency = event.getCurrency();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));

    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if (command.getAmount() < 0) throw new AmountNegativeException("Debit amount cannot be negative");

        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if(command.getAmount() < 0) throw new AmountNegativeException("Debit amount cannot be negative");
        if(this.balance<command.getAmount())throw new AmountNotSufficientException("Balance  "+this.balance+" not sufficint");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-=event.getAmount();
    }
}