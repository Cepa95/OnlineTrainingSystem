package com.training.OnlineTraining.service.implementation;

import com.training.OnlineTraining.dto.ClientDto;
import com.training.OnlineTraining.dto.UpdateClientDTO;
import com.training.OnlineTraining.mapper.ClientMapper;
import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.Role;
import com.training.OnlineTraining.model.User;
import com.training.OnlineTraining.repository.ClientRepository;
import com.training.OnlineTraining.repository.UserRepository;
import com.training.OnlineTraining.service.ClientService;
import com.training.OnlineTraining.service.UserService;
import com.training.OnlineTraining.util.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserService userService;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public void registerClient(ClientDto clientDto, UUID userId) {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(userId));

        optionalUser.ifPresentOrElse(
                user -> {
                    Client client = new Client();
                    client.setUser(user);
                    client.setMedicalCondition(clientDto.getMedicalCondition());
                    client.setInjuries(clientDto.getInjuries());
                    clientRepository.save(client);
                    if (user.getRole() == null) {
                        user.setRole(Role.RoleFactory.getClientRole());
                        userRepository.save(user);
                    }

                },
                () -> {
                    throw new RuntimeException("User not found");
                }
        );
    }
    @Override
    public boolean isClient(User user) {
        return clientRepository.existsByUser(user);
    }

    @Override
    public Client getClientByUserId(UUID userId) {
        return clientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public Client getClientsById(UUID clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public boolean areInputsInvalid(UpdateClientDTO request) {
        return  ValidationUtils.isStringNullOrEmpty(request.getFirstName()) ||
                ValidationUtils.isStringNullOrEmpty(request.getLastName()) ||
                ValidationUtils.isStringNullOrEmpty(request.getStreet()) ||
                ValidationUtils.isStringNullOrEmpty(request.getCity()) ||
                ValidationUtils.isStringNullOrEmpty(request.getCountry()) ||
                ValidationUtils.isStringNullOrEmpty(request.getPhoneNumber()) ||
                ValidationUtils.isStringNullOrEmpty(request.getGender()) ||
                ValidationUtils.isAgeInvalid(request.getAge());
    }

    @Transactional
    @Override
    public void updateClient(UUID clientId, UpdateClientDTO updateClientDTO) {

        if (areInputsInvalid(updateClientDTO)) {
            throw new RuntimeException("Invalid client input");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));


        ClientMapper.INSTANCE.updateClientFromDTO(updateClientDTO, client);


        clientRepository.save(client);
    }

}
