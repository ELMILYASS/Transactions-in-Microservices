package com.example.webflux.controller;

import com.example.webflux.entities.Company;
import com.example.webflux.entities.Transaction;
import com.example.webflux.repositories.CompanyRepository;
import com.example.webflux.repositories.TransactionRepository;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class TransactionReactiveRestController {

    private TransactionRepository transactionRepository;

    private CompanyRepository companyRepository;


    public TransactionReactiveRestController(TransactionRepository transactionRepository, CompanyRepository companyRepository) {
        this.transactionRepository = transactionRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping(value = "/transactions")
    public Flux<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    @GetMapping(value = "/transactions/{id}")
    public Mono<Transaction> getOne(@PathVariable Long id){
        return transactionRepository.findById(id);
    }
    @PostMapping("/transactions")
    public Mono<Transaction> save(@RequestBody Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @DeleteMapping(value = "/transactions/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return transactionRepository.deleteById(id);
    }
    @PutMapping("/transactions/{id}")
    public Mono<Transaction> update(@RequestBody Transaction transaction, @PathVariable String id){
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @GetMapping(value = "/streamTransactions",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> streamTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transactionsByCompany/{id}")
    public Flux<Transaction> transactionsBySoc(@PathVariable Long id){
        return transactionRepository.findByCompanyId(id);
    }

    //The produces attribute specifies that the response will be in the text/event-stream format, which is suitable for SSE.

    @GetMapping(value = "/streamTransactionsByCompany/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> stream(@PathVariable Long id){
        return companyRepository.findById(id) // this return Mono<Company>
                // flatMap to construct one flux from multiple flux , Many to convert Mono to Flux
                .flatMapMany(soc->{
                    // This creates a Flux that emits a value every second
                    Flux<Long> interval=Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction> transactionFlux= Flux.fromStream(Stream.generate(()->{
                        Transaction transaction=new Transaction();
                        transaction.setInstant(Instant.now());
                        transaction.setCompanyId(soc);
                        transaction.setPrice(soc.getPrice()*(1+(Math.random()*12-6)/100));
                        return transaction;
                    }));
                    return Flux.zip(interval,transactionFlux) //new Flux that emits a tuple containing the timer value and a Transaction object every second.
                            .map(data->{
                                return data.getT2();
                            }).share(); // This operator ensures that the Flux is shared among all subscribers, meaning that multiple clients can subscribe to the same stream of transactions without each receiving a separate stream.
                });
    }

    @GetMapping(value = "/events/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Flux<Double>  events(@PathVariable String id){
        WebClient webClient=WebClient.create("http://localhost:8082");
        Flux<Double> eventFlux=webClient.get()
                .uri("/streamEvents/"+id)
                .retrieve().bodyToFlux(Event.class)
                .map(data->data.getValue());
        return eventFlux;

    }
    @GetMapping("/test")
    public String test(){
        return Thread.currentThread().getName();
    }

}
@Data
class Event{
    private Instant instant;
    private double value;
    private Long companyId;
}