package com.example.demo.repository;

import com.example.demo.domain.ClientBalanceSaveDto;
import org.springframework.data.repository.CrudRepository;

public interface ClientBalanceSaverRepository extends CrudRepository<ClientBalanceSaveDto, String> {
}
