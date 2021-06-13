package com.revature.initiative.service;

import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.exception.UserNotFoundException;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.model.File;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InitiativeServiceImpl implements InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(InitiativeServiceImpl.class);

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

        ret.setInitiativeId(ent.getId());
        ret.setCreatedBy(ent.getCreatedById());
        ret.setPointOfContact(ent.getPointOfContactId());
        ret.setTitle(ent.getTitle());
        ret.setDescription(ent.getDescription());
        ret.setState(ent.getState());
        ret.setMembers(new HashSet<>());
        ret.setFiles(new HashSet<>());
        if (ent.getMembers() != null) for (User i : ent.getMembers()) {
            UserDTO user = new UserDTO();
            user.setUsername(i.getUsername());
            user.setRole(i.getRole());
            user.setId(i.getId());
            ret.getMembers().add(user);
        }
        if (ent.getFiles() != null) for (File i : ent.getFiles()) {
            FileDTO file = new FileDTO();
            file.setFileName(i.getFileName());
            file.setFileUrl(i.getFileURL());
            file.setUploadedBy(i.getUploadedById());
            ret.getFiles().add(file);
        }

        return ret;
    }

    private static List<InitiativeDTO> initiativeMapDTO(List<Initiative> ent) {
        List<InitiativeDTO> ret = new ArrayList<>();

        for (Initiative i : ent) {
            i.setFiles(new HashSet<>());
            i.setMembers(new HashSet<>());
            ret.add(initiativeMapDTO(i));
        }

        return ret;
    }

    @Override
    public InitiativeDTO addInitiative(InitiativeDTO init) {
        try {
            if (init == null) throw new EmptyEntity();
            logger.info("Initiative added with [ Title: {} ]", init.getTitle());
            return initiativeMapDTO(initiativeRepository.save(initiativeMapENT(init)));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            e.printStackTrace();
            logger.info("Initiative already exists with [ Title: {} ]", init.getTitle());
            throw new InvalidTitleException("ERROR: title already exists");
        }
    }

    @Override
    public InitiativeDTO getInitiative(long id) {
        logger.info("Initiative retrieved with [ Id: {} ]", id);
        return initiativeMapDTO(initiativeRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public InitiativeDTO getInitiative(String title) {
        logger.info("Initiative retrieved with [ Title: {} ]", title);
        return initiativeMapDTO(initiativeRepository.findByTitle(title));
    }

    @Override
    public List<InitiativeDTO> getInitiatives() {
        logger.info("All initiatives retrieved");
        return initiativeMapDTO(initiativeRepository.findAll());
    }

    @Override
    public List<InitiativeDTO> getInitiatives(InitiativeState state) {
        logger.info("Initiative retrieved with [ State: {} ]", state);
        return initiativeMapDTO(initiativeRepository.findByState(state));
    }

    @Override
    public InitiativeDTO setInitiativeState(Long id, InitiativeState state) {
        Initiative ent = initiativeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ent.setState(state);
        logger.info("Initiative retrieved with [ State: {} ]", state);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(long initId, long userId) {
        Initiative ent = initiativeRepository.findById(initId).orElseThrow(NoSuchElementException::new);
        ent.setPointOfContactId(userId);
        logger.info("Initiative POC set with [ Initiative ID: {}, User ID: {} ]", initId, userId);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(long initId, String username) {
        Initiative ent = initiativeRepository.findById(initId).orElseThrow(NoSuchElementException::new);
        ent.setPointOfContactId(userRepository.findByUsername(username).getId());
        logger.info("Initiative POC set with [ Initiative ID: {}, Username: {} ]", initId, username);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    //This is the one we're using
    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(String title, long userId) {
        Initiative ent = initiativeRepository.findByTitle(title);
        User user = userRepository.findUserById(userId);

        if (user == null) {
            logger.info("ERROR: Initiative POC not set - user does not exist [ Title: {}, User ID: {} ]", title, userId);
            throw new UserNotFoundException("ERROR: user does not exist");
        }
        if (ent == null) {
            logger.info("ERROR: Initiative POC not set - title does not exist [ Title: {}, User ID: {} ]", title, userId);
            throw new InvalidTitleException("ERROR: title does not exist");
        }
        ent.setPointOfContactId(userId);
        logger.info("Initiative POC set with [ Initiative Title: {}, User ID: {} ]", title, userId);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Transactional
    @Override
    public InitiativeDTO setInitiativePOC(String title, String username) {
        Initiative ent = initiativeRepository.findByTitle(title);
        if (ent == null) return null;
        ent.setPointOfContactId(userRepository.findByUsername(username).getId());
        logger.info("Initiative POC set with [ Title: {}, Username: {} ]", title, username);
        return initiativeMapDTO(initiativeRepository.save(ent));
    }

    @Override
    public void remInitiative(long id) {
        logger.info("Initiative removed with [ Initiative ID: {} ]", id);
        initiativeRepository.deleteById(id);
    }

    @Override
    public void remInitiative(String title) {
        logger.info("Initiative removed with [ Title: {} ]", title);
        initiativeRepository.deleteByTitle(title);
    }
}
