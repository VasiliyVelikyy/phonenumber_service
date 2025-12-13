package com.example.demo.repository;

import com.example.demo.domain.ClientBalanceFetchDto;
import org.springframework.data.repository.CrudRepository;

public interface ClientBalanceFetcherRepository extends CrudRepository<ClientBalanceFetchDto, String> {

}