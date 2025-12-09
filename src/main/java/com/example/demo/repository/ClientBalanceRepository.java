package com.example.demo.repository;

import com.example.demo.domain.ClientBalanceDto;
import org.springframework.data.repository.CrudRepository;

public interface ClientBalanceRepository  extends CrudRepository<ClientBalanceDto, String> {

}