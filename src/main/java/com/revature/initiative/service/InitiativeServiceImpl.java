package com.revature.initiative.service;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.exception.UserNotFoundException;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InitiativeServiceImpl implements InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final UserRepository userRepository;

    @Autowired
    public InitiativeServiceImpl(InitiativeRepository initiativeRepository, UserRepository userRepository) {
        this.initiativeRepository = initiativeRepository;
        this.userRepository = userRepository;
    }

    private static Initiative initiativeMapENT(InitiativeDTO ent) {
        if (ent == null) return null;

        Initiative ret = new Initiative();

        ret.setCreatedById(ent.getCreatedBy());
        ret.setPointOfContactId(ent.getPointOfContact());
        ret.setTitle(ent.getTitle());
        ret.setDescription(ent.getDescription());
        ret.setState(ent.getState());

        return ret;
    }

    private static InitiativeDTO initiativeMapDTO(Initiative ent) {
        if (ent == null) return null;
        InitiativeDTO ret = new InitiativeDTO();

        ret.setCreatedBy(ent.getCreatedById());
        ret.setPointOfContact(ent.getPointOfContactId());
        ret.setTitle(ent.getTitle());
        ret.setDescription(ent.getDescription());
        ret.setState(ent.getState());
        ret.setMembers(ent.getMembers());

        return ret;
    }

    private static List<Initiative> initiativeMapENT(List<InitiativeDTO> ent) {
        List<Initiative> ret = new ArrayList<>();

        for (InitiativeDTO i : ent) ret.add(initiativeMapENT(i));

        return ret;
    }

    private static List<InitiativeDTO> initiativeMapDTO(List<Initiative> ent) {
        List<InitiativeDTO> ret = new ArrayList<>();

        for (Initiative i : ent) ret.add(initiativeMapDTO(i));

        return ret;
    }

    @Override
    public InitiativeDTO addInitiative(InitiativeDTO init) {
        try {
            return initiativeMapDTO(initiativeRepository.save(initiativeMapENT(init)));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new InvalidTitleException("ERROR: title already exists");
        }
    }

    @Transactional
    @Override
    public InitiativeDTO getInitiative(long id) {
        return initiativeMapDTO(initiativeRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Transactional
    @Override
    public InitiativeDTO getInitiative(String title) {
        return initiativeMapDTO(initiativeRepository.findByTitle(title));
    }

    @Override
    public List<InitiativeDTO> getInitiatives() {
        return initiativeMapDTO(initiativeRepository.findAll());
    }

    @Override
    public List<InitiativeDTO> getInitiatives(InitiativeState state) {
        return initiativeMapDTO(initiativeRepository.findByState(state));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(long initId, long userId) {
        Initiative ent = initiativeRepository.findById(initId).orElseThrow(NoSuchElementException::new);
        ent.setPointOfContactId(userId);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(long initId, String username) {
        Initiative ent = initiativeRepository.findById(initId).orElseThrow(NoSuchElementException::new);
        ent.setPointOfContactId(userRepository.findByuserName(username).getId());
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    //This is the one we're using
    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(String title, long userId) {
        Initiative ent = initiativeRepository.findByTitle(title);
        User user = userRepository.findUserById(userId);

        if(user == null) throw new UserNotFoundException("ERROR: user does not exist");
        if(ent == null) throw new InvalidTitleException("ERROR: title does not exist");
        ent.setPointOfContactId(userId);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(String title, String username) {
        Initiative ent = initiativeRepository.findByTitle(title);
        if (ent == null) return null;
        ent.setPointOfContactId(userRepository.findByuserName(username).getId());
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Override
    public void remInitiative(long id) {
        initiativeRepository.deleteById(id);
    }

    @Override
    public void remInitiative(String title) {
        initiativeRepository.deleteByTitle(title);
    }
}
